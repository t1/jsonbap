package com.github.t1.jsonbap.impl;

import com.github.t1.exap.reflection.Method;
import jakarta.json.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

@RequiredArgsConstructor
abstract class GetterProperty implements Property {
    static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getName().startsWith("get") // TODO booleans may start with `is`
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName());
    }

    static GetterProperty of(Method method) {
        return of(method.getReturnType().getFullName(), nameFrom(method.getName()), "object." + method.getName() + "()");
    }

    static GetterProperty of(String typeName, String name, String valueExpression) {
        if (ScalarGetterProperty.SUPPORTED.contains(typeName))
            return new ScalarGetterProperty(typeName, name, valueExpression);
        if (typeName.startsWith("java.util.List<") && typeName.endsWith(">"))
            return new ArrayGetterProperty(name, valueExpression);
        return new ObjectGetterProperty(name, valueExpression);
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Getter @Accessors(fluent = true)
    private final String name;
    protected final String valueExpression;

    /**
     * Write values that a {@link jakarta.json.stream.JsonGenerator} can handle directly.
     */
    private static class ScalarGetterProperty extends GetterProperty {
        public static final Set<String> SUPPORTED = Set.of(
                JsonValue.class.getName(),
                String.class.getName(),
                BigInteger.class.getName(),
                BigDecimal.class.getName(),
                int.class.getName(), Integer.class.getName(),
                long.class.getName(), Long.class.getName(),
                double.class.getName(), Double.class.getName(),
                boolean.class.getName(), Boolean.class.getName());
        private static final Set<String> PRIMITIVE = Set.of(int.class.getName(), long.class.getName(), double.class.getName(), boolean.class.getName());

        private final boolean isPrimitive;

        public ScalarGetterProperty(String typeName, String name, String valueExpression) {
            super(name, valueExpression);
            this.isPrimitive = PRIMITIVE.contains(typeName);
        }

        @Override public void write(StringBuilder out) {
            var getterExpression = "out.write(\"" + name() + "\", " + valueExpression + ");\n";
            if (isPrimitive) {
                out
                        .append("        ").append(getterExpression);
            } else {
                out
                        .append("        if (").append(valueExpression).append(" != null) {\n")
                        .append("            ").append(getterExpression)
                        .append("        } else if (context.writeNullValues()) {\n")
                        .append("            out.writeNull(\"").append(name()).append("\");\n")
                        .append("        }\n");
            }
        }
    }

    /**
     * Write values that are JSON arrays.
     */
    private static class ArrayGetterProperty extends GetterProperty {
        public ArrayGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.writeKey(\"").append(name()).append("\");\n")
                    .append("            ApJsonbProvider.jsonbWriterFor(").append(valueExpression)
                    .append(".getClass()).toJson(").append(valueExpression).append(", out, context);\n")
                    .append("        }\n");
        }
    }

    /**
     * Write values that are JSON objects.
     */
    private static class ObjectGetterProperty extends GetterProperty {
        public ObjectGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.writeKey(\"").append(name()).append("\");\n")
                    /**/.append("            ApJsonbProvider.jsonbWriterFor(").append(valueExpression)
                    /**/.append(".getClass()).toJson(").append(valueExpression).append(", out, context);\n")
                    .append("        }\n");
        }
    }
}
