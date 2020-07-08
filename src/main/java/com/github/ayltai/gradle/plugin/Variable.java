package com.github.ayltai.gradle.plugin;

import java.io.Serializable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Variable<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final String name;
    protected final T      value;

    protected Variable(@Nonnull final String name, @Nullable final T value) {
        this.name  = name;
        this.value = value;
    }

    public static <T> Variable<T> create(@Nonnull final String name, @Nullable final T value) {
        return new Variable<>(name, value);
    }
}
