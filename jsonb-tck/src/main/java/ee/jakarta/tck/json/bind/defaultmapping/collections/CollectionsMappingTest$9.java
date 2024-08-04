package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.NavigableSetContainer;

import java.util.NavigableSet;
import java.util.TreeSet;

public class CollectionsMappingTest$9 extends NavigableSetContainer {
    {
        NavigableSet<String> instance = new TreeSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
