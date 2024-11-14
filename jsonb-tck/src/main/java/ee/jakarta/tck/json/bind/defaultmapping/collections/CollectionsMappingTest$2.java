package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.HashMap;

@Bindable(deserializable = false)
public class CollectionsMappingTest$2 extends HashMap<String, String> {
    public CollectionsMappingTest$2(CollectionsMappingTest ignoreUnused) {
        put("string1", "Test 1");
        put("string2", "Test 2");
    }
}
