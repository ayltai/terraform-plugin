package com.github.ayltai.gradle.plugin;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public abstract class BaseApplyTask extends StatefulTask {
    //region Properties

    protected final Property<String>                      backup;
    protected final Property<Boolean>                     compactWarnings;
    protected final Property<Integer>                     parallelism;
    protected final Property<String>                      state;
    protected final ListProperty<String>                  targets;
    protected final NamedDomainObjectContainer<Variables> variables;

    //endregion

    public BaseApplyTask() {
        this.backup          = this.getProject().getObjects().property(String.class);
        this.compactWarnings = this.getProject().getObjects().property(Boolean.class);
        this.parallelism     = this.getProject().getObjects().property(Integer.class);
        this.state           = this.getProject().getObjects().property(String.class);
        this.targets         = this.getProject().getObjects().listProperty(String.class);
        this.variables       = this.getProject().getObjects().domainObjectContainer(Variables.class);
    }

    //region Gradle task inputs

    @Nonnull
    @Input
    public Property<String> getBackup() {
        return this.backup;
    }

    @Nonnull
    @Input
    public Property<Boolean> getCompactWarnings() {
        return this.compactWarnings;
    }

    @Nonnull
    @Input
    public Property<Integer> getParallelism() {
        return this.parallelism;
    }

    @Nonnull
    @Input
    public Property<String> getStateFile() {
        return this.state;
    }

    @Nonnull
    @Input
    public ListProperty<String> getTargets() {
        return this.targets;
    }

    @Nonnull
    @Input
    public NamedDomainObjectContainer<Variables> getVariables() {
        return this.variables;
    }

    //endregion

    @Nonnull
    @Internal
    @Override
    protected List<String> getCommandLineArgs() {
        final List<String> args = super.getCommandLineArgs();

        if (this.backup.isPresent() && this.backup.getOrNull() != null) args.add("-backup=" + this.backup.get());
        if (this.compactWarnings.getOrElse(false)) args.add("-compact-warnings");
        if (this.parallelism.isPresent()) args.add("-parallelism=" + this.parallelism.get());
        if (this.state.isPresent() && this.state.getOrNull() != null) args.add("-state=" + this.state.get());
        if (this.targets.isPresent() && !this.targets.get().isEmpty()) args.addAll(this.targets.get().stream().map(target -> "-target=" + target).collect(Collectors.toList()));

        if (!this.variables.isEmpty()) {
            this.variables.forEach(variable -> {
                args.addAll(variable.files.stream().map(file -> "-var-file=" + file.getAbsolutePath()).collect(Collectors.toList()));
                args.addAll(variable.vars.stream().map(var -> String.format(Locale.US, "-var '$1%s=$2%s'", var.name, var.value)).collect(Collectors.toList()));
            });
        }

        return args;
    }
}
