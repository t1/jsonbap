package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.ArrayDeque;

@Bindable
public class CollectionsMappingTest$32 extends ArrayDeque<String> {
    public CollectionsMappingTest$32(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
