package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.LinkedHashSetContainer;

import java.util.LinkedHashSet;

public class CollectionsMappingTest$15 extends LinkedHashSetContainer {
    {
        LinkedHashSet<String> instance = new LinkedHashSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
