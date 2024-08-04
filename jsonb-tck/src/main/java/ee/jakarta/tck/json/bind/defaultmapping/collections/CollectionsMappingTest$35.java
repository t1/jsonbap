package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.ArrayDequeContainer;

import java.util.ArrayDeque;

public class CollectionsMappingTest$35 extends ArrayDequeContainer {
    {
        ArrayDeque<String> instance = new ArrayDeque<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
