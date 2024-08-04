package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.MapContainer;

import java.util.HashMap;

public class CollectionsMappingTest$17 extends MapContainer {
    {
        HashMap<String, String> instance = new HashMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};
        setInstance(instance);

    }
}
