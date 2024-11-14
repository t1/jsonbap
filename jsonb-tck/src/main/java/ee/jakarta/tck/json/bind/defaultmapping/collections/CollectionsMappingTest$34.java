package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.ArrayDeque;

@Bindable(deserializable = false)
public class CollectionsMappingTest$34 extends ArrayDeque<String> {
    public CollectionsMappingTest$34(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
