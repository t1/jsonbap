package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.PropertyNameCustomizationContainer;

@Bindable(deserializable = false)
public class PropertyNameCustomizationTest$4 extends PropertyNameCustomizationContainer {
    public PropertyNameCustomizationTest$4(PropertyNameCustomizationTest ignoreUnused) {
        setInstance("Test String");
    }
}
