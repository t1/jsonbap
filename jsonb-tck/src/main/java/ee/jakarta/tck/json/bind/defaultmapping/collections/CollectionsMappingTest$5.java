package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.SetContainer;

import java.util.HashSet;

public class CollectionsMappingTest$5 extends SetContainer {
    {
        HashSet<String> instance = new HashSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
