package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public class ValidateOptions extends Options {
    protected boolean json;

    public ValidateOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Produces output in a machine-readable JSON format if {@code true} is specified, suitable for use in text editor integrations and other automated systems.
     * <p>Default is {@code false}.</p>
     * @param json {@code true} to produce output in a machine-readable JSON format.
     */
    public void json(final boolean json) {
        this.json = json;
    }
}
