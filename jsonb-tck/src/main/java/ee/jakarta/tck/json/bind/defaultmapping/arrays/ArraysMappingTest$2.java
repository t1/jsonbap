package ee.jakarta.tck.json.bind.defaultmapping.arrays;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.arrays.model.MultiDimensionalArrayContainer;

@Bindable(deserializable = false)
public class ArraysMappingTest$2 extends MultiDimensionalArrayContainer {
    public ArraysMappingTest$2(ArraysMappingTest ignoreUnused, Integer[][] instance) {
        setInstance(instance);
    }
}
