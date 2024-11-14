package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.LocalDateContainer;

import java.time.LocalDate;

@Bindable(deserializable = false)
public class IJsonSupportTest$4 extends LocalDateContainer {
    public IJsonSupportTest$4(IJsonSupportTest ignoreUnused) {
        setInstance(LocalDate.of(1970, 1, 1));
    }
}
