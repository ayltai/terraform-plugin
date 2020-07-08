package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.tasks.Internal;

public abstract class BaseWorkspaceTask extends CommandLineTask {
    @Nonnull
    @Internal
    protected abstract String getCommand();

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("workspace");
        args.add(this.getCommand());

        return args;
    }
}
