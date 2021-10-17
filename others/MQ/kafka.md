# tips:
1. how to send
  ```
  Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
		
		
		KafkaProducer<String, Integer> producer = new KafkaProducer<String, Integer>(props);
		ProducerRecord<String, Integer> record = new ProducerRecord<>("OrderTopic", "Mac Book Pro", 10);
        producer.send(record, callback)
  ```

2. how to subscribe
```
Properties props = new Properties();
			props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
			props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
		 
 
			props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

			KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Collections.singletonList("OrderPartitionedTopic"));

			ConsumerRecords<String, Integer> orders = consumer.poll(Duration.ofSeconds(50));

```
3. how to read from begin
```
// 
  props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "new_group_id");
  props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
```

4. how to use custome serializer/deserializer 

```
implement Serializer/Deserializer
```



6. use avro as Serializer/Deserializer

    1. start a local cluster using confluent:    ` confluent local services start`
    2. access schema registry: localhost:8081/schemas
    3. `props.setProperty("schema.registry.url", "http://localhost:8081");`
    4. generate avro with maven plugin
    ```
    <plugin>
        <groupId>org.apache.avro</groupId>
        <artifactId>avro-maven-plugin</artifactId>
        <version>1.10.2</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals>
                    <goal>schema</goal>
                </goals>
                <configuration>
                    <sourceDirectory>${project.basedir}/src/main/resources/</sourceDirectory>
                    <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>

    ```

7. tranaction
```
    props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "order-producer-1");
    producer.initTransactions()   
    try {
			producer.beginTransaction();
			producer.send(record,new OrderCallback());
			producer.send(record2);
			producer.commitTransaction();
			
		} catch (Exception e) {
			producer.abortTransaction();
			e.printStackTrace();
		} finally {
			producer.close();
		}
```

# import config
```
 		props.setProperty(ProducerConfig.ACKS_CONFIG, "all");
 		props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "343434334");
 		props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
 		props.setProperty(ProducerConfig.RETRIES_CONFIG, "2");
 		props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "400");
 		props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "10243434343");
 		props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "500");
 		props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "200");

```

# offset commit 
1.  autocommit: props.setProperty("auto.commit.interval.ms", 2000)

2.  turn off auto commit: `props.setProperty("auto.commit.offset": "false")`
 1. cosumer.commitSync();
 2. cosumer.commitAsnc();