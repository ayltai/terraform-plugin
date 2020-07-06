package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;

public class InitTask extends StatefulTask {
    static final String TASK_NAME = "tfInit";

    //region Properties

    protected final Property<Boolean> useBackend;
    protected final Property<Boolean> upgrade;

    //endregion

    public InitTask() {
        this.useBackend = this.getProject().getObjects().property(Boolean.class);
        this.upgrade    = this.getProject().getObjects().property(Boolean.class);

        this.setGroup(Constants.GROUP_BUILD_SETUP);
        this.setDescription("Initialize Terraform source scripts");
    }

    //region Gradle task inputs and outputs

    @Nonnull
    @Input
    public Property<Boolean> getUseBackend() {
        return this.useBackend;
    }

    @Nonnull
    @Input
    public Property<Boolean> getNoColor() {
        return this.noColor;
    }

    @Nonnull
    @Input
    public Property<Boolean> getUpgrade() {
        return this.upgrade;
    }

    @Nonnull
    @OutputDirectory
    public File getPluginsDirectory() {
        return new File(this.getSourceDirectory(), ".terraform");
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("init");

        if (!this.useBackend.getOrElse(true)) args.add("-backend=false");
        if (this.upgrade.getOrElse(false)) args.add("-upgrade");

        args.addAll(super.getCommandLineArgs());

        return args;
    }
}
