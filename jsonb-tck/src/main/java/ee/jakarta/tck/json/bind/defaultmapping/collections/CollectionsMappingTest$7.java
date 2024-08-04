package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.HashSetContainer;

import java.util.HashSet;

public class CollectionsMappingTest$7 extends HashSetContainer {
    {
        HashSet<String> instance = new HashSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
