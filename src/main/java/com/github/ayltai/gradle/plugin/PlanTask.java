package com.github.ayltai.gradle.plugin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

public class PlanTask extends BaseApplyTask {
    static final String TASK_NAME = "tfPlan";

    //region Properties

    protected final Property<Boolean> destroy;
    protected final Property<Boolean> detailedExitCode;
    protected final Property<String>  out;
    protected final Property<Boolean> refresh;

    //endregion

    public PlanTask() {
        this.destroy          = this.getProject().getObjects().property(Boolean.class);
        this.detailedExitCode = this.getProject().getObjects().property(Boolean.class);
        this.out              = this.getProject().getObjects().property(String.class);
        this.refresh          = this.getProject().getObjects().property(Boolean.class);

        this.setGroup(Constants.GROUP_BUILD_SETUP);
        this.setDescription("Create a Terraform execution plan.");
    }

    //region Gradle task inputs

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getDestroy() {
        return this.destroy;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getDetailedExitCode() {
        return this.detailedExitCode;
    }

    @Nonnull
    @Optional
    @Input
    public Property<String> getOut() {
        return this.out;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getRefresh() {
        return this.refresh;
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = new ArrayList<>();
        args.add(this.getCli().getAbsolutePath());
        args.add("plan");

        if (this.destroy.getOrElse(false)) args.add("-destroy");
        if (this.detailedExitCode.getOrElse(false)) args.add("-detailed-exitcode");
        if (this.out.isPresent() && this.out.getOrNull() != null) args.add("-out=" + this.out.get());
        if (!this.refresh.getOrElse(true)) args.add("-refresh=false");

        args.addAll(super.getCommandLineArgs());

        return args;
    }
}
