package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.MapContainer;

import java.util.HashMap;
import java.util.Map;

public class CollectionsMappingTest$3 extends MapContainer {
    {
        Map<String, String> instance = new HashMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};

        setInstance(instance);
    }
}
