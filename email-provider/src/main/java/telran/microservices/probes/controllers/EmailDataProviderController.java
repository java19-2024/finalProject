package telran.microservices.probes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.service.IDataProvider;

@RestController
public class EmailDataProviderController {
	@Autowired
	IDataProvider service;
	
	@GetMapping("email/data/{id}")
	EmailData getEmailData(@PathVariable long id) {
		return service.getEmailData(id);
	}
}
