package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class NewWorkspaceTaskTests extends CommandLineTaskTests {
    private static final String WORKSPACE = "main";

    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(NewWorkspaceTask.TASK_NAME) instanceof NewWorkspaceTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final NewWorkspaceTask task = this.getTask(NewWorkspaceTask.TASK_NAME);
        task.workspace.set(NewWorkspaceTaskTests.WORKSPACE);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("workspace"));
        Assertions.assertTrue(args.contains("new"));
        Assertions.assertTrue(args.contains(NewWorkspaceTaskTests.WORKSPACE));
    }


    @Test
    public void testExec() {
        final NewWorkspaceTask task = this.getTask(NewWorkspaceTask.TASK_NAME);
        task.source.set(CommandLineTaskTests.SOURCE);
        task.workspace.set(NewWorkspaceTaskTests.WORKSPACE);
        task.exec();

        Assertions.assertTrue(task.getExecutionResult().isPresent());
        Assertions.assertEquals(0, task.getExecutionResult().get().getExitValue());
    }
}
