package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonArrayContainer;
import jakarta.json.JsonArray;

@Bindable(deserializable = false)
public class JSONPTypesMappingTest$4 extends JsonArrayContainer {
    public JSONPTypesMappingTest$4(JSONPTypesMappingTest ignoreUnused, JsonArray instance) {
        setInstance(instance);
    }
}
