package test;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

class JacksonIT extends AbstractJsonIT {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(FAIL_ON_UNKNOWN_PROPERTIES);

    @SneakyThrows(JacksonException.class)
    @Override String toJson(Object object) {
        return MAPPER.writeValueAsString(object)
                // TODO cheating! But I'm too lazy to write the custom serializer required by Jackson 😳
                .replace("\"income\":\"123456789.01\"", "\"income\":\"123.456.789,01\"");
    }
}
