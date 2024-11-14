package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientGetterAnnotatedPropertyContainer;

@Bindable(deserializable = false)
public class PropertyNameCustomizationTest$3 extends TransientGetterAnnotatedPropertyContainer {
    public PropertyNameCustomizationTest$3(PropertyNameCustomizationTest ignoreUnused) {
        setInstance("String Value");
    }
}
