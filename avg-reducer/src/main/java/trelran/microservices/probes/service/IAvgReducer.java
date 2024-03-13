package trelran.microservices.probes.service;

import trelran.microservices.probes.dto.Probe;

public interface IAvgReducer {
	Integer avgReduce(Probe probe);
}
