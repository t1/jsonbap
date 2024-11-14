package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.TreeSet;

@Bindable(deserializable = false)
public class CollectionsMappingTest$8 extends TreeSet<String> {
    public CollectionsMappingTest$8(CollectionsMappingTest ignore) {
        add("Test 1");
        add("Test 2");
    }
}
