package ee.jakarta.tck.json.bind.defaultmapping.generics;

import ee.jakarta.tck.json.bind.defaultmapping.generics.model.WildcardContainer;

import java.util.Arrays;
import java.util.List;

public class GenericsMappingTest$9 extends WildcardContainer {
    {
        final List<String> list = Arrays.asList("Test 1", "Test 2");
        setInstance(list);
    }
}
