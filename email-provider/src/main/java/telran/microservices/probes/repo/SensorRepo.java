package telran.microservices.probes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.microservices.probes.entities.Sensor;

public interface SensorRepo extends JpaRepository<Sensor, Long> {

}
