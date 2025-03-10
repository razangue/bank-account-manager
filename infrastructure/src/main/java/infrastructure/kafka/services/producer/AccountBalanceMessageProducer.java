package infrastructure.kafka.services.producer;

import java.time.LocalDateTime;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.interfaces.AccountBalanceMessageSender;
import domain.model.AccountBalanceMessage;

public class AccountBalanceMessageProducer implements AccountBalanceMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceMessageProducer.class);

    private final KafkaProducer<String, String> accountBalanceMessageProducer;
    private final String topic;
    private final ObjectMapper customObjectMapper;

    public AccountBalanceMessageProducer(KafkaProducer<String, String> accountBalanceMessageProducer,
            String topic, ObjectMapper customObjectMapper) {
        this.accountBalanceMessageProducer = accountBalanceMessageProducer;
        this.topic = topic;
        this.customObjectMapper = customObjectMapper;
    }

    @Override
    public void sendAccountBalanceMessage(AccountBalanceMessage accountBalanceMessage) {
        try {
            // Convert the object to a JSON string
            String jsonValue = customObjectMapper.writeValueAsString(accountBalanceMessage);
            ProducerRecord<String, String> message = new ProducerRecord<>(topic,
                    LocalDateTime.now().toString(), jsonValue);

                    accountBalanceMessageProducer.send(message, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Message sent successfully to topic %s, partition %d, offset %d%n",
                            metadata.topic(), metadata.partition(), metadata.offset());
                } else {
                    logger.error("An error occurs when sending message: {}", exception);
                }
            });
        } catch (Exception e) {
            logger.error("An error occurs when sending message: {}", e);
        }
    }
}
