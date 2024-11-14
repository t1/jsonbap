package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.LinkedList;

@Bindable(deserializable = false)
public class CollectionsMappingTest$30 extends LinkedList<String> {
    public CollectionsMappingTest$30(CollectionsMappingTest ignore) {
        add("Test 1");
        add("Test 2");
    }
}
