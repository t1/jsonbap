package ee.jakarta.tck.json.bind.defaultmapping.generics;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.generics.model.CollectionContainer;

import java.util.List;

@Bindable(deserializable = false)
public class GenericsMappingTest$4 extends CollectionContainer {
    public GenericsMappingTest$4(GenericsMappingTest ignoreUnused, List<String> list) {
        setInstance(list);
    }
}
