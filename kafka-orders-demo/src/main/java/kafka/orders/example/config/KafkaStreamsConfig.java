package kafka.orders.example.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.WindowedSerdes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import kafka.orders.example.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

	private final KafkaConfig kafkaConfig;

	@Bean
	public KStream<String, OrderResponseDto> orderStream(StreamsBuilder builder) {
		JsonSerde<OrderResponseDto> orderSerde = new JsonSerde<>(OrderResponseDto.class);
		orderSerde.deserializer().setUseTypeHeaders(false);
		orderSerde.deserializer().addTrustedPackages("*");

		KStream<String, OrderResponseDto> stream = builder.stream(kafkaConfig.getOrdersTopic(),
				Consumed.with(Serdes.String(), orderSerde));

		stream.groupBy((key, value) -> value.customerName())
				.windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(5), Duration.ZERO))
				.aggregate(() -> 0L, (key, value, aggregate) -> aggregate + value.amount(),
						Materialized.with(Serdes.String(), Serdes.Long()))
				.toStream().peek((key, value) -> {
					if (value > 1000) {
						log.info("Potential fraud: Customer {} spent {} in 5 min", key.key(),
								value);
					}
				}).to(kafkaConfig.getAggregatedTopic(),
						Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class,
								Duration.ofMinutes(5).toMillis()), Serdes.Long()));

		return stream;
	}

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getOrdersStreams());
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
				Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new KafkaStreamsConfiguration(props);
	}
}
