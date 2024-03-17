package telran.microservices.probes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.entities.Person;
import telran.microservices.probes.repo.PersonRepo;
import telran.microservices.probes.repo.SensorRepo;
@Service
public class DataProviderImpl implements IDataProvider {
@Autowired
SensorRepo sensorRepo;
@Autowired
PersonRepo personRepo;

	@Override
	public EmailData getEmailData(long sensorId) {
		List<Person> pers = personRepo.findBySensorId(sensorId);
		if(pers.isEmpty())
			throw new IllegalArgumentException("no person for sensor id="+sensorId);
		String[] emails = pers.stream().map(p -> p.getEmail()).toArray(String[] :: new);
		String[] names = pers.stream().map(p -> p.getName()).toArray(String[] :: new);
		return new EmailData(emails, names);
	}

}
