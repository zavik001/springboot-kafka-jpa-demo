package kafka.orders.example.example;

// Spring for Apache Kafka
// ├── Что это:
// │ ├── Фреймворк для интеграции Apache Kafka с Spring-приложениями
// │ ├── Абстрагирует низкоуровневые детали Kafka API
// │ ├── Работает с распределёнными потоками данных и событиями
// │ ├── Поддерживает producer, consumer, streams, admin
// │ └── Интегрируется с Spring Boot для автоконфигурации
// ├── История версий (от начала до августа 2025):
// │ ├── Начало: 2014 как часть Spring Integration Kafka
// │ ├── 1.0.0 (2016): Первый релиз, базовый producer/consumer
// │ ├── 1.1.x (2017): Улучшения в error handling, partitioning
// │ ├── 1.2.x (2017): Поддержка Kafka 0.11, headers в messages
// │ ├── 1.3.x (2018): Интеграция с Spring Boot 2.0
// │ ├── 2.0.x (2018): Kafka Streams API, reactive support
// │ ├── 2.1.x (2018): Улучшения в batch processing
// │ ├── 2.2.x (2019): Поддержка Kafka 2.1, transactions
// │ ├── 2.3.x (2019): Micrometer metrics, pause/resume
// │ ├── 2.4.x (2020): Kafka 2.4 support, record filters
// │ ├── 2.5.x (2020): Spring Boot 2.3, improved error handlers
// │ ├── 2.6.x (2021): Kafka 2.7, async replies
// │ ├── 2.7.x (2021): Reactive KafkaTemplate, Boot 2.5
// │ ├── 2.8.x (2022): Kafka 3.0, KRaft support (без ZooKeeper)
// │ ├── 2.9.x (2022): Улучшения в streams, Boot 2.7
// │ ├── 3.0.x (2023): Java 17, Spring Boot 3.0, Kafka 3.3
// │ ├── 3.1.x (2023): Enhanced consumer awareness
// │ ├── 3.2.x (2024): Kafka 3.6 support, improved testing
// │ ├── 4.0.0-M1 (мар 2025): Spring Boot 3.3, Kafka 4.0
// │ ├── 4.0.0-M2 (июл 2025): Bug fixes, reactive enhancements
// │ ├── 4.0.0-M3 (авг 2025): Dependency upgrades, new metrics
// │ ├── 4.0.0-M4 (авг 2025): Final milestone перед релизом
// │
// ├── Основные компоненты:
// │ ├── KafkaTemplate: Для отправки сообщений (producer)
// │ ├── @KafkaListener: Аннотация для consumers
// │ ├── KafkaAdmin: Управление topics, partitions
// │ ├── KafkaStreams: Потоковая обработка данных
// │ └── ConsumerFactory/ProducerFactory: Конфигурация
// ├── Как работает:
// │ ├── Интеграция через Spring Boot starters
// │ ├── Автоконфигурация с application.yml/properties
// │ ├── Под капотом использует kafka-clients library
// │ ├── Поддержка транзакций, error handling, retries
// │ └── Работает поверх Apache Kafka API
// └── Разница подходов:
// ├── Низкоуровневый: Прямое использование KafkaProducer/Consumer
// └── Декларативный: Аннотации + templates в Spring стиле

// ↓

// Apache Kafka (основа для Spring Kafka)
// ├── Что это:
// │ ├── Распределённая платформа для стриминга событий
// │ ├── Разработана LinkedIn (2010), open-source в 2011
// │ ├── Используется для real-time data pipelines
// │ ├── Высокая пропускная способность, низкая задержка
// │ └── Fault-tolerant, scalable, durable storage
// ├── История версий (от начала до августа 2025):
// │ ├── 0.7.x (2012): Базовые topics, producers/consumers
// │ ├── 0.8.x (2014): Replication, offset management
// │ ├── 0.9.x (2015): Consumer groups, security
// │ ├── 0.10.x (2016): Kafka Streams, timestamps
// │ ├── 0.11.x (2017): Exactly-once semantics
// │ ├── 1.0.x (2017): Java 9 support, admin client
// │ ├── 1.1.x (2018): Zookeeper security
// │ ├── 2.0.x (2018): TLS, JBOD storage
// │ ├── 2.1.x (2018): Incremental rebalancing
// │ ├── 2.2.x (2019): Kafka Connect improvements
// │ ├── 2.3.x (2019): MirrorMaker 2
// │ ├── 2.4.x (2020): Consumer offsets in topics
// │ ├── 2.5.x (2020): Leader election improvements
// │ ├── 2.6.x (2020): KRaft preview (без ZooKeeper)
// │ ├── 2.7.x (2021): Incremental cooperative rebalance
// │ ├── 2.8.x (2021): KRaft early access
// │ ├── 3.0.x (2022): KRaft production-ready, ZooKeeper removal
// │ ├── 3.1.x (2022): Fetch from closest replica
// │ ├── 3.2.x (2022): Kafka Streams rocksDB upgrades
// │ ├── 3.3.x (2022): Stronger consistency
// │ ├── 3.4.x (2023): Metadata improvements
// │ ├── 3.5.x (2023): Tiered storage preview
// │ ├── 3.6.x (2023): Client quota enforcement
// │ ├── 3.7.x (2024): JBOD improvements, KRaft enhancements
// │ ├── 3.8.x (2024): Virtual clusters
// │ ├── 3.9.x (май 2025): Bug fixes, performance tweaks
// │ ├── 4.0.0 (мар 2025): Major release, full KRaft, no ZooKeeper
// │
// ├── Основные компоненты:
// │ ├── Topics: Категории для сообщений, immutable logs
// │ ├── Partitions: Разделы topics для параллелизма
// │ ├── Producers: Отправители сообщений в topics
// │ ├── Consumers: Получатели, читают из partitions
// │ ├── Consumer Groups: Группы для load balancing
// │ ├── Brokers: Серверы, хранят данные, управляют
// │ ├── Replication: Копии partitions для fault-tolerance
// │ ├── Offsets: Позиции чтения в partitions
// │ └── Leaders/Followers: Роли в replication
// ├── Как работает:
// │ ├── Producers пишут в leader partitions
// │ ├── Brokers координируют через KRaft (с 3.0)
// │ ├── Consumers pull data, manage offsets
// │ ├── Replication синхронизирует followers
// │ ├── Scaling: Добавление brokers/partitions
// │ └── Durability: Acks, min.insync.replicas
// ├── ZooKeeper vs KRaft:
// │ ├── ZooKeeper (до 3.0): Координация metadata
// │ └── KRaft (с 2.8, full в 3.0+): Встроенный consensus
// └── Применение:
// └── Real-time processing, event sourcing, logging

