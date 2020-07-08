package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.Property;
import org.gradle.api.resources.ResourceException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.os.OperatingSystem;

import org.slf4j.LoggerFactory;

public abstract class StatefulTask extends CommandLineTask {
    private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(StatefulTask.class);

    private static final String CREDENTIALS = "credentials \"%1$s\" {%n  token = \"%2$s\"%n}";

    //region Properties

    protected final Property<String>    backend;
    protected final Property<String>    apiToken;
    protected final Property<Boolean>   input;
    protected final Property<Boolean>   lock;
    protected final Property<Integer>   lockTimeout;
    protected final RegularFileProperty configFile;

    //endregion

    protected StatefulTask() {
        this.backend     = this.getProject().getObjects().property(String.class);
        this.apiToken    = this.getProject().getObjects().property(String.class);
        this.input       = this.getProject().getObjects().property(Boolean.class);
        this.lock        = this.getProject().getObjects().property(Boolean.class);
        this.lockTimeout = this.getProject().getObjects().property(Integer.class);
        this.configFile  = this.getProject().getObjects().fileProperty();
    }

    //region Gradle task inputs and outputs

    @Nonnull
    @Optional
    @Input
    public Property<String> getBackend() {
        return this.backend;
    }

    @Nonnull
    @Optional
    @Input
    public Property<String> getApiToken() {
        return this.apiToken;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getInput() {
        return this.input;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getLock() {
        return this.lock;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Integer> getLockTimeout() {
        return this.lockTimeout;
    }

    @Nonnull
    @OutputFile
    public File getCredentials() {
        return Paths.get(this.getProject().getBuildDir().getAbsolutePath(), Constants.TERRAFORM, OperatingSystem.current().isWindows() ? "terraform.rc" : ".terraformrc").toFile();
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();

        if (!this.input.getOrElse(false)) args.add("-input=false");
        if (this.lock.getOrElse(false)) args.add("-lock=true");
        if (this.lockTimeout.getOrElse(0) > 0) args.add("-lock-timeout=" + this.lockTimeout.get());
        if (this.noColor.getOrElse(false)) args.add("-no-color");

        return args;
    }

    @TaskAction
    @Override
    protected void exec() {
        try {
            this.saveCredentials();
        } catch (final IOException e) {
            throw new ResourceException("Failed to save credentials to " + this.getCredentials().getAbsolutePath(), e);
        }

        this.environment("TF_CLI_CONFIG_FILE", this.configFile.isPresent() ? this.configFile.getAsFile().get().getAbsolutePath() : this.getCredentials().getAbsolutePath());

        super.exec();
    }

    protected void saveCredentials() throws IOException {
        if (this.backend.isPresent() && this.apiToken.isPresent()) {
            if (StatefulTask.LOGGER.isInfoEnabled()) StatefulTask.LOGGER.info("Create credentials at " + this.getCredentials().getAbsolutePath());

            try (PrintStream outputStream = new PrintStream(new FileOutputStream(this.getCredentials()), true, StandardCharsets.US_ASCII.name())) {
                outputStream.println(String.format(Locale.US, StatefulTask.CREDENTIALS, this.backend.get(), this.apiToken.get()));
            }
        } else {
            StatefulTask.LOGGER.info("Skip creating credentials");
        }
    }
}
