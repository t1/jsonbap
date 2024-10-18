package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.ElementalAnnotations;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.serializer.SerializationContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
abstract class Property<T extends Elemental> implements Comparable<Property<?>> {
    private static final Comparator<Property<?>> COMPARATOR = Comparator.comparing(Property::name);

    /**
     * The primitive types that {@link jakarta.json.stream.JsonGenerator} supports directly; no null-check required.
     * All other types are serialized via the {@link JsonGeneratorContext}, which does the null-check.
     */
    static final Set<String> PRIMITIVE_TYPES = Set.of(
            "int",
            "long",
            "double",
            "boolean");

    private final @NonNull PropertyConfig config;
    protected final @NonNull T elemental;
    private final @NonNull ElementalAnnotations annotations;

    public Property(@NonNull T elemental) {this(new PropertyConfig(elemental), elemental, elemental.annotations());}

    @Override public String toString() {return getClass().getSimpleName() + " " + elemental();}

    public String name() {
        return annotations.get(JsonbProperty.class)
                .map(an -> an.getStringProperty("value"))
                .flatMap(name -> name.isEmpty() ? Optional.empty() : Optional.of(name))
                .orElseGet(this::derivedName);
    }

    private String derivedName() {
        return switch (config.propertyNamingStrategy()) {
            case IDENTITY, CASE_INSENSITIVE -> rawName();
            case LOWER_CASE_WITH_DASHES -> rawName().replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
            case LOWER_CASE_WITH_UNDERSCORES -> rawName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
            case UPPER_CASE_WITH_UNDERSCORES -> rawName().replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            case UPPER_CAMEL_CASE -> rawName().substring(0, 1).toUpperCase() + rawName().substring(1);
            case UPPER_CAMEL_CASE_WITH_SPACES -> {
                var name = rawName().replaceAll("([a-z])([A-Z])", "$1 $2");
                yield name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        };
    }

    protected String rawName() {return elemental().name();}

    public @NonNull T elemental() {return elemental;}

    boolean isJsonbTransient() {return annotations.contains(JsonbTransient.class);}

    boolean isJsonbProperty() {return annotations.contains(JsonbProperty.class);}

    protected void writeJsonbException(TypeGenerator typeGenerator, StringBuilder out, String message) {
        elemental.warning(message);
        typeGenerator.addImport("jakarta.json.bind.JsonbException");
        // the `if (true)` makes the generated code valid, if more code is following, e.g., the `out.writeEnd()`
        out.append("        if (true) throw new JsonbException(\"").append(message.replace("\"", "\\\"")).append("\");\n");
    }

    protected void writeComment(StringBuilder out, String message) {out.append("        // ").append(message).append("\n");}

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    final void write(TypeGenerator typeGenerator, StringBuilder out) {
        if (isJsonbTransient()) {
            if (isJsonbProperty()) {
                writeJsonbException(typeGenerator, out,
                        "don't annotate something as JsonbProperty that you also annotated as JsonbTransient");
            } else {
                writeComment(out, elemental + " is annotated as JsonbTransient");
            }
        } else if (!elemental.isPublic()) {
            writeComment(out, elemental + " is not public");
        } else {
            writeTo(typeGenerator, out);
        }
    }

    protected abstract void writeTo(TypeGenerator typeGenerator, StringBuilder out);

    public Property<T> merge(Property<T> that) {
        elemental.note("merge " + this + " into " + that);
        return that.withAnnotations(this.annotations);
    }

    private Property<T> withAnnotations(ElementalAnnotations annotations) {
        return new Property<>(this.config, this.elemental, this.annotations.merge(annotations)) {
            @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
                Property.this.writeTo(typeGenerator, out);
            }
        };
    }

    /**
     * Append the code required to serialize a JSON key-value pair, either a primitive and non-nullable type directly
     * to the {@link jakarta.json.stream.JsonGenerator generator}, or a more complex type with the indirection
     * of the {@link SerializationContext context}, which may, or may not, write a <code>null</code> value.
     */
    protected void write(String typeName, String valueExpression, StringBuilder out) {
        if (PRIMITIVE_TYPES.contains(typeName)) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }
}
