package com.github.ayltai.gradle.plugin;

import java.util.List;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public abstract class ApplyOrDestroyTask extends ExecuteTask {
    //region Properties

    protected final Property<Boolean> refresh;
    protected final Property<String>  stateOut;
    protected final Property<String>  in;

    //endregion

    public ApplyOrDestroyTask() {
        this.refresh  = this.getProject().getObjects().property(Boolean.class);
        this.stateOut = this.getProject().getObjects().property(String.class);
        this.in       = this.getProject().getObjects().property(String.class);

        this.setGroup(Constants.GROUP_BUILD);
    }

    //region Gradle task inputs

    @Nonnull
    @Input
    public Property<Boolean> getRefresh() {
        return this.refresh;
    }

    @Nonnull
    @Input
    public Property<String> getStateOut() {
        return this.stateOut;
    }

    @Nonnull
    @Input
    public Property<String> getIn() {
        return this.in;
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = super.getCommandLineArgs();
        args.add("-auto-approve");

        if (!this.refresh.getOrElse(true)) args.add("-refresh=false");
        if (this.stateOut.isPresent()) args.add("-state-out=" + this.stateOut.get());
        if (this.in.isPresent()) args.add(this.in.get());

        return args;
    }
}
