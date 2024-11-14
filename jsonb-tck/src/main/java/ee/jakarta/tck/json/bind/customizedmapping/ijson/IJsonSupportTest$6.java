package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.InstantContainer;

import java.time.Instant;

@Bindable(deserializable = false)
public class IJsonSupportTest$6 extends InstantContainer {
    public IJsonSupportTest$6(IJsonSupportTest ignoreUnused) {
        setInstance(Instant.ofEpochMilli(0));
    }
}
