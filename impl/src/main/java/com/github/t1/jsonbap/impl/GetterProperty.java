package com.github.t1.jsonbap.impl;

import com.github.t1.exap.reflection.Method;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

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
        if ("int".equals(typeName))
            return new IntGetterProperty(name, valueExpression);
        if ("java.lang.String".equals(typeName))
            return new StringGetterProperty(name, valueExpression);
        if ("java.lang.Integer".equals(typeName))
            return new IntegerGetterProperty(name, valueExpression);
        if (typeName.startsWith("java.util.List<") && typeName.endsWith(">"))
            return new CollectionGetterProperty(name, valueExpression);
        return new ObjectGetterProperty(name, valueExpression);
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Getter @Accessors(fluent = true) private final String name;
    protected final String valueExpression;

    private static class IntGetterProperty extends GetterProperty {
        public IntGetterProperty(String valueExpression, String name) {super(valueExpression, name);}

        @Override public void write(StringBuilder out) {
            out.append("        out.write(\"").append(name()).append("\", ").append(valueExpression).append(");\n");
        }
    }

    private static class IntegerGetterProperty extends GetterProperty {
        public IntegerGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.write(\"").append(name()).append("\", ").append(valueExpression).append(");\n")
                    .append("        }\n");
        }
    }

    private static class StringGetterProperty extends GetterProperty {
        public StringGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            // TODO use String$$JsonbWriter
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.write(\"").append(name()).append("\", ").append(valueExpression).append(");\n")
                    .append("        }\n");
        }
    }

    private static class CollectionGetterProperty extends GetterProperty {
        public CollectionGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.writeKey(\"").append(name()).append("\");\n")
                    .append("            ApJsonbProvider.jsonbWriterFor(").append(valueExpression)
                    .append(").toJson(").append(valueExpression).append(", out);\n")
                    .append("        }\n");
        }
    }

    private static class ObjectGetterProperty extends GetterProperty {
        public ObjectGetterProperty(String name, String valueExpression) {super(name, valueExpression);}

        @Override public void write(StringBuilder out) {
            out
                    .append("        if (").append(valueExpression).append(" != null) {\n")
                    .append("            out.writeKey(\"").append(name()).append("\");\n")
                    /**/.append("            ApJsonbProvider.jsonbWriterFor(").append(valueExpression)
                    /**/.append(").toJson(").append(valueExpression).append(", out);\n")
                    .append("        }\n");
        }
    }
}
