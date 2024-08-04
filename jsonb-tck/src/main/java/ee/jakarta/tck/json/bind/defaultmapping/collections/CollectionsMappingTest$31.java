package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.LinkedListContainer;

import java.util.LinkedList;

public class CollectionsMappingTest$31 extends LinkedListContainer {
    {
        LinkedList<String> instance = new LinkedList<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
