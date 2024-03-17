package telran.microservices.probes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sensor_id")
	Sensor sensor;
}
