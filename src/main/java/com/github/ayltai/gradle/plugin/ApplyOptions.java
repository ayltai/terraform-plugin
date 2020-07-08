package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public class ApplyOptions extends BaseApplyOptions {
    protected boolean refresh = true;
    protected String  stateOut;
    protected String  in;

    public ApplyOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Updates the state prior to checking for differences if {@code true} is specified.
     * <p>Default is {@code true}.</p>
     * @param refresh {@code true} to update the state prior to checking for differences.
     */
    public void refresh(final boolean refresh) {
        this.refresh = refresh;
    }

    /**
     * Sets the path to write updated state file.
     * @param stateOut The path to write updated state file.
     */
    public void stateOut(@Nonnull final String stateOut) {
        this.stateOut = stateOut;
    }

    /**
     * The optional path to the Terraform source scripts or an execution plan to apply/destroy.
     * @param in The optional path to the Terraform source scripts or an execution plan to apply/destroy.
     */
    public void in(@Nonnull final String in) {
        this.in = in;
    }
}
