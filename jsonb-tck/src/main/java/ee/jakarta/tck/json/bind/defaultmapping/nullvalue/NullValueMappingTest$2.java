package ee.jakarta.tck.json.bind.defaultmapping.nullvalue;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.nullvalue.model.NullArrayContainer;

@Bindable
public class NullValueMappingTest$2 extends NullArrayContainer {
    public NullValueMappingTest$2(NullValueMappingTest ignoreUnused) {
        setInstance(new String[]{"Test 1", null, "Test 2"});
    }
}
