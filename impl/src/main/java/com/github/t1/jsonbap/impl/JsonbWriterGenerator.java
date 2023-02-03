package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeExpressionGenerator;
import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.reflection.ReflectionProcessingEnvironment;
import com.github.t1.exap.reflection.Type;
import com.github.t1.jsonbap.api.JsonbWriter;

import javax.annotation.processing.Generated;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.util.stream.Collectors.toList;

class JsonbWriterGenerator {
    private static Type type(Class<?> klass) {
        return ReflectionProcessingEnvironment.ENV.type(klass);
    }

    private final Type type;

    public JsonbWriterGenerator(Type type) {
        this.type = type;
    }

    public String className() {
        return type.getSimpleName() + "$$JsonbWriter";
    }

    public void generate(TypeGenerator typeGenerator) {
        typeGenerator.addImport(type(ApJsonbProvider.class));
        typeGenerator.annotation(type(Generated.class))
            .set("value", JsonbAnnotationProcessor.class.getName());
        typeGenerator.addImplements(new TypeExpressionGenerator(typeGenerator, type(JsonbWriter.class)).withTypeArg(type));

        var toJson = typeGenerator.addMethod("toJson");
        toJson.annotation(type(Override.class));
        toJson.addParameter("object").type(new TypeExpressionGenerator(typeGenerator, type));
        toJson.addParameter("out").type(new TypeExpressionGenerator(typeGenerator, type(Writer.class)));
        toJson.addThrows(type(IOException.class));
        toJson.body(body());
    }

    private String body() {
        var body = new StringBuilder();
        body.append("char delim = '{';\n");
        for (Property property : properties()) {
            property.write(body);
        }
        body.append("        out.append(\"}\");");
        return body.toString();
    }

    private List<Property> properties() {
        return type.getAllMethods().stream()
            .filter(GetterProperty::isGetter)
            .map(GetterProperty::of)
            .sorted()
            .collect(toList());
    }
}
