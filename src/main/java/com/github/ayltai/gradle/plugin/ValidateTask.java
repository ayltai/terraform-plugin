package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public class ValidateTask extends CommandLineTask {
    static final String TASK_NAME = "tfValidate";

    protected final Property<Boolean> json;

    public ValidateTask() {
        this.json = this.getProject().getObjects().property(Boolean.class);

        this.setGroup(Constants.GROUP_VERIFICATION);
        this.setDescription("Validate the Terraform source scripts to verify whether they are syntactically valid and internally consistent.");
    }

    @Nonnull
    @Input
    public Property<Boolean> getJson() {
        return this.json;
    }

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("validate");

        if (this.json.getOrElse(false)) args.add("-json");
        if (this.noColor.getOrElse(false)) args.add("-no-color");

        return args;
    }
}
