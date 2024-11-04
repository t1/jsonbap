package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeExpressionGenerator;
import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Type;
import com.github.t1.exap.reflection.ReflectionProcessingEnvironment;
import com.github.t1.jsonbap.api.Bindable;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import lombok.AllArgsConstructor;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static com.github.t1.exap.generator.Visibility.PUBLIC;
import static com.github.t1.jsonbap.impl.FieldProperty.fieldProperties;
import static com.github.t1.jsonbap.impl.GetterProperty.getterProperties;
import static com.github.t1.jsonbap.impl.TypeProperty.typeProperties;

@AllArgsConstructor
class JsonbSerializerGenerator {
    private static Type type(Class<?> klass) {
        return ReflectionProcessingEnvironment.ENV.type(klass);
    }

    private final JsonbapConfig jsonbapConfig;
    private final Type type;
    private final TypeConfig typeConfig;

    public JsonbSerializerGenerator(JsonbapConfig jsonbapConfig, Type type) {
        this(jsonbapConfig, type, new TypeConfig(type.annotation(Bindable.class)));
    }

    public String className() {
        return type.getRelativeName().replace('.', '$') + "$$JsonbSerializer";
    }

    public void generate(TypeGenerator typeGenerator) {
        typeGenerator.annotation(type(Generated.class))
                .set("value", JsonbAnnotationProcessor.class.getName());
        typeGenerator.addImplements(new TypeExpressionGenerator(typeGenerator, type(JsonbSerializer.class))
                .withTypeArg(type));

        var toJson = typeGenerator.addMethod(PUBLIC, "serialize");
        toJson.annotation(type(Override.class));
        toJson.addParameter("object").type(new TypeExpressionGenerator(typeGenerator, type));
        toJson.addParameter("out").type(new TypeExpressionGenerator(typeGenerator, type(JsonGenerator.class)));
        toJson.addParameter("context").type(new TypeExpressionGenerator(typeGenerator, type(SerializationContext.class)));
        toJson.body(body(typeGenerator));
    }

    private String body(TypeGenerator typeGenerator) {
        var body = new StringBuilder();
        body.append("out.writeStartObject();\n");
        properties().forEach(property -> property.write(typeGenerator, body));
        body.append("        out.writeEnd();");
        return body.toString();
    }

    private List<Property<?>> properties() {
        return Stream.concat(
                        Stream.concat(
                                typeProperties(jsonbapConfig, typeConfig, type),
                                Stream.empty()), // just for symmetry
                        Stream.concat(
                                fieldProperties(jsonbapConfig, typeConfig, type),
                                getterProperties(jsonbapConfig, typeConfig, type)))
                .sorted()
                .collect(propertiesMerger());
    }

    private Collector<Property<?>, Map<String, Property<?>>, List<Property<?>>> propertiesMerger() {
        return Collector.of(
                LinkedHashMap::new,
                (m, p) -> m.merge(p.name(), p, Property::merge),
                (l, r) -> {
                    l.putAll(r);
                    return l;
                },
                m -> m.values().stream().toList()
        );
    }
}
