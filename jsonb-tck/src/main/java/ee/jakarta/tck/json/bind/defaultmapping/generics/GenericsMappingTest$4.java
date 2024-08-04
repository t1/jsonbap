package ee.jakarta.tck.json.bind.defaultmapping.generics;

import ee.jakarta.tck.json.bind.defaultmapping.generics.model.CollectionContainer;

import java.util.Arrays;
import java.util.List;

public class GenericsMappingTest$4 extends CollectionContainer {
    {
        final List<String> list = Arrays.asList("Test 1", "Test 2");
        setInstance(list);
    }
}
