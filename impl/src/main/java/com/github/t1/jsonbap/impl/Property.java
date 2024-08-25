package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.ElementalAnnotations;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Set;

@RequiredArgsConstructor
abstract class Property implements Comparable<Property> {
    private static final Comparator<Property> COMPARATOR = Comparator.comparing(Property::name);

    /**
     * The primitive types that JsonGenerator supports directly; no null-check required.
     * All other types are serialized via the context, which does the null-check.
     */
    static final Set<String> PRIMITIVE_TYPES = Set.of(
            "int",
            "long",
            "double",
            "boolean");

    private final @NonNull String name;
    private final @NonNull Elemental elemental;
    private final @NonNull ElementalAnnotations annotations;

    public Property(@NonNull Elemental elemental) {this(elemental.name(), elemental, elemental.annotations());}

    @Override public String toString() {return getClass().getSimpleName() + " " + elemental();}

    public @NonNull String name() {return name;}

    public @NonNull Elemental elemental() {return elemental;}

    boolean isJsonbTransient() {return annotations.contains(JsonbTransient.class);}

    boolean isJsonbProperty() {return annotations.contains(JsonbProperty.class);}

    private void writeJsonbException(TypeGenerator typeGenerator, StringBuilder out, String message) {
        elemental.warning(message);
        typeGenerator.addImport("jakarta.json.bind.JsonbException");
        // the `if (true)` makes the generated code valid, if more code is following, e.g., the `out.writeEnd()`
        out.append("        if (true) throw new JsonbException(\"").append(message.replace("\"", "\\\"")).append("\");\n");
    }

    private void writeComment(StringBuilder out, String message) {out.append("        // ").append(message).append("\n");}

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    final void write(TypeGenerator typeGenerator, StringBuilder out) {
        if (isJsonbTransient()) {
            if (isJsonbProperty()) {
                writeJsonbException(typeGenerator, out,
                        "don't annotate something as JsonbProperty that you also annotated as JsonbTransient");
            } else {
                writeComment(out, elemental + " is annotated as JsonbTransient");
            }
        } else {
            writeTo(typeGenerator, out);
        }
    }

    protected abstract void writeTo(TypeGenerator typeGenerator, StringBuilder out);

    public Property merge(Property that) {
        elemental.note("merge " + this + " into " + that);
        return that.withAnnotations(this.annotations);
    }

    private Property withAnnotations(ElementalAnnotations annotations) {
        return new Property(this.name, this.elemental, this.annotations.merge(annotations)) {
            @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
                Property.this.writeTo(typeGenerator, out);
            }
        };
    }
}
