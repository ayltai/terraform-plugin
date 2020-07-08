package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class SelectWorkspaceTaskTests extends CommandLineTaskTests {
    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(SelectWorkspaceTask.TASK_NAME) instanceof SelectWorkspaceTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final SelectWorkspaceTask task = this.getTask(SelectWorkspaceTask.TASK_NAME);
        final List<String>        args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("workspace"));
        Assertions.assertTrue(args.contains("select"));
        Assertions.assertTrue(args.contains("default"));
    }


    @Test
    public void testExec() {
        final SelectWorkspaceTask task = this.getTask(SelectWorkspaceTask.TASK_NAME);
        task.source.set(CommandLineTaskTests.SOURCE);
        task.exec();

        Assertions.assertTrue(task.getExecutionResult().isPresent());
        Assertions.assertEquals(0, task.getExecutionResult().get().getExitValue());
    }
}
