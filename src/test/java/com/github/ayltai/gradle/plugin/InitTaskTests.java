package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class InitTaskTests extends CliTaskTests {
    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(InitTask.TASK_NAME) instanceof InitTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final InitTask task = this.getTask(InitTask.TASK_NAME);
        task.useBackend.set(false);
        task.input.set(false);
        task.lock.set(true);
        task.lockTimeout.set(5);
        task.upgrade.set(true);
        task.noColor.set(true);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("init"));
        Assertions.assertTrue(args.contains("-backend=false"));
        Assertions.assertTrue(args.contains("-input=false"));
        Assertions.assertTrue(args.contains("-lock=true"));
        Assertions.assertTrue(args.contains("-lock-timeout=5"));
        Assertions.assertTrue(args.contains("-upgrade"));
        Assertions.assertTrue(args.contains("-no-color"));
    }

    @Test
    public void testExec() {
        final InitTask task = this.getTask(InitTask.TASK_NAME);
        task.source.set(CliTaskTests.SOURCE);
        task.exec();

        Assertions.assertTrue(task.getPluginsDirectory().exists());
    }

    @Test
    public void testSaveCredentials() throws IOException {
        final InitTask task = this.getTask(InitTask.TASK_NAME);

        Assertions.assertFalse(task.getCredentials().exists());

        task.backend.set("backend");
        task.apiToken.set("token");
        task.saveCredentials();

        Assertions.assertTrue(task.getCredentials().exists());
    }
}
