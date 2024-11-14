package ee.jakarta.tck.json.bind.defaultmapping.generics;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.generics.model.WildcardContainer;

import java.util.List;

@Bindable(deserializable = false)
public class GenericsMappingTest$9 extends WildcardContainer {
    public GenericsMappingTest$9(GenericsMappingTest ignoreUnused, List<String> list) {
        setInstance(list);
    }
}
