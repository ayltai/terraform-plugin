package com.github.ayltai.gradle.plugin;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class DownloadTaskTests extends TaskTests {
    @Test
    public void testSetUp() {
        Assertions.assertTrue(this.getTask(DownloadTask.TASK_NAME) instanceof DownloadTask);
    }

    @Test
    public void testRun() {
        final DownloadTask task = this.getTask(DownloadTask.TASK_NAME);
        task.toolVersion.set("0.12.28");
        task.run();

        Assertions.assertTrue(new File(task.getOutputDirectory(), Constants.TERRAFORM).exists());
    }
}
