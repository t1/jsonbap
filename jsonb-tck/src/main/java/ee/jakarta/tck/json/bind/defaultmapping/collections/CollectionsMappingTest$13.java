package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.TreeSetContainer;

import java.util.TreeSet;

public class CollectionsMappingTest$13 extends TreeSetContainer {
    {
        TreeSet<String> instance = new TreeSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
