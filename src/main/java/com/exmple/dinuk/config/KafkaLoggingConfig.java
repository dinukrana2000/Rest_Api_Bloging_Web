package com.exmple.dinuk.config;

import ch.qos.logback.classic.Logger;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaLoggingConfig {


    @Value("${kafca.nodes}")
    private String bootstrapServers;

    @Value("${app.logging.kafka.topic}")
    private String topic;


    @Bean
    public Map<String, Object> producerConfigs() {

        Map<String, Object> prop = new HashMap<>();
        System.out.println(bootstrapServers);
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put("enable.auto.commit", "true");
        prop.put("auto.commit.interval.ms", "1000");
        prop.put("request.timeout.ms", "50000");
        prop.put("security.protocol", "PLAINTEXT");
        prop.put("compression.type", "lz4");
        prop.put("linger.ms","5");
        prop.put("acks", "all");

        return prop;
    }


    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> kafkaLogbackInitializer(KafkaTemplate<String, String> kafkaTemplate) {
        return event -> {
            KafkaLogbackAppender kafkaAppender = new KafkaLogbackAppender();
            kafkaAppender.setKafkaTemplate(kafkaTemplate);
            kafkaAppender.setTopic(topic);
            kafkaAppender.setName("CUSTOM_KAFKA");
            kafkaAppender.start();

            Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(kafkaAppender);
        };
    }
}
