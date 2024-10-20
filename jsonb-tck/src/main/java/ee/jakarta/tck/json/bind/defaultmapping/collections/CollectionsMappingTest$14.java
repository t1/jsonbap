package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.LinkedHashSet;

@Bindable
public class CollectionsMappingTest$14 extends LinkedHashSet<String> {
    public CollectionsMappingTest$14(CollectionsMappingTest ignore) {
        add("Test 1");
        add("Test 2");
    }
}
