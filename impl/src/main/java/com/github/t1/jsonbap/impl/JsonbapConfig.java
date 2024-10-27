package com.github.t1.jsonbap.impl;

import javax.annotation.processing.ProcessingEnvironment;

record JsonbapConfig(
        /// The TCK is overly specific by expecting the French number format to contain NBSP (0x00a0).
        /// Starting in JDK 13, this was changed to NNBSP (0x202f), aligning with the updated specs.
        /// The TCK should be fixed, but for now, we have to work around it,
        /// but only when that system property is set _at compile time_!
        ///
        /// @see <a href="https://github.com/jakartaee/jsonb-api/issues/272">issue-272</a>
        boolean frenchNnbspWorkaround,

        /// We detect some problems already at compile time, so we normally report that as error notification,
        /// which stops the build; however, the TCK expects the build to succeed, so we have to work around that.
        boolean throwJsonbExceptionsAtRuntime) {

    public JsonbapConfig() {this(false, false);}

    JsonbapConfig(ProcessingEnvironment processingEnv) {
        this(booleanOption(processingEnv, "jsonbap.old.style.fr.nbsp", "false"),
                booleanOption(processingEnv, "jsonbap.throw.exceptions.at.runtime", "false"));
    }

    private static boolean booleanOption(ProcessingEnvironment processingEnv, String key, @SuppressWarnings("SameParameterValue") String defaultValue) {
        return Boolean.parseBoolean(processingEnv.getOptions().getOrDefault(key, defaultValue));
    }
}
