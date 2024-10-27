package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.jsonbap.impl.Property.JsonbAnnotations.AnnotationWithSource;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.serializer.SerializationContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
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

    protected final JsonbapConfig jsonbapConfig;
    protected final TypeConfig typeConfig;
    protected final T elemental;
    private JsonbAnnotations annotations;

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    @Override public String toString() {return propertyType() + " " + rawName();}

    protected abstract String propertyType();

    public @NonNull T elemental() {return elemental;}

    boolean isPublic() {return elemental().isPublic();}

    boolean isJsonbTransient() {return annotations().jsonbTransient.isPresent();}

    Optional<JsonbProperty> jsonbProperty() {return annotations().jsonbProperty();}

    Optional<AnnotationWithSource<JsonbNumberFormat>> jsonbNumberFormat() {return annotations().jsonbNumberFormat();}

    private JsonbAnnotations annotations() {
        if (annotations == null) annotations = new JsonbAnnotations(elemental);
        return annotations;
    }

    public String name() {
        return annotatedName()
                .or(this::derivedName)
                .orElseGet(this::rawName);
    }

    private Optional<String> annotatedName() {
        return jsonbProperty()
                .map(JsonbProperty::value)
                .flatMap(name -> name.isEmpty() ? Optional.empty() : Optional.of(name));
    }

    private Optional<String> derivedName() {
        var raw = rawName();
        return Optional.ofNullable(switch (typeConfig.propertyNamingStrategy()) {
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

    /// The algorithm is described [here](https://jakarta.ee/specifications/jsonb/3.0/jakarta-jsonb-spec-3.0#scope-and-field-access-strategy).
    ///
    /// For a serialization operation, if a matching public getter method exists,
    /// the method is called to obtain the value of the property.
    /// If a matching getter method with private, protected, or defaulted to package-only access exists,
    /// then this field is ignored.
    /// If no matching getter method exists and the field is public,
    /// then the value is obtained directly from the field.
    public Property<?> merge(Property<?> that) {
        elemental.note("merge " + this + " and " + that);
        var optionalBase = this.or(that);
        if (optionalBase.isOr()) return null;
        var base = optionalBase.get();
        var other = (base == this) ? that : this;
        base.annotations = base.annotations.merge(other.annotations);
        return base;
    }

    protected abstract <V extends Property<?>> Either<V, String> or(V that);


    final void write(TypeGenerator typeGenerator, StringBuilder out) {
        if (isJsonbTransient()) {
            if (jsonbProperty().isPresent()) {
                jsonbException(typeGenerator, out,
                        "don't annotate something as JsonbProperty that you also annotated as JsonbTransient");
            } else {
                writeComment(out, this + " is annotated as JsonbTransient");
            }
        } else if (elemental.isTransient()) {
            writeComment(out, this + " is transient");
        } else if (!elemental.isPublic()) {
            writeComment(out, this + " is not public");
        } else if (PRIMITIVE_TYPES.contains(typeName()) && jsonbNumberFormat().isEmpty()) {
            writeComments(out);
            writeDirect(valueExpression(), out);
        } else {
            writeComments(out);
            writeViaContext(full(valueExpression(), typeGenerator), out);
        }
    }

    private void writeComments(StringBuilder out) {
        if (annotatedName().isPresent())
            writeComment(out, "name from JsonbProperty annotation");
        else if (derivedName().isPresent())
            writeComment(out, "name derived from " + propertyType() + " name with strategy " + typeConfig.propertyNamingStrategy());
        jsonbNumberFormat().ifPresent(jsonbNumberFormat -> writeComment(out, "number format from " + jsonbNumberFormat));
    }

    private String full(String valueExpression, TypeGenerator typeGenerator) {
        return jsonbNumberFormat().map(jsonbNumberFormat -> {
            // TODO as the config is static, it should be possible to do the formatting in far better performing inline code
            var formatter = getNumberFormatterExpression(jsonbNumberFormat.annotation, typeGenerator);
            typeGenerator.addImport(Optional.class.getName());
            return "Optional.ofNullable(" + valueExpression + ")\n" +
                   "            .map(" + formatter + "::format)\n" +
                   frenchNnbspWorkaround() +
                   "            .orElse(null)";
        }).orElse(valueExpression);
    }

    private static String getNumberFormatterExpression(JsonbNumberFormat format, TypeGenerator typeGenerator) {
        if (JsonbNumberFormat.DEFAULT_LOCALE.equals(format.locale())) {
            if (format.value().isEmpty()) { // default
                typeGenerator.addImport(NumberFormat.class.getName());
                return "NumberFormat.getInstance()";
            } else {
                typeGenerator.addImport(DecimalFormat.class.getName());
                return "new DecimalFormat(\"" + format.value() + "\")";
            }
        } else {
            typeGenerator.addImport(Locale.class.getName());
            if (format.value().isEmpty()) {
                typeGenerator.addImport(NumberFormat.class.getName());
                return "NumberFormat.getInstance(Locale.of(\"" + format.locale() + "\"))";
            } else {
                typeGenerator.addImport(DecimalFormat.class.getName());
                typeGenerator.addImport(DecimalFormatSymbols.class.getName());
                return "new DecimalFormat(\"" + format.value() + "\", DecimalFormatSymbols.getInstance(Locale.of(\"" + format.locale() + "\")))";
            }
        }
    }

    private String frenchNnbspWorkaround() {
        return jsonbapConfig.frenchNnbspWorkaround() ? "            .map(f -> f.replace('\\u202f', '\\u00a0'))\n" : "";
    }

    protected void jsonbException(TypeGenerator typeGenerator, StringBuilder out, @SuppressWarnings("SameParameterValue") String message) {
        if (jsonbapConfig.throwJsonbExceptionsAtRuntime()) {
            elemental.warning(message);
            typeGenerator.addImport("jakarta.json.bind.JsonbException");
            // the `if (true)` makes the generated code valid, if more code is following, e.g., the `out.writeEnd()`
            out.append("        if (true) throw new JsonbException(\"").append(message.replace("\"", "\\\"")).append("\");\n");
        } else {
            elemental.error(message);
        }
    }

    protected void writeComment(StringBuilder out, String message) {out.append("        // ").append(message).append("\n");}

    /**
     * Append the code required to serialize a primitive and non-nullable JSON key-value pair
     * directly to the {@link jakarta.json.stream.JsonGenerator generator}
     */
    private void writeDirect(String valueExpression, StringBuilder out) {
        out.append("        out.write(\"");
        writeName(out);
        out.append("\", ").append(valueExpression).append(");\n");
    }

    /**
     * Append the code required to serialize a potentially nullable or complex JSON key-value pair,
     * with the indirection of the {@link SerializationContext context}, which may, or may not,
     * write a <code>null</code> value.
     */
    private void writeViaContext(String valueExpression, StringBuilder out) {
        out.append("        context.serialize(\"");
        writeName(out);
        out.append("\", ").append(valueExpression).append(", out);\n");
    }

    protected abstract String typeName();

    protected abstract String valueExpression();

    protected void writeName(StringBuilder out) {out.append(name());}

    record JsonbAnnotations(
            Optional<JsonbTransient> jsonbTransient,
            Optional<JsonbProperty> jsonbProperty,
            Optional<AnnotationWithSource<JsonbNumberFormat>> jsonbNumberFormat) {

        /// Derive annotations from this elemental or eventually it's containers.
        /// Merging between field and getter happens later.
        JsonbAnnotations(Elemental elemental) {
            this(
                    elemental.annotation(JsonbTransient.class),
                    elemental.annotation(JsonbProperty.class),
                    findAnnotationSource(elemental, JsonbNumberFormat.class));
        }

        private static <A extends Annotation> Optional<AnnotationWithSource<A>> findAnnotationSource(Elemental elemental, @SuppressWarnings("SameParameterValue") Class<A> type) {
            for (var e = elemental; e != null; e = e.enclosingElement().orElse(null)) {
                if (e.isAnnotated(type)) return Optional.of(new AnnotationWithSource<>(e.getAnnotation(type), e));
            }
            return Optional.empty();
        }

        JsonbAnnotations merge(JsonbAnnotations that) {
            return new JsonbAnnotations(
                    this.jsonbTransient.or(that::jsonbTransient),
                    this.jsonbProperty.or(that::jsonbProperty),
                    this.jsonbNumberFormat.or(that::jsonbNumberFormat));
        }

        record AnnotationWithSource<A extends Annotation>(A annotation, Elemental source) {
            @Override public String toString() {return annotation + " on " + source;}
        }
    }
}
