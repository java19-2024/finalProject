package telran.microservices.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.service.IDataProvider;

@WebMvcTest
class EmailDataProviderControllerTest {
	private static final long SENSOR_ID_EXIST = 123;
	private static final long SENSOR_ID_NOT_EXIST = 124;
	private static final String ERROR_MESSAGE = "no email data for sensor id="+SENSOR_ID_NOT_EXIST;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	IDataProvider dataProvider;
	
	EmailData data = new EmailData(new String[] {"vasya@mail.ru", "sara@gmail.com"},
			new String[] {"Vasya", "Sara"});

	@Test
	void testDataExists() throws  Exception {
		when(dataProvider.getEmailData(SENSOR_ID_EXIST))
		.thenReturn(data);
		
		String jsonData = mockMvc.perform(get("http://localhost:8080/email/data/"+SENSOR_ID_EXIST))
		.andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonExp = mapper.writeValueAsString(data);
		
		assertEquals(jsonExp, jsonData);
	}
	@Test
	void testDataNotExists() throws  Exception {
		when(dataProvider.getEmailData(SENSOR_ID_NOT_EXIST))
		.thenThrow(new IllegalArgumentException(ERROR_MESSAGE));
		
		String res = mockMvc.perform(get("http://localhost:8080/email/data/"+SENSOR_ID_NOT_EXIST))
				.andDo(print()).andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(ERROR_MESSAGE, res);
	}
}
