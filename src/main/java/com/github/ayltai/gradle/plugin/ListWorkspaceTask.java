package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

import org.gradle.api.tasks.Internal;

public class ListWorkspaceTask extends BaseWorkspaceTask {
    static final String TASK_NAME = "tfWorkspaceList";

    public ListWorkspaceTask() {
        this.setDescription("List all existing workspaces.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "list";
    }
}
