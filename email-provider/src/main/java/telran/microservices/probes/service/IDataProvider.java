package telran.microservices.probes.service;

import telran.microservices.probes.dto.EmailData;

public interface IDataProvider {
	EmailData getEmailData(long sensorId);
}
