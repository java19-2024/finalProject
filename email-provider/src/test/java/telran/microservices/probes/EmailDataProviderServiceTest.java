package telran.microservices.probes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.service.IDataProvider;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@Sql(scripts="db-test-script.sql")
public class EmailDataProviderServiceTest {

	private static final long SENSOR_ID_EXIST = 100000;
	private static final long SENSOR_ID_NOT_EXIST = 100001;
	private static final String ERROR_MESSAGE = "no person for sensor id="+SENSOR_ID_NOT_EXIST;
	@Autowired
	IDataProvider service;
	
	@Test
	void emailDataExistTest() {
	EmailData exp = new EmailData(new String[] {"vasya@mail.ru", "sara@gmail.com"},
			new String[] {"Vasya", "Sara"});

	EmailData actual = service.getEmailData(SENSOR_ID_EXIST);
	System.out.println(actual);
	assertArrayEquals(exp.emails(), actual.emails());
	assertArrayEquals(exp.names(), actual.names());
}
	@Test
	void emailDataNotExistTest() {
		assertThrowsExactly(NotFoundException.class, () ->
		service.getEmailData(SENSOR_ID_NOT_EXIST), ERROR_MESSAGE);
	}
}
