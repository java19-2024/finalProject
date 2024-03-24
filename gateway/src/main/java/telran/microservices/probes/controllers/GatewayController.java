package telran.microservices.probes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import telran.microservices.probes.service.IProxyService;

@RestController
public class GatewayController {
	@Autowired
	IProxyService service;
	
	@GetMapping("/**")
	ResponseEntity<byte[]>getResult(ProxyExchange<byte[]> proxy, HttpServletRequest request){
		return service.proxyRouting(proxy, request);
	}
}
