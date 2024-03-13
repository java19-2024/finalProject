package trelran.microservices.probes;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import trelran.microservices.probes.dto.Probe;
import trelran.microservices.probes.service.IAvgReducer;

@SpringBootApplication
public class AvgReducerAppl {
	@Autowired
	IAvgReducer service;
	@Autowired
	StreamBridge sb;
	@Value("${app.avg.producer.binding.name:avgProducer-out-0}")
	String bindingName;

	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);
	}

	@Bean
	Consumer<Probe> avgConsumer(){
		return (probe) -> {
			Integer avgValue = service.avgReduce(probe);
			if(avgValue != null) {
				Probe avgProbe = new Probe(probe.id(), avgValue);
				sb.send(bindingName, avgProbe);
			}
		};
	}
}
