package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public class FmtTask extends CliTask {
    static final String TASK_NAME = "tfFmt";

    //region Properties

    protected final Property<Boolean> list;
    protected final Property<Boolean> write;
    protected final Property<Boolean> diff;
    protected final Property<Boolean> check;
    protected final Property<Boolean> recursive;

    //endregion

    public FmtTask() {
        this.list      = this.getProject().getObjects().property(Boolean.class);
        this.write     = this.getProject().getObjects().property(Boolean.class);
        this.diff      = this.getProject().getObjects().property(Boolean.class);
        this.check     = this.getProject().getObjects().property(Boolean.class);
        this.recursive = this.getProject().getObjects().property(Boolean.class);

        this.setGroup(Constants.GROUP_VERIFICATION);
        this.setDescription("Rewrite Terraform source scripts to a canonical format and style.");
    }

    //region Gradle task inputs

    @Nonnull
    @Input
    public Property<Boolean> getList() {
        return this.list;
    }

    @Nonnull
    @Input
    public Property<Boolean> getWrite() {
        return this.write;
    }

    @Nonnull
    @Input
    public Property<Boolean> getDiff() {
        return this.diff;
    }

    @Nonnull
    @Input
    public Property<Boolean> getCheck() {
        return this.check;
    }

    @Nonnull
    @Input
    public Property<Boolean> getRecursive() {
        return this.recursive;
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("fmt");

        if (this.list.getOrElse(false)) args.add("-list=true");
        if (this.write.getOrElse(false)) args.add("-write=true");
        if (this.diff.getOrElse(false)) args.add("-diff");
        if (this.check.getOrElse(false)) args.add("-check");
        if (this.recursive.getOrElse(false)) args.add("-recursive");

        return args;
    }
}
