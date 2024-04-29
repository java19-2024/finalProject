package telran.probes.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ProbesGeneratorAppl {

	public static void main(String[] args) {
		ConfigurableApplicationContext cac = SpringApplication.run(ProbesGeneratorAppl.class, args);
		try {
			Thread.sleep(30000);
			cac.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
