package ee.jakarta.tck.json.bind.defaultmapping.attributeorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.attributeorder.model.ExtendedContainer;

@Bindable(deserializable = false)
public class AttributeOrderMappingTest$2 extends ExtendedContainer {
    public AttributeOrderMappingTest$2(AttributeOrderMappingTest ignoreUnused) {
        setIntInstance(0);
        setStringInstance("Test String");
        setLongInstance(0L);
        setFloatInstance(0f);
        setShortInstance((short) 0);
    }
}
