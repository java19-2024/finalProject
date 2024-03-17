package telran.microservices.probes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.service.IDataProvider;

@SpringBootTest
@Sql(scripts="db-test-script.sql")
public class EmailDataProviderServiceTest {

	private static final long SENSOR_ID_EXIST = 10000;
	private static final long SENSOR_ID_NOT_EXIST = 10001;
	private static final String ERROR_MESSAGE = "no person for sensor id="+SENSOR_ID_NOT_EXIST;
	@Autowired
	IDataProvider service;
	
	@Test
	void emailDataExistTest() {
	EmailData exp = new EmailData(new String[] {"vasya@mail.ru", "sara@gmail.com"},
			new String[] {"Vasya", "Sara"});
	assertEquals(exp,  service.getEmailData(SENSOR_ID_EXIST));
}
	@Test
	void emailDataNotExistTest() {
		assertThrowsExactly(IllegalArgumentException.class, () ->
		service.getEmailData(SENSOR_ID_NOT_EXIST), ERROR_MESSAGE);
	}
}
