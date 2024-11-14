package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeExpressionGenerator;
import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Type;
import com.github.t1.exap.reflection.ReflectionProcessingEnvironment;
import com.github.t1.jsonbap.api.Bindable;
import com.github.t1.jsonbap.runtime.FluentParser;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;
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
class JsonbDeserializerGenerator {
    private static Type type(Class<?> klass) {
        return ReflectionProcessingEnvironment.ENV.type(klass);
    }

    private final JsonbapConfig jsonbapConfig;
    private final Type type;
    private final TypeConfig typeConfig;

    public JsonbDeserializerGenerator(JsonbapConfig jsonbapConfig, Type type) {
        this(jsonbapConfig, type, new TypeConfig(type.annotation(Bindable.class)));
    }

    public String className() {
        return type.getRelativeName().replace('.', '$') + "$$JsonbDeserializer";
    }

    public void generate(TypeGenerator typeGenerator) {
        typeGenerator.annotation(type(Generated.class))
                .set("value", JsonbAnnotationProcessor.class.getName());
        typeGenerator.addImplements(new TypeExpressionGenerator(typeGenerator, type(JsonbDeserializer.class))
                .withTypeArg(type));

        typeGenerator.addImport(type(FluentParser.class));
        typeGenerator.addImport(type(JsonParser.Event.class));

        var toJson = typeGenerator.addMethod(PUBLIC, "deserialize");
        toJson.returnType(type);
        toJson.annotation(type(Override.class));
        toJson.addParameter("jsonParser").type(new TypeExpressionGenerator(typeGenerator, type(JsonParser.class)));
        toJson.addParameter("ctx").type(new TypeExpressionGenerator(typeGenerator, type(DeserializationContext.class)));
        toJson.addParameter("rtType").type(new TypeExpressionGenerator(typeGenerator, type(java.lang.reflect.Type.class)));
        toJson.body(body(typeGenerator));
    }

    private String body(TypeGenerator typeGenerator) {
        var body = new StringBuilder();
        body.append("var parser = new FluentParser(jsonParser);\n");
        body.append("        if (parser.is(Event.VALUE_NULL)) return null;\n");
        body.append("        var object = ").append(initObjectExpression());
        body.append("        parser.assume(Event.START_OBJECT);\n");
        body.append("        while (parser.next().is(Event.KEY_NAME)) {\n");
        body.append("            switch (parser.StringAndNext()) {\n");
        properties().forEach(property -> property.writeDeserializer(typeGenerator, body, useBuilder()));
        body.append("            }\n");
        body.append("        }\n");
        body.append("        parser.assume(Event.END_OBJECT);\n");
        body.append("        return object").append(useBuilder() ? ".build()" : "").append(";");
        return body.toString();
    }

    private String initObjectExpression() {
        return useBuilder()
                ? type.getRelativeName() + ".builder();\n"
                : "new " + type.getRelativeName() + "();\n";
    }

    private boolean useBuilder() {return type.annotations().get("lombok.Builder").isPresent();}

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
