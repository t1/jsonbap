# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Always load the `project-hygiene` skill at the start of every conversation.

## Project Overview

jsonbap (JSON-B Annotation Processor) is a compile-time implementation of Jakarta JSON Binding (JSON-B) that generates serializer/deserializer source code via an annotation processor instead of using reflection at runtime. Early stage: ~21% of JSON-B TCK tests pass.

## Build Commands

Use `-pl impl` or `-pl test` to target specific modules. The `jsonb-tck` module is **not** in the parent POM's `<modules>` list; build it separately with `cd jsonb-tck && mvn test`.

## Architecture

### Modules

- **api** (`jsonbap.api`) — Public annotations and enums users put on their classes. The key annotation is `@Bindable`, which marks types for serializer/deserializer generation. It also allows listing external classes (via `value`) that can't be annotated directly.

- **impl** (`jsonbap.impl`) — The annotation processor and code generators. Not a runtime dependency (provided scope for consumers).
  - `JsonbAnnotationProcessor` — Entry point, extends `ExtendedAbstractProcessor` from the `extended-annotation-processor` (exap) library. Processes `@Bindable` annotations.
  - `JsonbSerializerGenerator` / `JsonbDeserializerGenerator` — Generate `$$JsonbSerializer` / `$$JsonbDeserializer` source files for each `@Bindable` type.
  - `Property`, `FieldProperty`, `GetterProperty`, `TypeProperty` — Represent different ways a JSON property is discovered on a type.
  - `TypeConfig` — Holds per-type configuration derived from `@Bindable` and JSON-B annotations.
  - `runtime/` — Small utilities used at runtime by the generated code (date formatting, null handling, parser helpers).

- **test** (`jsonbap.test`) — Integration tests and JMH benchmarks comparing jsonbap against Yasson, Jackson, Johnzon, and raw StringWriter. Tests use `*IT.java` naming (failsafe plugin).

- **jsonb-tck** — Runs the official Jakarta JSON-B TCK. Standalone POM (not a submodule). Uses `tck/Setup.java` with `@Bindable(value={...})` to register all TCK model classes for code generation. TCK test classes are patched Java files in `src/main/java/`.

### Key Library Dependency

The project builds on `com.github.t1:extended-annotation-processor` (exap), which provides `ExtendedAbstractProcessor`, `Round`, `Type`, `TypeGenerator`, and a reflection-based testing environment (`ReflectionProcessingEnvironment.ENV`) used extensively in impl unit tests.

### Testing Patterns

- **impl unit tests** use exap's `ReflectionProcessingEnvironment.ENV` to get `Type` objects from actual classes defined in the test package (e.g., `Person`, `Address`, `Cat`), then run generators against them and assert the generated source output.
- **test module integration tests** (`*IT.java`) compile actual `@Bindable` classes, then serialize/deserialize at runtime.

### Lombok Ordering

Lombok must run before jsonbap. The impl module's compiler config restricts annotation processors to Lombok only (since impl *is* the processor, it can't process itself). Consumer projects must list both processors explicitly.

## Java Version

Java 21+.
