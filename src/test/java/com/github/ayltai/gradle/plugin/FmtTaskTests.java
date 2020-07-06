package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class FmtTaskTests extends CliTaskTests {
    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(FmtTask.TASK_NAME) instanceof FmtTask);
    }

    @Test
    public void testGetCommandLineArgs() {
        final FmtTask task = this.getTask(FmtTask.TASK_NAME);
        task.list.set(true);
        task.write.set(true);
        task.diff.set(true);
        task.check.set(true);
        task.recursive.set(true);

        final List<String> args = task.getCommandLineArgs();

        Assertions.assertEquals(Constants.TERRAFORM, new File(args.get(0)).getName());
        Assertions.assertTrue(args.contains("fmt"));
        Assertions.assertTrue(args.contains("-list=true"));
        Assertions.assertTrue(args.contains("-write=true"));
        Assertions.assertTrue(args.contains("-diff"));
        Assertions.assertTrue(args.contains("-check"));
        Assertions.assertTrue(args.contains("-recursive"));
    }

    @Test
    public void testExec() {
        final FmtTask task = this.getTask(FmtTask.TASK_NAME);
        task.source.set(CliTaskTests.SOURCE);
        task.exec();

        Assertions.assertTrue(task.getExecutionResult().isPresent());
        Assertions.assertEquals(0, task.getExecutionResult().get().getExitValue());
    }
}
