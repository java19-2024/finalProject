package trelran.microservices.probes.repo;

import org.springframework.data.repository.CrudRepository;

import trelran.microservices.probes.entities.ListProbeValues;

public interface ListProbeRepo extends CrudRepository<ListProbeValues, Long> {

}
