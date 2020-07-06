package com.github.ayltai.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.gradle.internal.impldep.com.google.api.client.util.Throwables;

import org.junit.jupiter.api.BeforeEach;

public abstract class CliTaskTests extends TaskTests {
    protected static final String SOURCE = "src/test/terraform";

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        final File destination = new File(this.project.getProjectDir(), CliTaskTests.SOURCE);
        if (!destination.exists() && !destination.mkdirs()) throw new IOException("Failed to create test directory: " + destination.getAbsolutePath());

        Files.walk(new File(this.getClass().getClassLoader().getResource("terraform").toURI()).toPath())
            .forEach(path -> {
                System.out.println(path.toAbsolutePath().toString());

                try {
                    Files.copy(path, new File(destination, path.getFileName().toString()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (final IOException e) {
                    throw Throwables.propagate(e);
                }
            });

        final DownloadTask task = this.getTask(DownloadTask.TASK_NAME);
        task.toolVersion.set("0.12.26");
        task.run();
    }
}
