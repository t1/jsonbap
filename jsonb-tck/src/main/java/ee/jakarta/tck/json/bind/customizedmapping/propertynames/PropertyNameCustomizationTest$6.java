package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

@Bindable(deserializable = false)
public class PropertyNameCustomizationTest$6 extends StringContainer {
    public PropertyNameCustomizationTest$6(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
