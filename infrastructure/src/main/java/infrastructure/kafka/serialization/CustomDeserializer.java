package infrastructure.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer<T> implements Deserializer<T> {

    private Class<T> targetType;
    @Autowired
    private ObjectMapper customObjectMapper;

    public CustomDeserializer(){}
    // Constructor to specify the target class
    public CustomDeserializer(Class<T> targetType, ObjectMapper customObjectMapper) {
        this.targetType = targetType;
        this.customObjectMapper = customObjectMapper;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configure the deserializer (if needed)
        if (configs.containsKey("target.type")) {
            this.targetType = (Class<T>) configs.get("target.type");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null; // Handle null or empty payload
        }
        try {
            // Deserialize JSON to the target class
            return customObjectMapper.readValue(data, targetType);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON to " + targetType.getName(), e);
        }
    }

    @Override
    public void close() {
        // Cleanup resources (if any)
    }
}