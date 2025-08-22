package com.exmple.dinuk.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class KafkaLogbackAppender extends AppenderBase<ILoggingEvent> {

    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (kafkaTemplate != null && eventObject.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.ERROR)) {
            try {
                kafkaTemplate.send(topic, eventObject.getFormattedMessage())
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("Failed to send log message to Kafka topic {}: {}", topic, ex.getMessage());
                            }

                        });

            } catch (Exception ex) {
                log.error("Error while sending log message to Kafka topic {}: {}", topic, ex.getMessage());
            }
        }
    }
}
