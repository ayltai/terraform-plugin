package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.tasks.Internal;

public class DestroyTask extends ApplyOrDestroyTask {
    static final String TASK_NAME = "tfDestroy";

    public DestroyTask() {
        this.setDescription("Destroy the Terraform-managed infrastructure");
    }

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("destroy");
        args.addAll(super.getCommandLineArgs());

        return args;
    }
}
