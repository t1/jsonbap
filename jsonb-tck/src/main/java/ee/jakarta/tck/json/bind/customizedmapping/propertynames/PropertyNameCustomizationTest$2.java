package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientAnnotatedPropertyContainer;

@Bindable(deserializable = false)
public class PropertyNameCustomizationTest$2 extends TransientAnnotatedPropertyContainer {
    public PropertyNameCustomizationTest$2(PropertyNameCustomizationTest ignoreUnused) {
        setInstance("String Value");
    }
}
