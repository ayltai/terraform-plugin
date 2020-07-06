package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class PlanTaskTests extends CliTaskTests {
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
        Assertions.assertTrue(this.getTask(PlanTask.TASK_NAME) instanceof PlanTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final PlanTask task = this.getTask(PlanTask.TASK_NAME);

        task.out.set("main");
        task.input.set(false);
        task.lock.set(true);
        task.lockTimeout.set(5);
        task.noColor.set(true);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("plan"));
        Assertions.assertTrue(args.contains("-input=false"));
        Assertions.assertTrue(args.contains("-lock=true"));
        Assertions.assertTrue(args.contains("-lock-timeout=5"));
        Assertions.assertTrue(args.contains("-no-color"));
        Assertions.assertTrue(args.contains("-out=main"));
    }

    @Test
    public void testExec() {
        final PlanTask task = this.getTask(PlanTask.TASK_NAME);
        task.source.set(CliTaskTests.SOURCE);
        task.exec();

        Assertions.assertTrue(task.getExecutionResult().isPresent());
        Assertions.assertEquals(0, task.getExecutionResult().get().getExitValue());
    }

    @Test
    public void testSaveCredentials() throws IOException {
        final PlanTask task = this.getTask(PlanTask.TASK_NAME);

        Assertions.assertFalse(task.getCredentials().exists());

        task.backend.set("backend");
        task.apiToken.set("token");
        task.saveCredentials();

        Assertions.assertTrue(task.getCredentials().exists());
    }
}
