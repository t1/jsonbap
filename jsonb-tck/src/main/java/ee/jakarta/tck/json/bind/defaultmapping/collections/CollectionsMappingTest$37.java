package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.QueueContainer;

import java.util.LinkedList;
import java.util.Queue;

public class CollectionsMappingTest$37 extends QueueContainer {
    {
        Queue<String> instance = new LinkedList<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
