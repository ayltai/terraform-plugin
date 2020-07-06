package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Variable<T> {
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
