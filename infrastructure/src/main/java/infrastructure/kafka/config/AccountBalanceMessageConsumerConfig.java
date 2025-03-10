package infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import domain.model.Client;
import infrastructure.kafka.serialization.AccountBalanceMessageDeserializerExceptionHandler;

@Configuration
public class AccountBalanceMessageConsumerConfig {
    @Bean
    public KafkaConsumer<String, Client> kafkaConsumer(
            @Value("${spring.kafka.bootstrap-servers}") String serverConfig,
            @Value("${spring.kafka.consumer.group-id}") String groupId) {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "infrastructure.kafka.serialization.CustomDeserializer");
        props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
                AccountBalanceMessageDeserializerExceptionHandler.class.getName());
        props.put("target.type", Client.class); // Pass the target type for deserialization
        props.put("auto.offset.reset", "earliest");
        return new KafkaConsumer<>(props);
    }
}
