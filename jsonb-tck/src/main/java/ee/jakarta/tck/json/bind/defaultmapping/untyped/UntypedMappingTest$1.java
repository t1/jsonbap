package ee.jakarta.tck.json.bind.defaultmapping.untyped;

import com.github.t1.jsonbap.api.Bindable;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
@Bindable
public class UntypedMappingTest$1 {
    public UntypedMappingTest$1(UntypedMappingTest ignoreUnused) {
    }

    private String stringProperty = "Test String";

    public String getStringProperty() {
        return stringProperty;
    }

    private Number numericProperty = 0;

    public Number getNumericProperty() {
        return numericProperty;
    }

    private boolean booleanProperty = false;

    public boolean getBooleanProperty() {
        return booleanProperty;
    }

    private Object nullProperty = null;

    public Object getNullProperty() {
        return nullProperty;
    }
}
