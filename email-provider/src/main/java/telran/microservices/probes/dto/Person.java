package telran.microservices.probes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;


public record Person(@Positive(message="Id must be positive") long id, 
		@Email String email, 
		@Pattern(regexp="[A-Z][a-z]{2,}") String name, 
		@Past() LocalDate birthDate, 
		@Min(100000) @Max(999999) long sensorId) {
}
