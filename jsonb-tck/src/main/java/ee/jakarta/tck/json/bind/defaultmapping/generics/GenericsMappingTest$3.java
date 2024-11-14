package ee.jakarta.tck.json.bind.defaultmapping.generics;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.generics.model.GenericContainer;

@Bindable(deserializable = false)
public class GenericsMappingTest$3 extends GenericContainer<String> {
    public GenericsMappingTest$3(GenericsMappingTest ignoreUnused) {
        setInstance("Test String");
    }
}
