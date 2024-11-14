package ee.jakarta.tck.json.bind.customizedmapping.propertyorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertyorder.model.CustomOrderContainer;

@Bindable(deserializable = false)
public class PropertyOrderCustomizationTest$5 extends CustomOrderContainer {
    public PropertyOrderCustomizationTest$5(PropertyOrderCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
