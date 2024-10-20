package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.LinkedList;

@Bindable
public class CollectionsMappingTest$36 extends LinkedList<String> {
    public CollectionsMappingTest$36(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
