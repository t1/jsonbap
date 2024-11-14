package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;

import java.util.LinkedHashMap;

@Bindable(deserializable = false)
public class CollectionsMappingTest$24 extends LinkedHashMap<String, String> {
    public CollectionsMappingTest$24(CollectionsMappingTest ignore) {
        put("string1", "Test 1");
        put("string2", "Test 2");
    }
}
