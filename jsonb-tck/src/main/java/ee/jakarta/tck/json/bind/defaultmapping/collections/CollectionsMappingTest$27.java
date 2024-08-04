package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class CollectionsMappingTest$27 extends ListContainer {
    {
        List<String> instance = new ArrayList<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
