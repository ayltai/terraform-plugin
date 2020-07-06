package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class ValidateTaskTests extends CliTaskTests {
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();

        final InitTask task = this.getTask(InitTask.TASK_NAME);
        task.useBackend.set(false);
        task.source.set(CliTaskTests.SOURCE);
        task.exec();
    }

    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(ValidateTask.TASK_NAME) instanceof ValidateTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final ValidateTask task = this.getTask(ValidateTask.TASK_NAME);
        task.json.set(true);
        task.noColor.set(true);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("-json"));
        Assertions.assertTrue(args.contains("-no-color"));
    }

    @Test
    public void testExec() {
        final ValidateTask task = this.getTask(ValidateTask.TASK_NAME);
        task.source.set(CliTaskTests.SOURCE);
        task.exec();

        Assertions.assertTrue(task.getExecutionResult().isPresent());
        Assertions.assertEquals(0, task.getExecutionResult().get().getExitValue());
    }
}
