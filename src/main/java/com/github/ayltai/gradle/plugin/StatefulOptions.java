package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public abstract class StatefulOptions extends Options {
    protected boolean input = true;
    protected boolean lock;
    protected int     lockTimeout;

    public StatefulOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Asks for input if necessary if {@code true} is specified.
     * <p>Default is {@code true}</p>
     * @param input {@code true} to ask for input if necessary.
     */
    public void input(final boolean input) {
        this.input = input;
    }

    /**
     * Disables locking of state files during state-related operations if {@code true} is specified.
     * <p>Default is {@code false}</p>
     * @param lock {@code true} to disable locking of state files during state-related operations.
     */
    public void lock(final boolean lock) {
        this.lock = lock;
    }

    /**
     * Overrides the time Terraform will wait to acquire a state lock with the value specified.
     * <p>Default is {@code 0}, which causes immediate failure if the lock is already held by another process.</p>
     * @param lockTimeout The time Terraform will wait to acquire a state lock.
     */
    public void lockTimeout(final int lockTimeout) {
        this.lockTimeout = lockTimeout;
    }
}
