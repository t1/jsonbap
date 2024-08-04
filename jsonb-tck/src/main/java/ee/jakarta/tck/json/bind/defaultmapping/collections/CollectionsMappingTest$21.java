package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.SortedMapContainer;

import java.util.SortedMap;
import java.util.TreeMap;

public class CollectionsMappingTest$21 extends SortedMapContainer {
    {
        SortedMap<String, String> instance = new TreeMap<>() {{
            put("string1", "Test 1");
            put("string2", "Test 2");
        }};
        setInstance(instance);
    }
}
