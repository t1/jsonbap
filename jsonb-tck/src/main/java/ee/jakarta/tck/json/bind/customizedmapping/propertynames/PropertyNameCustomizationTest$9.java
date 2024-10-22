package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.UPPER_CAMEL_CASE;

@Bindable(propertyNamingStrategy = UPPER_CAMEL_CASE)
public class PropertyNameCustomizationTest$9 extends StringContainer {
    public PropertyNameCustomizationTest$9(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
