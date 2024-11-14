package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.ArrayList;

@Bindable(deserializable = false)
public class CollectionsMappingTest$28 extends ArrayList<String> {
    public CollectionsMappingTest$28(CollectionsMappingTest ignore) {
        add("Test 1");
        add("Test 2");
    }
}
