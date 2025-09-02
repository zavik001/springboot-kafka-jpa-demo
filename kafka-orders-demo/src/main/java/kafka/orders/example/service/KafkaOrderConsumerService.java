package kafka.orders.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaOrderConsumerService {

	@KafkaListener(topics = "#{@kafkaConfig.getAggregatedTopic()}",
			groupId = "#{@kafkaConfig.getAggregatedGroup()}",
			containerFactory = "aggregatedKafkaListenerContainerFactory")
	public void consumeAggregated(Long aggregated,
			@Header(KafkaHeaders.RECEIVED_KEY) Windowed<String> key, Acknowledgment ack) {
		log.info("Aggregated data for customer {} in window {}-{}: {}", key.key(),
				key.window().start(), key.window().end(), aggregated);
		ack.acknowledge();
	}

	@KafkaListener(topics = "#{@kafkaConfig.getDlqTopic()}",
			groupId = "#{@kafkaConfig.getDlqGroup()}")
	public void consumeDlq(ConsumerRecord<String, Object> record, Acknowledgment ack) {
		log.warn("DLQ message: key={}, value={}", record.key(), record.value());
		ack.acknowledge();
	}
}
