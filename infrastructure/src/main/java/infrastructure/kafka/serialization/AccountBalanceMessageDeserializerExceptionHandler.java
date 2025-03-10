package infrastructure.kafka.serialization;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountBalanceMessageDeserializerExceptionHandler implements DeserializationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceMessageDeserializerExceptionHandler.class);
    @Override
    public DeserializationHandlerResponse handle(ProcessorContext context, ConsumerRecord<byte[], byte[]> record, Exception exception) {
        logger.info("Deserialization error for record with key " + new String(record.key()) +
                " and value " + new String(record.value()) + ": " + exception.getMessage());
        return DeserializationHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}