// ↓

// Producers в Spring Kafka
// ├── Что это:
// │ ├── Компонент для отправки сообщений в Kafka topics
// │ ├── В Spring: KafkaTemplate<T,K,V>
// │ └── Поддержка sync/async отправки, partitioning
// ├── Возможности:
// │ ├── send(topic, key, value)
// │ ├── Transactions: @Transactional или producer fences
// │ ├── Error handling: SeekToCurrentErrorHandler
// │ ├── Serialization: JsonSerializer, Avro и т.д.
// │ └── Partitioning: Default or custom strategies
// ├── Как работает:
// │ ├── ProducerFactory создаёт KafkaProducer
// │ ├── Template оборачивает producer API
// │ ├── Под капотом: kafka-clients library
// │ └── Интеграция: Bootstrap servers из config
// └── Зависит от:
// └── Apache Kafka Producer API

// ↓

// Consumers в Spring Kafka
// ├── Что это:
// │ ├── Компонент для чтения сообщений из topics
// │ ├── В Spring: @KafkaListener на methods
// │ └── Поддержка batch, single record processing
// ├── Возможности:
// │ ├── containerProperties для config
// │ ├── Acknowledgment: Manual или auto commit
// │ ├── Retries: NonBlockingRetries
// │ ├── Deserialization: JsonDeserializer и т.д.
// │ ├── Pause/Resume: Для backpressure
// │ └── Dead Letter Topics: Для failed messages
// ├── Как работает:
// │ ├── ListenerContainerFactory создаёт consumers
// │ ├── Polling из partitions, group management
// │ ├── Под капотом: KafkaConsumer API
// │ └── Rebalancing: Cooperative protocol (с 2.3+)
// └── Зависит от:
// └── Apache Kafka Consumer API

// ↓

// Topics и Partitions
// ├── Что это:
// │ ├── Topics: Логические каналы для сообщений
// │ ├── Partitions: Физические сегменты topics
// │ └── Ключи определяют partition (hash)
// ├── Управление в Spring:
// │ ├── KafkaAdmin: NewTopic для создания
// │ ├── @Bean для declarative creation
// │ └── Config: Num partitions, replication factor
// ├── Как работает:
// │ ├── Брокеры хранят partitions
// │ ├── Replication обеспечивает availability
// │ └── Scaling: Reassign partitions
// └── Эволюция:
// └── С 0.8: Добавлена replication

// ↓

// Kafka Streams (в Spring Kafka)
// ├── Что это:
// │ ├── Библиотека для обработки потоков данных
// │ ├── В Spring: @EnableKafkaStreams
// │ └── Stateful/stateless processing
// ├── Возможности:
// │ ├── KStream, KTable, GlobalKTable
// │ ├── Joins, aggregations, windows
// │ ├── Exactly-once semantics
// │ └── Interactive queries
// ├── Как работает:
// │ ├── StreamsBuilder для topology
// │ ├── Интеграция с Spring config
// │ └── Под капотом: Kafka Streams API
// └── Эволюция:
// └── Введено в Kafka 0.10 (2016)

// ↓

// Конфигурация и Best Practices
// ├── Bootstrap.servers: Список brokers
// ├── Serialization: Key/value serializers
// ├── Security: SSL, SASL, ACLs (с Kafka 0.9)
// ├── Monitoring: Micrometer, JMX metrics
// ├── Testing: @EmbeddedKafka для unit tests
// └── When to use: High-throughput events, microservices

public class KafkaExample {

}
