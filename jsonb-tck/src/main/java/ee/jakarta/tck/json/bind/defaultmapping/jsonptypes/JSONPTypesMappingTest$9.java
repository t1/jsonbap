package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonNumberContainer;
import jakarta.json.JsonNumber;

@Bindable(deserializable = false)
public class JSONPTypesMappingTest$9 extends JsonNumberContainer {
    public JSONPTypesMappingTest$9(JSONPTypesMappingTest ignoreUnused, JsonNumber instance) {
        setInstance(instance);
    }
}
