package telran.microservices.probes.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProxyServiceImpl implements IProxyService {
	@Value("#${app.routed.urls}")
	Map<String, String> urlsMap;
	//email:'http://email-data-provider:8080
	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy, HttpServletRequest request) {
		
		String routedUri = getRoutedUri(request);
		return proxy.uri(routedUri ).get();
	}
	private String getRoutedUri(HttpServletRequest request) {
		String recievedUri = request.getRequestURI();
		//                localhost:8080/email/data/{id}?id=123&hash=5w3456
		
		String firstUrn = recievedUri.split("/+")[1];
		if(!urlsMap.containsKey(firstUrn))
			throw new IllegalArgumentException("Bad request");
		String routeUri = urlsMap.get(firstUrn);
		String queryString = request.getQueryString();
		return String.format("%s%s?%s", routeUri, recievedUri, queryString);
		//       http://email-data-provider:8080/email/data/{id}?id=123&hash=5w3456
	}

}
