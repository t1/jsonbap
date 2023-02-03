package test;

import com.github.t1.jsonbap.test.Person;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class JsonIterIT extends AbstractJsonIT {
    static {
        JsonIterator.setMode(DecodingMode.STATIC_MODE);
        JsonStream.setMode(EncodingMode.STATIC_MODE);
    }

    @Override String toJson(Object object) {
        return JsonStream.serialize(object);
    }

    @Test
    void shouldSerialize() {
        // I didn't find out how to generate code to serialize an arraylist
        var json = toJson(PERSON);

        // the field order in jsoniter is not deterministic
        then(json)
            .startsWith("{")
            .contains(
                "\"address\":{",
                /**/"\"street\":\"12001 Main Street\"",
                /**/"\"zip\":50001",
                /**/"\"city\":\"Somewhere-1\"",
                "}",
                "\"age\":13",
                "\"firstName\":\"Jane-1\"",
                "\"lastName\":\"Doe-1\"")
            .endsWith("}");
    }

    private static final Person PERSON = person(1);
}
