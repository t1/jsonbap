package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.HashSet;

@Bindable(deserializable = false)
public class CollectionsMappingTest$4 extends HashSet<String> {
    public CollectionsMappingTest$4(CollectionsMappingTest ignoreUnused) {
        add("Test 1");
        add("Test 2");
    }
}
