package infrastructure.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomSerializer<T> implements Serializer<T> {
    private static final Logger logger = LoggerFactory.getLogger(CustomSerializer.class);
    private final ObjectMapper customObjectMapper;

    public CustomSerializer(ObjectMapper customObjectMapper) {
        this.customObjectMapper = customObjectMapper;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            logger.info("Start serialize object, data: {}", data);
            if (data == null) {
                return null;
            }
            return customObjectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("An error occurs when serializing object, cause : {}", e);
        }
    }

    @Override
    public void close() {

    }
}
