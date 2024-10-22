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

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
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

    protected final @NonNull TypeConfig config;
    protected final @NonNull T elemental;
    private final @NonNull ElementalAnnotations annotations;

    @Override public abstract String toString(); // subclasses MUST implement this

    public String name() {
        return annotatedName()
                .or(this::derivedName)
                .orElseGet(this::rawName);
    }

    private Optional<String> annotatedName() {
        return annotations.get(JsonbProperty.class)
                .map(an -> an.getStringProperty("value"))
                .flatMap(name -> name.isEmpty() ? Optional.empty() : Optional.of(name));
    }

    private Optional<String> derivedName() {
        var raw = rawName();
        return Optional.ofNullable(switch (config.propertyNamingStrategy()) {
            case IDENTITY, CASE_INSENSITIVE -> null;
            case LOWER_CASE_WITH_DASHES -> raw.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
            case LOWER_CASE_WITH_UNDERSCORES -> raw.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
            case UPPER_CASE_WITH_UNDERSCORES -> raw.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            case UPPER_CAMEL_CASE -> raw.substring(0, 1).toUpperCase() + raw.substring(1);
            case UPPER_CAMEL_CASE_WITH_SPACES -> {
                var name = raw.replaceAll("([a-z])([A-Z])", "$1 $2");
                yield name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        });
    }

    protected String rawName() {return elemental().name();}

    public @NonNull T elemental() {return elemental;}

    boolean isPublic() {return elemental().isPublic();}

    boolean isJsonbTransient() {return annotations.contains(JsonbTransient.class);}

    boolean isJsonbProperty() {return annotations.contains(JsonbProperty.class);}

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    public Property<?> merge(Property<?> that) {
        elemental.note("merge " + this + " and " + that);
        var optionalBase = this.or(that);
        if (optionalBase.isOr()) return null;
        var base = optionalBase.get();
        var other = (base == this) ? that : this;
        if (!base.isJsonbProperty() && other.isJsonbProperty()) {
            base = base.withAnnotations(other.annotations);
        }
        return base;
    }

    protected abstract Property<?> withAnnotations(@NonNull ElementalAnnotations annotations);

    /// The algorithm is described [here](https://jakarta.ee/specifications/jsonb/3.0/jakarta-jsonb-spec-3.0#scope-and-field-access-strategy).
    ///
    /// For a serialization operation, if a matching public getter method exists,
    /// the method is called to obtain the value of the property.
    /// If a matching getter method with private, protected, or defaulted to package-only access exists,
    /// then this field is ignored.
    /// If no matching getter method exists and the field is public,
    /// then the value is obtained directly from the field.
    protected abstract <V extends Property<?>> Either<V, String> or(V that);


    final void write(TypeGenerator typeGenerator, StringBuilder out) {
        if (isJsonbTransient()) {
            if (isJsonbProperty()) {
                writeJsonbException(typeGenerator, out,
                        "don't annotate something as JsonbProperty that you also annotated as JsonbTransient");
            } else {
                writeComment(out, this + " is annotated as JsonbTransient");
            }
        } else if (!elemental.isPublic()) {
            writeComment(out, this + " is not public");
        } else {
            writeTo(typeGenerator, out);
        }
    }

    protected void writeJsonbException(TypeGenerator typeGenerator, StringBuilder out, String message) {
        elemental.warning(message); // TODO this should normally be an error, but that would break the TCK build. Make it configurable?
        typeGenerator.addImport("jakarta.json.bind.JsonbException");
        // the `if (true)` makes the generated code valid, if more code is following, e.g., the `out.writeEnd()`
        out.append("        if (true) throw new JsonbException(\"").append(message.replace("\"", "\\\"")).append("\");\n");
    }

    protected void writeComment(StringBuilder out, String message) {out.append("        // ").append(message).append("\n");}

    protected abstract void writeTo(TypeGenerator typeGenerator, StringBuilder out);

    /**
     * Append the code required to serialize a JSON key-value pair, either a primitive and non-nullable type directly
     * to the {@link jakarta.json.stream.JsonGenerator generator}, or a more complex type with the indirection
     * of the {@link SerializationContext context}, which may, or may not, write a <code>null</code> value.
     */
    protected void write(String typeName, String valueExpression, StringBuilder out) {
        String name;
        if (annotatedName().isPresent()) {
            writeComment(out, "name from JsonbProperty annotation");
            name = annotatedName().get();
        } else if (derivedName().isPresent()) {
            writeComment(out, "name derived from \"" + rawName() + "\" with strategy " + config.propertyNamingStrategy());
            name = derivedName().get();
        } else {
            name = rawName();
        }
        if (PRIMITIVE_TYPES.contains(typeName)) {
            out.append("        out.write(\"").append(name).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }
}
