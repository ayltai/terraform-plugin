package com.github.ayltai.gradle.plugin;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;

public class SelectWorkspaceTask extends BaseWorkspaceTask {
    static final String TASK_NAME = "tfWorkspaceSelect";

    protected final Property<String> workspace;

    public SelectWorkspaceTask() {
        this.workspace = this.getProject().getObjects().property(String.class);

        this.setDescription("Choose a different workspace to use for further.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "select";
    }

    @Nullable
    @Internal
    protected String getWorkspace() {
        return this.workspace.getOrNull();
    }

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = super.getCommandLineArgs();

        if (this.getWorkspace() != null) args.add(this.getWorkspace());

        return args;
    }
}
