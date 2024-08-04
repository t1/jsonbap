package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.LinkedHashMapContainer;

import java.util.LinkedHashMap;

public class CollectionsMappingTest$25 extends LinkedHashMapContainer {
    {
        LinkedHashMap<String, String> instance = new LinkedHashMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};
        setInstance(instance);
    }
}
