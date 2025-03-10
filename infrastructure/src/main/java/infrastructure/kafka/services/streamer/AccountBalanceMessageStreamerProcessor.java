package infrastructure.kafka.services.streamer;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import domain.model.Client;

@Component
public class AccountBalanceMessageStreamerProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceMessageStreamerProcessor.class);

    public AccountBalanceMessageStreamerProcessor(@Value("${account.kafka.input.topic.name}") String inputTopic,
            @Value("${account.kafka.output.topic.name}") String outputTopic,
            StreamsBuilder streamsBuilder) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "bank.account.group");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Client.class.getName());

        Serde<String> bankAccountSerde = Serdes.String();

        KStream<String, String> stream = streamsBuilder.stream(inputTopic,
                Consumed.with(Serdes.String(), bankAccountSerde));

        stream.to(outputTopic, Produced.with(Serdes.String(), bankAccountSerde));
        logger.info("write account balance message to outputTopic {}", outputTopic);
    }
}
