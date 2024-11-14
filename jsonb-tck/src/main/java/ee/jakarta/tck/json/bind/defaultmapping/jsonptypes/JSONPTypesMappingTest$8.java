package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonStringContainer;
import jakarta.json.JsonString;

@Bindable(deserializable = false)
public class JSONPTypesMappingTest$8 extends JsonStringContainer {
    public JSONPTypesMappingTest$8(JSONPTypesMappingTest ignoreUnused, JsonString instance) {
        setInstance(instance);
    }
}
