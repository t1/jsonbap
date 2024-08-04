package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.DequeContainer;

import java.util.ArrayDeque;
import java.util.Deque;

public class CollectionsMappingTest$33 extends DequeContainer {
    {
        Deque<String> instance = new ArrayDeque<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
