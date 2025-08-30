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



// Apache Kafka работает на низком уровне как распределенная система для стриминга данных, где
// ключевыми элементами являются брокеры (серверы), которые хранят и управляют данными, клиенты
// (продюсеры и консьюмеры), общающиеся с ними по сети.

// 1. Общая архитектура и сетевой протокол
// Kafka использует бинарный протокол поверх TCP для общения между клиентами (продюсерами,
// консьюмерами) и брокерами. Это не HTTP или что-то текстовое — чистый бинарный формат для высокой
// производительности и низкой задержки. Протокол называется Kafka Protocol, и он request-response:
// клиент отправляет запрос, брокер отвечает.

// - Как работает соединение: Клиенты устанавливают persistent TCP-соединения с брокерами (без
// handshake, просто connect). Соединения держатся открытыми, чтобы избежать overhead от повторных
// подключений. По одному соединению может быть только один in-flight request (для сохранения
// порядка), но клиенты используют non-blocking I/O, чтобы параллельно обрабатывать несколько
// соединений. Если нужно, клиент может pipelining'овать запросы, но Kafka рекомендует ограничить
// in-flight, чтобы избежать out-of-order.

// - Формат сообщений в протоколе: Все запросы и ответы — это size-delimited бинарные blobs.
// Структура: 4 байта на размер (INT32), за ним само сообщение. Примитивы: INT8/16/32/64
// (big-endian), STRING (длина INT16 + байты UTF-8), COMPACT_STRING (unsigned varint для длины +
// байты), BYTES, ARRAY и т.д. Для переменных длин — zig-zag encoding (как в Protocol Buffers).
// Сообщения поддерживают тегированные поля (tagged fields с KIP-482) для эволюции схемы без ломания
// совместимости.

// - Версионирование: Каждый запрос имеет api_key (INT16, например 0 для Produce) и api_version
// (INT16). Клиент сначала шлет ApiVersionsRequest (key 18), чтобы узнать, какие версии поддерживает
// брокер, и выбирает максимальную общую. Это обеспечивает bidirectional compatibility: новый клиент
// работает со старым брокером и наоборот.

// - Обработка ошибок: Ответы содержат error_code (INT16, например -1 UNKNOWN_SERVER_ERROR, 3
// UNKNOWN_TOPIC_OR_PARTITION). Ошибки делятся на retriable (например, NOT_LEADER_OR_FOLLOWER —
// клиент рефрешит метадату и retry) и non-retriable (например, UNSUPPORTED_VERSION). Клиенты
// автоматически retry на retriable ошибках.

// Протокол эволюционировал: с 0.8 добавлена репликация, с 0.11 — exactly-once, с 2.0 — улучшения
// TLS, с 3.0 — полный KRaft (без ZooKeeper).



// 2. Как работают продюсеры (производители сообщений)
// Продюсер — это клиент, который отправляет сообщения в топики.

// - Поток отправки: Продюсер не шлет сразу — аккумулирует в батчах (batch.size ~16KB по
// умолчанию, linger.ms для задержки). Ключ сообщения хэшируется (murmur2), чтобы определить
// партицию: partition = hash(key) % num_partitions. Если нет ключа — round-robin.

// - Низкоуровневый запрос: Использует ProduceRequest (api_key 0). Структура (пример v9):
// transactional_id (COMPACT_NULLABLE_STRING), acks (INT16: 0=fire-and-forget, 1=leader ack, -1=all
// ISR), timeout_ms (INT32), topic_data (массив: topic name + массив partition_data: index INT32 +
// records COMPACT_RECORDS). Records — батч сообщений, сжатый (gzip/snappy/lz4/zstd) если указано.

// - Под капотом: Продюсер шлет на leader-брокер партиции (узнает из MetadataRequest, api_key
// 3). Брокер append'ит к логу, реплицирует на followers. Acks определяет, когда считать успешным:
// для durability — all + min.insync.replicas >1. Если error — retry с backoff.

// - Транзакции: С 0.11 — idempotent producers (transactional.id для fencing zombies).
// BeginTransaction, send, commitTransaction — атомарно, с маркерами в логе.

// Это обеспечивает высокую throughput: батчинг снижает I/O, компрессия — сеть.



// 3. Хранение сообщений (где и как хранятся)
// Сообщения хранятся на дисках брокеров в append-only логах. Топик — логическая категория,
// разделенная на партиции (для параллелизма). Каждая партиция — immutable последовательность
// записей.

// - Формат хранения: Партиция — директория на диске (например, /kafka-logs/topic-partition/).
// Внутри — сегменты: файлы вроде 00000000000000000000.log (base offset в имени). Сегмент —
// append-only, роллится по размеру (1GB default, log.segment.bytes) или времени (log.roll.hours).
// Каждый сегмент имеет индексы: .index (offset -> physical pos), .timeindex (timestamp -> offset
// для поиска по времени).

// - Структура записи: Message: offset (INT64), size (INT32), attributes (INT8: compression
// type), timestamp delta (VARLONG), key length (VARINT), key, value length, value, headers (ARRAY).
// Батчи сообщений — для efficiency.

// - Очистка (cleanup): Политики — delete (по времени/размеру, log.retention.hours/ms/bytes) или
// compact (log.cleanup.policy=compact). Compaction: background thread удаляет старые значения по
// ключу, оставляя latest (как key-value store). Использует cleaner-offset-checkpoint для трекинга.

// - Durability: Не fsync каждый раз — полагается на OS page cache + replication. Flush по
// log.flush.interval.messages/ms. Если crash — recovery из лога.

// Хранение оптимизировано: sequential writes/reads, zero-copy (sendfile для fetch), mmap для
// индексов.



// 4. Брокеры и репликация (под капотом кластера)
// Брокеры — JVM-процессы, хранящие партиции.

// - Репликация: Каждая партиция имеет replication.factor (например 3). Одна — leader (handles
// reads/writes), остальные — followers (fetch от leader). Followers синхронизируют via FetchRequest
// (как консьюмеры, но internal).

// - ISR (In-Sync Replicas): Подмножество реплик, в sync с leader (lag <
// replica.lag.time.max.ms). Write succeeds, если ack от min.insync.replicas (включая leader).
// High-watermark — max offset, committed ко всем ISR.

// - Лидер-элекшн: В KRaft (с 3.0) — Raft-based controller. Старый — ZooKeeper. Если leader
// down, controller выбирает new leader из ISR, уведомляет via LeaderAndIsrRequest.

// - Координация: Metadata в KRaft (Raft quorum) или ZooKeeper (old). Брокеры heartbeat'уют,
// чтобы детектить failures.

// Fault-tolerance: Если брокер down, reassignment партиций на другие.



// 5. Как работают консьюмеры (потребители)
// Консьюмер — pull-model: сам запрашивает данные.

// - Запрос: FetchRequest (api_key 1). Структура (v13): max_wait_ms (INT32), min_bytes (для
// batching), topics (topic_id UUID + partitions: fetch_offset INT64, etc.). Ответ:
// throttle_time_ms, responses с records (COMPACT_RECORDS).

// - Offsets: Консьюмер трекает offset per partition. Commit в __consumer_offsets (special
// topic). Auto-commit или manual.

// - Rebalancing: Когда join/leave group, GroupCoordinator (брокер) redistributes partitions
// (strategies: range, round-robin). Cooperative с 2.3 — incremental.

// - Под капотом: Poll loop: fetch batches (max.poll.records), process, commit. Isolation:
// read_committed для транзакций (игнорит aborted).
public class KafkaExample {

}
