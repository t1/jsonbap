package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.LocalDateTimeContainer;

import java.time.LocalDateTime;

@Bindable
public class IJsonSupportTest$5 extends LocalDateTimeContainer {
    public IJsonSupportTest$5(IJsonSupportTest ignoreUnused) {
        setInstance(LocalDateTime.of(1970, 1, 1, 1, 1, 1));
    }
}
