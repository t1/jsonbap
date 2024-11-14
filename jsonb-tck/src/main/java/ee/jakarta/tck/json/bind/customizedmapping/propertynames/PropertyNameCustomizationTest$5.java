package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.PropertyNameCustomizationAccessorsContainer;

@Bindable(deserializable = false)
public class PropertyNameCustomizationTest$5 extends PropertyNameCustomizationAccessorsContainer {
    public PropertyNameCustomizationTest$5(PropertyNameCustomizationTest ignoreUnused) {
        setInstance("Test String");
    }
}
