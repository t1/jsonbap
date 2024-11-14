package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonObjectContainer;
import jakarta.json.JsonObject;

@Bindable(deserializable = false)
public class JSONPTypesMappingTest$2 extends JsonObjectContainer {
    public JSONPTypesMappingTest$2(JSONPTypesMappingTest ignoreUnused, JsonObject instance) {
        setInstance(instance);
    }
}
