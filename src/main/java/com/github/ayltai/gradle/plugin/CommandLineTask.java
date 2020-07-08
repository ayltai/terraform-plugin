package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandLineTask extends Exec {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineTask.class);

    //region Properties

    protected final Property<String>  source;
    protected final Property<Boolean> noColor;

    //endregion

    protected CommandLineTask() {
        this.source  = this.getProject().getObjects().property(String.class);
        this.noColor = this.getProject().getObjects().property(Boolean.class);
    }

    //region Gradle task inputs

    @Nonnull
    @Optional
    @Input
    public Property<String> getSource() {
        return this.source;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getNoColor() {
        return this.noColor;
    }

    //endregion

    //region Getters

    @Nonnull
    @Internal
    protected File getSourceDirectory() {
        return new File(this.getProject().getRootDir(), this.source.get());
    }

    @Nonnull
    @Internal
    protected File getCli() {
        return new File(((DownloadTask)this.getProject().getTasks().getByName(DownloadTask.TASK_NAME)).getOutputDirectory(), Constants.TERRAFORM);
    }

    @Nonnull
    @Internal
    protected abstract List<String> getCommandLineArgs();

    //endregion

    @TaskAction
    @Override
    protected void exec() {
        this.workingDir(this.getSourceDirectory())
            .commandLine(this.getCommandLineArgs());

        if (CommandLineTask.LOGGER.isDebugEnabled()) CommandLineTask.LOGGER.debug(String.join(" ", this.getCommandLine()));

        super.exec();
    }
}
