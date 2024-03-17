package telran.microservices.probes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	ResponseEntity<?> getEmailData(@PathVariable long id) {
		ResponseEntity<?> res = null;
		try {
			EmailData data = service.getEmailData(id);
			res = new ResponseEntity<EmailData>(data, HttpStatus.OK);
		} catch (Exception e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return res;
	}
}
