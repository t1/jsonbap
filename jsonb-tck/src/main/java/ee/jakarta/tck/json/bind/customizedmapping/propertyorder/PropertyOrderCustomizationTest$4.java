package ee.jakarta.tck.json.bind.customizedmapping.propertyorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertyorder.model.CustomOrderContainer;

@Bindable(deserializable = false)
public class PropertyOrderCustomizationTest$4 extends CustomOrderContainer {
    public PropertyOrderCustomizationTest$4(PropertyOrderCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
