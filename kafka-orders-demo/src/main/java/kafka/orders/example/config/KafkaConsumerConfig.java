package kafka.orders.example.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.kstream.TimeWindowedDeserializer;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	private final KafkaConfig kafkaConfig;

	@Bean
	public ConsumerFactory<String, Object> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getConsumerGroup());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
			KafkaTemplate<String, Object> kafkaTemplate) {

		ConcurrentKafkaListenerContainerFactory<String, Object> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactory());

		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
				(cr, ex) -> new TopicPartition(kafkaConfig.getDlqTopic(), -1));

		factory.setCommonErrorHandler(
				new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3)));

		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}

	@Bean
	public ConsumerFactory<Windowed<String>, Long> aggregatedConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

		TimeWindowedDeserializer<String> keyDeserializer = new TimeWindowedDeserializer<>(
				new StringDeserializer(), Duration.ofMinutes(5).toMillis());

		LongDeserializer valueDeserializer = new LongDeserializer();

		return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Windowed<String>, Long> aggregatedKafkaListenerContainerFactory(
			KafkaTemplate<String, Object> kafkaTemplate) {

		ConcurrentKafkaListenerContainerFactory<Windowed<String>, Long> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(aggregatedConsumerFactory());

		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
				(cr, ex) -> new TopicPartition(kafkaConfig.getDlqTopic(), -1));

		factory.setCommonErrorHandler(
				new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3)));

		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}
}
