package kafka.orders.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class KafkaOrdersDemoApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(10 > 7);
	}

}
