package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

import org.gradle.api.tasks.Internal;

public class ShowWorkspaceTask extends BaseWorkspaceTask {
    static final String TASK_NAME = "tfWorkspaceShow";

    public ShowWorkspaceTask() {
        this.setDescription("Output the current workspace.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "show";
    }
}
