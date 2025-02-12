package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.ElementalKind;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.impl.Property.JsonbAnnotations.AnnotationWithSource;
import com.github.t1.jsonbap.runtime.DateTimeWriter;
import com.github.t1.jsonbap.runtime.FluentParser;
import com.github.t1.jsonbap.runtime.NullWriter;
import com.github.t1.jsonbap.runtime.TypeLiteral;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbNillable;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.json.bind.serializer.SerializationContext;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.github.t1.jsonbap.runtime.FluentParser.titleCase;
import static java.util.stream.Collectors.joining;
import static javax.lang.model.type.TypeKind.DECLARED;

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

    private final JsonbapConfig jsonbapConfig;
    private final TypeConfig typeConfig;
    private final T elemental;
    private final List<String> elementalErrors = new ArrayList<>();
    private JsonbAnnotations annotations;
    private boolean isTransient;

    protected Property(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, T elemental) {
        this.jsonbapConfig = jsonbapConfig;
        this.typeConfig = typeConfig;
        this.elemental = elemental;
        this.isTransient = elemental.isTransient();
    }

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    @Override public String toString() {return propertyType() + " \"" + rawName() + '"';}

    protected abstract String propertyType();

    public @NonNull T elemental() {return elemental;}

    boolean isPublic() {return elemental().isPublic();}

    abstract boolean isSettable();

    boolean isTransient() {return isTransient || annotations().jsonbTransient.isPresent();}

    /// annotated as `@JsonbNillable(false)`, i.e. ignore the `nillable` property of the `JsonbProperty` annotation,
    /// as well as the configuration of the context
    Boolean isNillableFalse() {
        return annotations().jsonbNillable.map(nillable -> !nillable.annotation.value()).orElse(false);
    }

    /// annotated as `@JsonbNillable(true)` or with the deprecated `nillable` property of the `JsonbProperty` annotation.
    /// this ignores the configuration of the context.
    @SuppressWarnings("deprecation")
    boolean isNillable() {
        return annotations().jsonbNillable.map(nillable -> nillable.annotation.value())
                .or(() -> annotations().jsonbProperty.map(property -> property.annotation.nillable()))
                .orElse(false);
    }

    Optional<AnnotationWithSource<JsonbNumberFormat>> jsonbNumberFormat() {return annotations().jsonbNumberFormat();}

    Optional<AnnotationWithSource<JsonbDateFormat>> jsonbDateFormat() {return annotations().jsonbDateFormat();}

    Optional<AnnotationWithSource<JsonbVisibility>> jsonbVisibility() {return annotations().jsonbVisibility();}

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
        return annotations().jsonbProperty()
                .map(AnnotationWithSource::annotation)
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
            case UPPER_CAMEL_CASE -> titleCase(raw);
            case UPPER_CAMEL_CASE_WITH_SPACES -> titleCase(raw.replaceAll("([a-z])([A-Z])", "$1 $2"));
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
        // It's not a property duplications, if it's a field and a getter with the same name,
        // or a corresponding getter from a super class,
        // but it _is_ a duplication, if it's two fields or two getters with the same name (after renaming).
        if (this.getClass() == that.getClass() && !inheritFromOrTo(that))
            this.elementalError(duplicatePropertyMessage(that));
        var eitherBase = this.or(that);
        if (eitherBase.isOr()) return null;
        var base = eitherBase.get();
        var other = (base == this) ? that : this;
        base.annotations = base.annotations.merge(other.annotations);
        base.isTransient = base.isTransient || other.isTransient;
        base.elementalErrors.addAll(other.elementalErrors);
        return base;
    }

    private boolean inheritFromOrTo(Property<?> that) {
        var thisType = enclosingType(this.elemental);
        var thatType = enclosingType(that.elemental);
        return thisType != thatType && (thisType.isA(thatType) || thatType.isA(thisType));
    }

    private String duplicatePropertyMessage(Property<?> that) {
        var thisType = enclosingType(this.elemental);
        var thatType = enclosingType(that.elemental);
        return "Duplicate property name: " + ((thisType == thatType)
                ? this + " and " + that + " in class " + thisType
                : this + " in class " + thisType + " and " + that + " in class " + thatType);
    }

    private static Type enclosingType(Elemental elemental) {
        var enclosing = elemental.enclosingElement().orElseThrow();
        try {
            return (Type) enclosing;
        } catch (ClassCastException e) {
            throw new RuntimeException("expected " + elemental + " to be contained in a class, but it's the " +
                                       enclosing.kind().toString().toLowerCase() + " " + enclosing, e);
        }
    }

    private void elementalError(String message) {
        elementalErrors.add(message);
        if (jsonbapConfig.throwJsonbExceptionsAtRuntime()) {
            elemental.warning("THIS IS NORMALLY A COMPILE ERROR: " + message);
        } else {
            elemental.error(message);
        }
    }

    protected abstract <V extends Property<?>> Either<V, String> or(V that);

    final void writeSerializer(BodyWriter body) {new PropertySerializerWriter(body).write();}

    final void writeDeserializer(BodyWriter body, boolean useBuilder) {new PropertyDeserializerWriter(body, useBuilder).write();}


    protected abstract Type type();

    protected abstract String readExpression();

    protected abstract String writeExpression(String value);

    record JsonbAnnotations(
            Optional<AnnotationWithSource<JsonbTransient>> jsonbTransient,
            Optional<AnnotationWithSource<JsonbVisibility>> jsonbVisibility,
            Optional<AnnotationWithSource<JsonbProperty>> jsonbProperty,
            Optional<AnnotationWithSource<JsonbNumberFormat>> jsonbNumberFormat,
            Optional<AnnotationWithSource<JsonbDateFormat>> jsonbDateFormat,
            Optional<AnnotationWithSource<JsonbNillable>> jsonbNillable) {

        /// Derive annotations from this elemental or eventually it's containers.
        /// Merging between field and getter happens later.
        JsonbAnnotations(Elemental elemental) {
            this(
                    get(JsonbTransient.class, elemental),
                    find(JsonbVisibility.class, elemental),
                    get(JsonbProperty.class, elemental),
                    find(JsonbNumberFormat.class, elemental),
                    find(JsonbDateFormat.class, elemental),
                    find(JsonbNillable.class, elemental));
        }

        @Override public String toString() {
            return all().map(AnnotationWithSource::toString).collect(joining(", ", "[", "]"));
        }

        /// annotation directly on the field or getter
        private static <A extends Annotation> Optional<AnnotationWithSource<A>> get(Class<A> type, Elemental elemental) {
            return elemental.annotation(type).map(a -> new AnnotationWithSource<>(a, elemental));
        }

        /// annotation on the field or getter or any of its containers
        private static <A extends Annotation> Optional<AnnotationWithSource<A>> find(Class<A> type, Elemental elemental) {
            for (var e = elemental; e != null; e = e.enclosingElement().orElse(null)) {
                if (e.isAnnotated(type)) return Optional.of(new AnnotationWithSource<>(e.getAnnotation(type), e));
            }
            return Optional.empty();
        }

        Stream<AnnotationWithSource<?>> all() {
            return Stream.of(
                            jsonbTransient,
                            jsonbVisibility,
                            jsonbProperty,
                            jsonbNumberFormat,
                            jsonbDateFormat,
                            jsonbNillable)
                    .flatMap(Optional::stream);
        }

        JsonbAnnotations merge(JsonbAnnotations that) {
            return new JsonbAnnotations(
                    this.jsonbTransient.or(that::jsonbTransient),
                    sorted(this.jsonbVisibility, that.jsonbVisibility),
                    this.jsonbProperty.or(that::jsonbProperty),
                    sorted(this.jsonbNumberFormat, that.jsonbNumberFormat),
                    sorted(this.jsonbDateFormat, that.jsonbDateFormat),
                    sorted(this.jsonbNillable, that.jsonbNillable));
        }

        /// Find the most specific annotations, i.e. the ones closest to the field or getter.
        @SafeVarargs
        private <A extends Annotation> Optional<AnnotationWithSource<A>> sorted(Optional<AnnotationWithSource<A>>... values) {
            return Stream.of(values)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min(Comparator.comparing(AnnotationWithSource::sourceKind));
        }

        record AnnotationWithSource<A extends Annotation>(A annotation, Elemental source) {
            @Override
            public String toString() {return annotation + " on " + sourceKind().toString().toLowerCase() + " " + source;}

            public ElementalKind sourceKind() {return source.kind();}

            @SuppressWarnings("BooleanMethodIsAlwaysInverted")
            public boolean is(Class<?> type) {return type.isInstance(annotation);}
        }
    }

    private class PropertySerializerWriter extends BodyWriter {
        private PropertySerializerWriter(BodyWriter body) {super(body);}

        private void write() {
            if (isTransient()) {
                var transientMessage = Property.this + " is transient" + transientSourceMessage();
                writeComment(transientMessage);
                annotations().all().filter(annotation -> !annotation.is(JsonbTransient.class)).findAny()
                        .ifPresent(a -> elementalError(transientMessage + " but also annotated " + a));
            } else if (!isPublic()) {
                writeComment(Property.this + " is not public");
            } else {
                writeField();
            }
            elementalErrors.forEach(this::writeError);
        }

        private String transientSourceMessage() {
            if (isTransient && Property.this instanceof GetterProperty) return " from underlying field";
            return annotations().jsonbTransient.map(a -> " from " + a).orElse("");
        }

        private void writeField() {
            if (annotatedName().isPresent())
                writeComment("name from JsonbProperty annotation");
            else if (derivedName().isPresent())
                writeComment("name derived from " + propertyType() + " name with strategy " + typeConfig.propertyNamingStrategy());

            jsonbVisibility().ifPresent(jsonbVisibility -> writeComment("visibility from " + jsonbVisibility));
            // TODO call the visibility strategy to find out, if we should write this property at all

            if (PRIMITIVE_TYPES.contains(type().getSimpleName()) && jsonbNumberFormat().isEmpty()) {
                writeDirect(readExpression());
            } else {
                jsonbNumberFormat().ifPresent(jsonbNumberFormat -> writeComment("number format from " + jsonbNumberFormat));
                jsonbDateFormat().ifPresent(jsonbDateFormat -> writeComment("date format from " + jsonbDateFormat));

                var valueExpression = formatted(readExpression());
                if (isNillableFalse()) {
                    writeViaNullWriter(valueExpression, "writeNullable");
                } else if (isNillable()) {
                    writeViaNullWriter(valueExpression, "writeNillable");
                } else {
                    writeViaContext(valueExpression);
                }
            }
        }

        private void writeComment(String message) {append("        // ").append(message).append("\n");}

        /**
         * Append the code required to serialize a primitive and non-nullable JSON key-value pair
         * directly to the {@link jakarta.json.stream.JsonGenerator generator}
         */
        private void writeDirect(String valueExpression) {
            append("        out.write(\"");
            append(name());
            append("\", ").append(valueExpression).append(");\n");
        }

        private void writeViaNullWriter(String valueExpression, String methodName) {
            typeGenerator.addImport(NullWriter.class.getName());
            append("        NullWriter.").append(methodName).append("(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out, context);\n");
        }

        private String formatted(String valueExpression) {
            // TODO as this config is static, it should be possible to do the formatting with far better performing inline code.
            //  But we can't simply create, e.g., a field in the generated code, as it could be shared between multiple calls,
            //  but the formatters aren't thread-safe!
            return jsonbDateFormat().map(jsonbDateFormat -> {
                        var formatter = getDateTimeFormatterExpression(jsonbDateFormat.annotation);
                        typeGenerator.addImport(DateTimeWriter.class.getName());
                        typeGenerator.addImport(Optional.class.getName());
                        return "Optional.ofNullable(" + valueExpression + ")\n" +
                               "            .map(" + formatter + "::format)\n" +
                               "            .orElse(null)";
                    })
                    .or(() -> jsonbNumberFormat().map(jsonbNumberFormat -> {
                        var formatter = getNumberFormatterExpression(jsonbNumberFormat.annotation);
                        typeGenerator.addImport(Optional.class.getName());
                        return "Optional.ofNullable(" + valueExpression + ")\n" +
                               "            .map(" + formatter + "::format)\n" +
                               frenchNnbspWorkaround() +
                               "            .orElse(null)";
                    }))
                    .orElse(valueExpression);
        }

        private String getDateTimeFormatterExpression(JsonbDateFormat annotation) {
            String optionalLocaleExpression;
            if (JsonbDateFormat.DEFAULT_LOCALE.equals(annotation.locale())) {
                optionalLocaleExpression = "Optional.empty()";
            } else {
                typeGenerator.addImport(Locale.class.getName());
                optionalLocaleExpression = "Optional.of(Locale.of(\"" + annotation.locale() + "\"))";
            }
            if (JsonbDateFormat.DEFAULT_FORMAT.equals(annotation.value())) {
                return "DateTimeWriter.dateFormat(" + optionalLocaleExpression + ")";
            } else {
                return "DateTimeWriter.dateFormat(\"" + annotation.value() + "\", " + optionalLocaleExpression + ")";
            }
        }

        private String getNumberFormatterExpression(JsonbNumberFormat format) {
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

        /**
         * Append the code required to serialize a potentially nullable or complex JSON key-value pair,
         * with the indirection of the {@link SerializationContext context}, which may, or may not,
         * write a <code>null</code> value.
         */
        private void writeViaContext(String valueExpression) {
            append("        context.serialize(\"");
            append(name());
            append("\", ").append(valueExpression).append(", out);\n");
        }
    }

    private class PropertyDeserializerWriter extends BodyWriter {
        private final boolean useBuilder;

        private PropertyDeserializerWriter(BodyWriter body, boolean useBuilder) {
            super(body);
            this.useBuilder = useBuilder;
        }

        private void write() {
            try {
                if (!isSettable()) {
                    writeComment(Property.this + " is not settable");
                } else if (isTransient()) {
                    var transientMessage = Property.this + " is transient" + transientSourceMessage();
                    writeComment(transientMessage);
                    annotations().all().filter(annotation -> !annotation.is(JsonbTransient.class)).findAny()
                            .ifPresent(a -> elementalError(transientMessage + " but also annotated " + a));
                } else if (!isPublic()) {
                    writeComment(Property.this + " is not public");
                } else {
                    writeField();
                }
                //elementalErrors.forEach(this::writeError);
            } catch (Exception e) {
                throw new RuntimeException("can't write code to serialize " + type() + " " + Property.this, e);
            }
        }

        private String transientSourceMessage() {
            if (isTransient && Property.this instanceof GetterProperty) return " from underlying field";
            return annotations().jsonbTransient.map(a -> " from " + a).orElse("");
        }

        private void writeField() {
            if (annotatedName().isPresent())
                writeComment("name from JsonbProperty annotation");
            else if (derivedName().isPresent())
                writeComment("name derived from " + propertyType() + " name with strategy " + typeConfig.propertyNamingStrategy());

            jsonbVisibility().ifPresent(jsonbVisibility -> writeComment("visibility from " + jsonbVisibility));
            // TODO call the visibility strategy to find out, if we should read this property at all

            append("                case \"")
                    .append(name())
                    .append("\" -> ");
            var typeToDeserialize = typeToDeserialize();
            typeToImport(typeToDeserialize).ifPresent(typeGenerator::addImport);
            var readMethod = FluentParser.readMethod(typeToDeserialize.getSimpleName());
            if (type().isArray()) {
                var elementType = type().elementType();
                String nestedExpression;
                if (elementType.isParameterized()) {
                    typeGenerator.addImport(elementType);
                    typeGenerator.addImport(TypeLiteral.class.getName());
                    nestedExpression = "TypeLiteral.genericType(new TypeLiteral<" + elementType.getRelativeName()
                                       + elementType.getTypeParameters().stream().map(Type::getRelativeName)
                                               .collect(joining(",", "<", ">")) + "[]>() {})";
                } else {
                    nestedExpression = elementType.getRelativeName() + "[].class";
                }
                assign("parser.deserialize(ctx, " + nestedExpression + ")");
            } else if (readMethod.isEmpty()) {
                String nestedExpression;
                if (type().isA(Optional.class)) {
                    typeGenerator.addImport(Optional.class.getName());
                    nestedExpression = "Optional.ofNullable(" +
                                       "parser.deserialize(ctx, " + type().getTypeParameters().getFirst().getRelativeName() +
                                       ".class))";
                } else if (type().isParameterized()) {
                    typeGenerator.addImport(TypeLiteral.class.getName());
                    nestedExpression = "parser.deserialize(ctx, TypeLiteral.genericType(new TypeLiteral<" + typeExpression() + "[]>() {}))";
                } else {
                    nestedExpression = "parser.deserialize(ctx, " + type().getRelativeName() + ".class" + ")";
                }
                assign(nestedExpression);
            } else {
                if (type().isA(Optional.class)) {
                    assign("parser." + readMethod.get() + "()");
                } else {
                    append("parser.").append(readMethod.get()).append("().ifPresent(");
                    if (useBuilder) {
                        append("object::").append(rawName());
                    } else {
                        append("value -> object.").append(writeExpression("value"));
                    }
                    append(")");
                }
            }
            append(";\n");
        }

        private String typeExpression() {
            var out = new StringBuilder();
            typeGenerator.addImport(type());
            out.append(type().getRelativeName());
            boolean first = true;
            for (var param : type().getTypeParameters()) {
                if (first) {
                    first = false;
                    out.append("<");
                } else {
                    out.append(", ");
                }
                typeGenerator.addImport(param);
                out.append(param.getRelativeName());
            }
            out.append(">");
            return out.toString();
        }

        private void writeComment(String message) {append("        // ").append(message).append("\n");}

        private Type typeToDeserialize() {
            var type = type();
            if (type.isArray()) type = type.elementType();
            try {
                return type.isA(Optional.class) ? type.getTypeParameters().getFirst() : type;
            } catch (Exception e) {
                throw new RuntimeException("can't get type parameter of " + type.typeKind() + " " + type, e);
            }
        }

        private Optional<Type> typeToImport(Type type) {
            if (type.isArray()) type = type.elementType();
            return type.typeKind() == DECLARED ? Optional.of(type) : Optional.empty();
        }

        private void assign(String nestedExpression) {
            if (useBuilder) {
                append("object.").append(rawName()).append("(").append(nestedExpression).append(")");
            } else {
                append("object.").append(writeExpression(nestedExpression));
            }
        }
    }
}
