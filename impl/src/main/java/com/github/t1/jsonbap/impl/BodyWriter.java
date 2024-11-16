package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Elemental;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BodyWriter {
    protected final JsonbapConfig jsonbapConfig;
    protected final TypeGenerator typeGenerator;
    protected final StringBuilder out;

    public BodyWriter(BodyWriter from) {this(from.jsonbapConfig, from.typeGenerator, from.out);}

    @Override public String toString() {return out.toString();}

    public BodyWriter append(String message) {
        out.append(message);
        return this;
    }

    /// Even if we don't fail the build at compile time (due to the `throwJsonbExceptionsAtRuntime` config option),
    /// we still create code that throws a `JsonbException` at runtime... as required by the spec.
    protected void writeError(String message) {
        typeGenerator.addImport("jakarta.json.bind.JsonbException");
        // the `if (true)` makes the generated code valid, if more code is following, e.g., the `out.writeEnd()`
        out.append("        if (true) throw new JsonbException(\"")
                .append(message.replace("\"", "\\\""))
                .append("\");\n");
    }

    public void error(Elemental elemental, String message) {
        if (jsonbapConfig.throwJsonbExceptionsAtRuntime()) {
            elemental.warning("THIS IS NORMALLY A COMPILE ERROR: " + message);
        } else {
            elemental.error(message);
        }
        writeError(message);
    }
}
