package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_UNDERSCORES;

@Bindable(propertyNamingStrategy = LOWER_CASE_WITH_UNDERSCORES, deserializable = false)
public class PropertyNameCustomizationTest$8 extends StringContainer {
    public PropertyNameCustomizationTest$8(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
