package telran.probes.microservices.service;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import telran.probes.microservices.dto.Probe;

@Slf4j
@Configuration
public class ProbesGeneratorConfigurableService {

	ObjectMapper mapper = new ObjectMapper();
	Random rand = new Random();
	@Value("${n_sensors:5}")
	private int nSensors;
	@Value("${min_value:30}")
	private int minValue;
	@Value("${max_value:250}")
	private int maxValue;
	
	@Bean
	Supplier<String> sendSensorData() {
		return () -> {
			Probe probe = getRandomProbe();
			log.debug("Probe was generaTED {}", probe);
			try {
				return mapper.writeValueAsString(probe);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		};
	}

	private Probe getRandomProbe() {

		long id = rand.nextLong(1, nSensors);
		int value = rand.nextInt(minValue, maxValue);
		Probe res = new Probe(id, value);
		return res;
	}
}
