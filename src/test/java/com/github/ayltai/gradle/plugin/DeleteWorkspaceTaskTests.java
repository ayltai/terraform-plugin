package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class DeleteWorkspaceTaskTests extends CommandLineTaskTests {
    private static final String WORKSPACE = "main";

    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(DeleteWorkspaceTask.TASK_NAME) instanceof DeleteWorkspaceTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final DeleteWorkspaceTask task = this.getTask(DeleteWorkspaceTask.TASK_NAME);
        task.workspace.set(DeleteWorkspaceTaskTests.WORKSPACE);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("workspace"));
        Assertions.assertTrue(args.contains("delete"));
        Assertions.assertTrue(args.contains(DeleteWorkspaceTaskTests.WORKSPACE));
    }


    @Test
    public void testExec() {
        final NewWorkspaceTask newWorkspaceTask = this.getTask(NewWorkspaceTask.TASK_NAME);
        newWorkspaceTask.source.set(CommandLineTaskTests.SOURCE);
        newWorkspaceTask.workspace.set(DeleteWorkspaceTaskTests.WORKSPACE);
        newWorkspaceTask.exec();

        Assertions.assertTrue(newWorkspaceTask.getExecutionResult().isPresent());
        Assertions.assertEquals(0, newWorkspaceTask.getExecutionResult().get().getExitValue());

        final SelectWorkspaceTask selectWorkspaceTask = this.getTask(SelectWorkspaceTask.TASK_NAME);
        selectWorkspaceTask.source.set(CommandLineTaskTests.SOURCE);
        newWorkspaceTask.workspace.set("default");
        selectWorkspaceTask.exec();

        Assertions.assertTrue(selectWorkspaceTask.getExecutionResult().isPresent());
        Assertions.assertEquals(0, selectWorkspaceTask.getExecutionResult().get().getExitValue());

        final DeleteWorkspaceTask deleteWorkspaceTask = this.getTask(DeleteWorkspaceTask.TASK_NAME);
        deleteWorkspaceTask.source.set(CommandLineTaskTests.SOURCE);
        deleteWorkspaceTask.workspace.set(DeleteWorkspaceTaskTests.WORKSPACE);
        deleteWorkspaceTask.exec();

        Assertions.assertTrue(deleteWorkspaceTask.getExecutionResult().isPresent());
        Assertions.assertEquals(0, deleteWorkspaceTask.getExecutionResult().get().getExitValue());
    }
}
