package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.TreeMap;

@Bindable(deserializable = false)
public class CollectionsMappingTest$22 extends TreeMap<String, String> {
    public CollectionsMappingTest$22(CollectionsMappingTest ignoreUnused) {
        put("string1", "Test 1");
        put("string2", "Test 2");
    }
}
