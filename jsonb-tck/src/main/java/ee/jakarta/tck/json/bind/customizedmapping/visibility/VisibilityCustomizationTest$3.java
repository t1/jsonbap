package ee.jakarta.tck.json.bind.customizedmapping.visibility;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.visibility.model.customized.PackageCustomizedContainer;

@Bindable(deserializable = false)
public class VisibilityCustomizationTest$3 extends PackageCustomizedContainer {
    public VisibilityCustomizationTest$3(VisibilityCustomizationTest ignoredUnused) {
        setStringInstance("Test String");
    }
}
