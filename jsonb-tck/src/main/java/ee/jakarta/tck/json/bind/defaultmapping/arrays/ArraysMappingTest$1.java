package ee.jakarta.tck.json.bind.defaultmapping.arrays;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.arrays.model.PrimitiveArrayContainer;

@Bindable
public class ArraysMappingTest$1 extends PrimitiveArrayContainer {
    public ArraysMappingTest$1(ArraysMappingTest ignoreUnused, int[] instance) {
        setInstance(instance);
    }
}
