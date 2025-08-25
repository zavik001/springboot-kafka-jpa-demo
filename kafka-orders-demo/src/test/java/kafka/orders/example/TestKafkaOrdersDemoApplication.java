package kafka.orders.example;

import org.springframework.boot.SpringApplication;

public class TestKafkaOrdersDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaOrdersDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
