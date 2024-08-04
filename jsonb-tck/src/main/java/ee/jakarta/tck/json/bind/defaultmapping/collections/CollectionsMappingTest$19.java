package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.NavigableMapContainer;

import java.util.NavigableMap;
import java.util.TreeMap;

public class CollectionsMappingTest$19 extends NavigableMapContainer {
    {
        NavigableMap<String, String> instance = new TreeMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};
        setInstance(instance);
    }
}
