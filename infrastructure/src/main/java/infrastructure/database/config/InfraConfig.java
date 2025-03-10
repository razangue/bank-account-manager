package infrastructure.database.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import domain.interfaces.AccountBalanceMessageSender;
import infrastructure.kafka.services.producer.AccountBalanceMessageProducer;

@Configuration
public class InfraConfig {
    @Bean(name = "accountBalanceMessageSender")
    public AccountBalanceMessageSender accountBalanceMessageSender(
            KafkaProducer<String, String> accountBalanceMessageProducer,
            @Value("${account.kafka.input.topic.name}") String topic, ObjectMapper customObjectMapper) {
        return new AccountBalanceMessageProducer(accountBalanceMessageProducer, topic, customObjectMapper);
    }

    @Bean
    public ObjectMapper customObjectMapper(@Value("${local.date.format}") String localDateFormat,
            @Value("${local.date.time.format}") String localDateTimeFormat) {
        ObjectMapper customObjectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Define custom formats
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(localDateFormat);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormat);

        // Register serializers/deserializers
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        // Register the module with the ObjectMapper
        customObjectMapper.registerModule(javaTimeModule);

        // Optional: Configure ObjectMapper to avoid timestamps
        customObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return customObjectMapper;
    }
}
