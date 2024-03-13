package trelran.microservices.probes;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import trelran.microservices.probes.dto.Probe;
import trelran.microservices.probes.entities.ListProbeValues;
import trelran.microservices.probes.repo.ListProbeRepo;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AvgReducerTest {
	private static final long ID_PROBE_NO_VALUES = 123;
	private static final int VALUE = 100;
	private static final long ID_PROBE_NO_AVG = 124;
	private static final long ID_PROBE_AVG = 125;
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	@MockBean
	ListProbeRepo repo;
	
	//===3 Probes for 3 scens===========
	Probe probeNoValues = new Probe(ID_PROBE_NO_VALUES, VALUE);
	Probe probeNoAvg = new Probe(ID_PROBE_NO_AVG, VALUE);
	Probe probeAvg = new Probe(ID_PROBE_AVG, VALUE);
	private String consumerBindingName = "avgConsumer-in-0";
	private String producerBindingName = "avgProducer-out-0";
	//==entities for Redis============
	static ListProbeValues listProbesNoAvg = new ListProbeValues(ID_PROBE_NO_AVG);
	static ListProbeValues listProbesAvg = new ListProbeValues(ID_PROBE_AVG);
	static List<Integer> valuesNoAvg;
	static List<Integer> valuesAvg;
	//===map to mock Redis==========
	static HashMap<Long, ListProbeValues> redisMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		valuesNoAvg = listProbesNoAvg.getValues();
		valuesAvg = listProbesAvg.getValues();
		valuesAvg.add(VALUE);
		redisMap.put(ID_PROBE_NO_AVG, listProbesNoAvg);
		redisMap.put(ID_PROBE_AVG, listProbesAvg);
	}
	@Test
	void probeNoValuesTest() {
		
		when(repo.findById(ID_PROBE_NO_VALUES))
		.thenReturn(Optional.ofNullable(null));
		
		when(repo.save(new ListProbeValues(ID_PROBE_NO_VALUES)))
		.thenAnswer(new Answer<ListProbeValues>() {
			@Override
			public ListProbeValues answer(InvocationOnMock invocation) throws Throwable {
				redisMap.put(ID_PROBE_NO_VALUES, invocation.getArgument(0));
				return  invocation.getArgument(0);
			}		
		});
		producer.send(new GenericMessage<Probe>(probeNoValues), consumerBindingName);
		Message<byte[]>message = consumer.receive(0, producerBindingName);
		assertNull(message);
		assertEquals(VALUE, redisMap.get(ID_PROBE_NO_VALUES).getValues().get(0));
	}
	
	@Test
	void probeNoAvgTest() {
		
		when(repo.findById(ID_PROBE_NO_AVG))
		.thenReturn(Optional.of(listProbesNoAvg));
		
		when(repo.save(new ListProbeValues(ID_PROBE_NO_AVG)))
		.thenAnswer(new Answer<ListProbeValues>() {
			@Override
			public ListProbeValues answer(InvocationOnMock invocation) throws Throwable {
				redisMap.put(ID_PROBE_NO_AVG, invocation.getArgument(0));
				return  invocation.getArgument(0);
			}		
		});
		producer.send(new GenericMessage<Probe>(probeNoAvg), consumerBindingName);
		Message<byte[]>message = consumer.receive(0, producerBindingName);
		assertNull(message);
		assertEquals(VALUE, redisMap.get(ID_PROBE_NO_AVG).getValues().get(0));
	}
	
	@Test
	void probeAvgTest() throws Exception {
		
		when(repo.findById(ID_PROBE_AVG))
		.thenReturn(Optional.of(listProbesAvg));
		
		when(repo.save(new ListProbeValues(ID_PROBE_AVG)))
		.thenAnswer(new Answer<ListProbeValues>() {
			@Override
			public ListProbeValues answer(InvocationOnMock invocation) throws Throwable {
				redisMap.put(ID_PROBE_AVG, invocation.getArgument(0));
				return  invocation.getArgument(0);
			}		
		});
		producer.send(new GenericMessage<Probe>(probeAvg), consumerBindingName);
		Message<byte[]>message = consumer.receive(0, producerBindingName);
		assertNotNull(message);
		ObjectMapper mapper = new ObjectMapper();
		assertEquals(probeAvg, mapper.readValue(message.getPayload(), Probe.class));
		assertTrue(redisMap.get(ID_PROBE_AVG).getValues().isEmpty());
	}
}
