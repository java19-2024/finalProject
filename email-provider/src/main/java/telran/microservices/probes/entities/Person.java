package telran.microservices.probes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
@Getter
@Entity
@Table(name="persons")
public class Person {
	@Id
	long id;
	String email;
	String name;
	@ManyToOne
	@JoinColumn(name="sensor_id")
	Sensor sensor;
}
