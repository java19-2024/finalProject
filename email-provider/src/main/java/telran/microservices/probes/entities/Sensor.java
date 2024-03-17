package telran.microservices.probes.entities;

import jakarta.persistence.*;

@Entity
@Table(name="sensors")
public class Sensor {
	@Id
	long id;
	String purpose;
	@Column(name="min_value")
	double minValue;
	@Column(name="max_value")
	double maxValue;
}
