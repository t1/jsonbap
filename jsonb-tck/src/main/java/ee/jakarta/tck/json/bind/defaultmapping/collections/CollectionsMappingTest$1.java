package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.CollectionContainer;

import java.util.Arrays;

@Bindable
public class CollectionsMappingTest$1 extends CollectionContainer {
    public CollectionsMappingTest$1(CollectionsMappingTest ignoreUnused) {
        setInstance(Arrays.asList("Test 1", "Test 2"));
    }
}
