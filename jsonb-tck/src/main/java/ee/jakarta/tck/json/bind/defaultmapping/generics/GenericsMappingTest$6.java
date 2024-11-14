package ee.jakarta.tck.json.bind.defaultmapping.generics;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.generics.model.NumberContainer;

@Bindable(deserializable = false)
public class GenericsMappingTest$6 extends NumberContainer<Integer> {
    public GenericsMappingTest$6(GenericsMappingTest ignoreUnused) {
        setInstance(Integer.MAX_VALUE);
    }
}
