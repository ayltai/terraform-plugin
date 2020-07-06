package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public class FmtOptions extends Options {
    protected boolean list;
    protected boolean write;
    protected boolean diff;
    protected boolean check;
    protected boolean recursive;

    public FmtOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Lists the files containing formatting inconsistencies if {@code true} is specified.
     * <p>Default is {@code false}.</p>
     * @param list {@code true} to list the files containing formatting inconsistencies.
     */
    public void list(final boolean list) {
        this.list = list;
    }

    /**
     * Overwrites the input files if {@code true} is specified.
     * <p>Default is {@code false}.</p>
     * @param write {@code true} to overwrite the input files.
     */
    public void write(final boolean write) {
        this.write = write;
    }

    /**
     * Displays diffs of formatting changes if {@code true} is specified.
     * <p>Default is {@code false}.</p>
     * @param diff {@code true} to display diffs of formatting changes.
     */
    public void diff(final boolean diff) {
        this.diff = diff;
    }

    /**
     * Checks if the input is formatted if {@code true} is specified.
     * <p>Exit status will be {@code 0} if all input is properly formatted and non-zero otherwise.</p>
     * <p>Default is {@code false}.</p>
     * @param check {@code true} to check if the input is formatted.
     */
    public void check(final boolean check) {
        this.check = check;
    }

    /**
     * Processes files in subdirectories if {@code true} is specified.
     * <p>By default, only the given directory (or current directory) is processed.</p>
     * <p>Default is {@code false}.</p>
     * @param recursive {@code true} to process files in subdirectories.
     */
    public void recursive(final boolean recursive) {
        this.recursive = recursive;
    }
}
