package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.SortedSetContainer;

import java.util.SortedSet;
import java.util.TreeSet;

public class CollectionsMappingTest$11 extends SortedSetContainer {
    {
        SortedSet<String> instance = new TreeSet<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
