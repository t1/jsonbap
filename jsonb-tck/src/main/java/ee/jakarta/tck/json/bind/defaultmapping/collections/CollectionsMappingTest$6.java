package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.HashSet;

@Bindable(deserializable = false)
public class CollectionsMappingTest$6 extends HashSet<String> {
    public CollectionsMappingTest$6(CollectionsMappingTest ignore) {
        add("Test 1");
        add("Test 2");
    }
}
