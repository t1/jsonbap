package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.HashMap;

@Bindable(deserializable = false)
public class CollectionsMappingTest$16 extends HashMap<String, String> {
    public CollectionsMappingTest$16(CollectionsMappingTest ignore) {
        put("string1", "Test 1");
        put("string2", "Test 2");
    }
}
