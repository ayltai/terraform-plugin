package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public class InitOptions extends StatefulOptions {
    protected boolean useBackend = true;
    protected boolean upgrade;

    public InitOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Initializes a working directory for validation without accessing any configured remote backend if {@code true} is specified.
     * <p>Default is {@code true}.</p>
     * @param useBackend {@code true} to initialize a working directory for validation without accessing any configured remote backend.
     */
    public void useBackend(final boolean useBackend) {
        this.useBackend = useBackend;
    }

    /**
     * Opts to upgrade modules and plugins as part of their respective installation steps is {@code true} is specified.
     * <p>Default is {@code false}</p>
     * @param upgrade {@code true} to opt to upgrade modules and plugins as part of their respective installation steps.
     */
    public void upgrade(final boolean upgrade) {
        this.upgrade = upgrade;
    }
}
