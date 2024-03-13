package trelran.microservices.probes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import trelran.microservices.probes.dto.Probe;
import trelran.microservices.probes.entities.ListProbeValues;
import trelran.microservices.probes.repo.ListProbeRepo;
@Service
@Slf4j
public class AvgReducerImpl implements IAvgReducer {
	@Autowired
	ListProbeRepo repo;
	@Value("${app.reducing.size:100}")
	int reducingSize;
	
	@Override
	public Integer avgReduce(Probe probe) {
	Integer res = null;
	long id = probe.id();
	ListProbeValues lpv = repo.findById(id).orElse(null);
		if(lpv == null) {
			log.debug("Sensor with id {} wasn't find in Redis", id);
			lpv = new ListProbeValues(id);
		}
		List<Integer>values = lpv.getValues();
		values.add(probe.value());
		if(values.size() == reducingSize) {
			res = values.stream().collect(Collectors.averagingInt(v -> v)).intValue();
			log.debug("Computed avg value for sensor id {}", id);
			values.clear();
		}
		else {
			log.trace("Not enougth data to conpute avg value for sensor {}", id);
		}
		repo.save(lpv);
		return res;
	}

}
