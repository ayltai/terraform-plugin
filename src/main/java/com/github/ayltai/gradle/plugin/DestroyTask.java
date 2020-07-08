package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

import org.gradle.api.tasks.Internal;

public class DestroyTask extends ApplyTask {
    static final String TASK_NAME = "tfDestroy";

    public DestroyTask() {
        this.setDescription("Destroy the Terraform-managed infrastructure.");
    }

    @Nonnull
    @Internal
    @Override
    protected String getCommand() {
        return "destroy";
    }
}
