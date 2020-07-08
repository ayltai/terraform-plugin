package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class BaseApplyOptions extends StatefulOptions {
    protected String       backup;
    protected boolean      compactWarnings;
    protected int          parallelism = 10;
    protected String       state;
    protected List<String> targets     = new ArrayList<>();

    public BaseApplyOptions(@Nonnull final String name) {
        super(name);
    }

    /**
     * Sets the path to backup the existing state file.
     * @param backup The path to backup the existing state file.
     */
    public void backup(@Nonnull final String backup) {
        this.backup = backup;
    }

    /**
     * if {@code true} is specified, shows warnings in a more compact form that includes only the summary messages if the warnings Terraform produces are not accompanied by errors.
     * <p>Default is {@code false}.</p>
     * @param compactWarnings {@code true} to shows warnings in a more compact form that includes only the summary messages if the warnings Terraform produces are not accompanied by errors.
     */
    public void compactWarnings(final boolean compactWarnings) {
        this.compactWarnings = compactWarnings;
    }

    /**
     * Limits the number of concurrent operation as Terraform <a href="https://www.terraform.io/docs/internals/graph.html#walking-the-graph">walks the graph</a>.
     * <p>Defaults is 10.</p>
     * @param parallelism The number of concurrent operation as Terraform <a href="https://www.terraform.io/docs/internals/graph.html#walking-the-graph">walks the graph</a>.
     */
    public void parallelism(final int parallelism) {
        this.parallelism = parallelism;
    }

    /**
     * Set the path to the state file.
     * <p>Ignored when <a href="https://www.terraform.io/docs/state/remote.html">remote state</a> is used.</p>
     * <p>Default is {@code terraform.tfstate}.</p>
     * @param state The path to the state file.
     */
    public void state(@Nonnull final String state) {
        this.state = state;
    }

    /**
     * <a href="https://www.terraform.io/docs/internals/resource-addressing.html">Resource Addresses</a> to target.
     * @param targets <a href="https://www.terraform.io/docs/internals/resource-addressing.html">Resource Addresses</a> to target.
     */
    public void targets(@Nonnull final List<String> targets) {
        this.targets = targets;
    }
}
