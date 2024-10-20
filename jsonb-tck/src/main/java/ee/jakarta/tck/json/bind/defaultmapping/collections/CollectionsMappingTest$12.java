package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.TreeSet;

@Bindable
public class CollectionsMappingTest$12 extends TreeSet<String> {
    public CollectionsMappingTest$12(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
