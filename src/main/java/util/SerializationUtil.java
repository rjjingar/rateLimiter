package util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationUtil {

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // allow access without getters
            //.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    public static String convertToJson(Object obj) {
        try {
            return OBJ_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException", e);
        }
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return OBJ_MAPPER.readValue(json, clazz);
        } catch (JsonMappingException e ) {
            throw new RuntimeException("JsonMappingException", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException", e);
        }
    }
}
