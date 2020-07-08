package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class TerraformPlugin implements Plugin<Project> {
    @Override
    public void apply(@Nonnull final Project project) {
        final TerraformExtension extension = project.getExtensions().create("terraform", TerraformExtension.class, project.getObjects());

        TerraformPlugin.registerDownloadTask(project, extension);
        TerraformPlugin.registerInitTask(project, extension);
        TerraformPlugin.registerFmtTask(project, extension);
        TerraformPlugin.registerValidateTask(project, extension);
        TerraformPlugin.registerPlanTask(project, extension);
        TerraformPlugin.registerApplyTask(project, extension);
        TerraformPlugin.registerDestroyTask(project, extension);
        TerraformPlugin.registerListWorkspaceTask(project, extension);
        TerraformPlugin.registerShowWorkspaceTask(project, extension);
        TerraformPlugin.registerSelectWorkspaceTask(project, extension);
        TerraformPlugin.registerNewWorkspaceTask(project, extension);
        TerraformPlugin.registerDeleteWorkspaceTask(project, extension);
    }

    private static void registerDownloadTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(DownloadTask.TASK_NAME, DownloadTask.class)
            .configure(task -> {
                task.toolVersion.set(extension.getToolVersion());
                task.forceDownload.set(extension.getForceDownload());
            });
    }

    private static void registerInitTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(InitTask.TASK_NAME, InitTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                final InitOptions options = extension.initOptions.maybeCreate("init");

                task.useBackend.set(options.useBackend);
                task.upgrade.set(options.upgrade);

                TerraformPlugin.configureStatefulTask(task, extension, options);
            });
    }

    private static void registerFmtTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(FmtTask.TASK_NAME, FmtTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                final FmtOptions options = extension.fmtOptions.maybeCreate("fmt");

                task.source.set(extension.getSource());
                task.list.set(options.list);
                task.write.set(options.write);
                task.diff.set(options.diff);
                task.check.set(options.check);
                task.recursive.set(options.recursive);
                task.noColor.set(options.noColor);
            });
    }

    private static void registerValidateTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(ValidateTask.TASK_NAME, ValidateTask.class)
            .configure(task -> {
                task.dependsOn(InitTask.TASK_NAME);

                final ValidateOptions options = extension.validateOptions.maybeCreate("fmt");

                task.source.set(extension.getSource());
                task.json.set(options.json);
                task.noColor.set(options.noColor);
            });
    }

    private static void registerPlanTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(PlanTask.TASK_NAME, PlanTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME)
                    .dependsOn(InitTask.TASK_NAME);

                final PlanOptions options = extension.planOptions.maybeCreate("plan");

                task.destroy.set(options.destroy);
                task.detailedExitCode.set(options.detailedExitCode);
                task.out.set(options.out);
                task.refresh.set(options.refresh);

                TerraformPlugin.configureStatefulTask(task, extension, options);
                TerraformPlugin.configureBaseApplyTask(task, extension, options);
            });
    }

    private static void registerApplyTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(ApplyTask.TASK_NAME, ApplyTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME)
                    .dependsOn(InitTask.TASK_NAME);

                final ApplyOptions options = extension.applyOptions.maybeCreate("apply");

                task.refresh.set(options.refresh);
                task.stateOut.set(options.stateOut);
                task.in.set(options.in);

                TerraformPlugin.configureStatefulTask(task, extension, options);
                TerraformPlugin.configureBaseApplyTask(task, extension, options);
            });
    }

    private static void registerDestroyTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(DestroyTask.TASK_NAME, DestroyTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME)
                    .dependsOn(InitTask.TASK_NAME);

                final ApplyOptions options = extension.destroyOptions.maybeCreate("destroy");

                task.refresh.set(options.refresh);
                task.stateOut.set(options.stateOut);
                task.in.set(options.in);

                TerraformPlugin.configureStatefulTask(task, extension, options);
                TerraformPlugin.configureBaseApplyTask(task, extension, options);
            });
    }

    private static void registerListWorkspaceTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(ListWorkspaceTask.TASK_NAME, ListWorkspaceTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                task.source.set(extension.getSource());
            });
    }

    private static void registerShowWorkspaceTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(ShowWorkspaceTask.TASK_NAME, ShowWorkspaceTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                task.source.set(extension.getSource());
            });
    }

    private static void registerSelectWorkspaceTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(SelectWorkspaceTask.TASK_NAME, SelectWorkspaceTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                task.source.set(extension.getSource());
                task.workspace.set(extension.getWorkspace());
            });
    }

    private static void registerNewWorkspaceTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(NewWorkspaceTask.TASK_NAME, NewWorkspaceTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                task.source.set(extension.getSource());
                task.workspace.set(extension.getWorkspace());
            });
    }

    private static void registerDeleteWorkspaceTask(@Nonnull final Project project, @Nonnull final TerraformExtension extension) {
        project.getTasks()
            .register(DeleteWorkspaceTask.TASK_NAME, DeleteWorkspaceTask.class)
            .configure(task -> {
                task.dependsOn(DownloadTask.TASK_NAME);

                task.source.set(extension.getSource());
                task.workspace.set(extension.getWorkspace());
            });
    }

    private static void configureStatefulTask(@Nonnull final StatefulTask task, @Nonnull final TerraformExtension extension, @Nonnull final StatefulOptions options) {
        task.backend.set(extension.getBackend());
        task.apiToken.set(extension.getApiToken());
        task.source.set(extension.getSource());
        task.input.set(options.input);
        task.lock.set(options.lock);
        task.lockTimeout.set(options.lockTimeout);
        task.noColor.set(options.noColor);
    }

    private static void configureBaseApplyTask(@Nonnull final BaseApplyTask task, @Nonnull final TerraformExtension extension, @Nonnull final BaseApplyOptions options) {
        task.backup.set(options.backup);
        task.compactWarnings.set(options.compactWarnings);
        task.parallelism.set(options.parallelism);
        task.state.set(options.state);
        task.targets.set(options.targets);

        final Variables variables = extension.variables.maybeCreate("variables");
        task.variables.add(variables);
    }
}
