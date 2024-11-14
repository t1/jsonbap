package ee.jakarta.tck.json.bind.customizedmapping.propertyorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertyorder.model.RenamedPropertiesContainer;

@Bindable(deserializable = false)
public class PropertyOrderCustomizationTest$8 extends RenamedPropertiesContainer {
    public PropertyOrderCustomizationTest$8(PropertyOrderCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
        setLongInstance(1);
    }
}
