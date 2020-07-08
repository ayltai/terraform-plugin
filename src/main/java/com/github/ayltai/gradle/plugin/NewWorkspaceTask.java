package com.github.ayltai.gradle.plugin;

import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;

public class NewWorkspaceTask extends SelectWorkspaceTask {
    static final String TASK_NAME = "tfWorkspaceNew";

    protected final Property<String> state;

    public NewWorkspaceTask() {
        this.state = this.getProject().getObjects().property(String.class);

        this.setDescription("Create a new workspace.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "new";
    }

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = super.getCommandLineArgs();
        if (this.state.isPresent()) args.add("-state=" + this.state.get());

        return args;
    }
}
