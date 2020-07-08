package com.github.ayltai.gradle.plugin;

import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;

public class DeleteWorkspaceTask extends SelectWorkspaceTask {
    static final String TASK_NAME = "tfWorkspaceDelete";

    protected final Property<Boolean> force;

    public DeleteWorkspaceTask() {
        this.force = this.getProject().getObjects().property(Boolean.class);

        this.setDescription("Delete an existing workspace.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "delete";
    }

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = super.getCommandLineArgs();
        if (this.force.getOrElse(false)) args.add("-force");

        return args;
    }
}
