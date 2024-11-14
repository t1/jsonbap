package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.PriorityQueue;

@Bindable(deserializable = false)
public class CollectionsMappingTest$38 extends PriorityQueue<String> {
    public CollectionsMappingTest$38(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
