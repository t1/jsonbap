package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.TreeMapContainer;

import java.util.TreeMap;

public class CollectionsMappingTest$23 extends TreeMapContainer {
    {
        TreeMap<String, String> instance = new TreeMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};
        setInstance(instance);
    }
}
