package telran.microservices.probes.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.microservices.probes.entities.Person;

public interface PersonRepo extends JpaRepository<Person, Long> {
	//@Query(value="select * from persons where sensor_id=:id", nativeQuery=true)
	List<Person> findBySensorId(long sensorId);

}
