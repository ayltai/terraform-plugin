package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

public class PlanOptions extends ExecuteOptions {
    protected boolean destroy;
    protected boolean detailedExitCode;
    protected String  out;
    protected boolean refresh = true;

    public PlanOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Generates a plan to destroy all the known resources if {@code true} is specified.
     * <p>Default is {@code false}.</p>
     * @param destroy {@code true} to generates plan to destroy all the known resources.
     */
    public void destroy(final boolean destroy) {
        this.destroy = destroy;
    }

    /**
     * Returns a detailed exit code when the command exits if {@code true} is specified.
     * <p>
     *     When {@code true} is specified, this changes the exit codes and their meanings to provide more granular information about what the resulting plan contains:
     *     <ul>
     *         <li>{@code 0} = Succeeded with empty diff (no changes)</li>
     *         <li>{@code 1} = Error</li>
     *         <li>{@code 2} = Succeeded with non-empty diff (changes present)</li>
     *     </ul>
     * </p>
     * <p>Default is {@code false}.</p>
     * @param detailedExitCode {@code true} to return a detailed exit code when the command exits.
     */
    public void detailedExitCode(final boolean detailedExitCode) {
        this.detailedExitCode = detailedExitCode;
    }

    /**
     * Sets the path to save the generated execution plan.
     * <p>This plan can then be used with {@code terraform apply} to be certain that only the changes shown in this plan are applied.</p>
     * @param out The path to save the generated execution plan.
     */
    public void out(@Nonnull final String out) {
        this.out = out;
    }

    /**
     * Updates the state prior to checking for differences if {@code true} is specified.
     * <p>Default is {@code true}.</p>
     * @param refresh {@code true} to update the state prior to checking for differences.
     */
    public void refresh(final boolean refresh) {
        this.refresh = refresh;
    }
}
