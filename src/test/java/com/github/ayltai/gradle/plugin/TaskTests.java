package com.github.ayltai.gradle.plugin;

import javax.annotation.Nonnull;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;

public abstract class TaskTests {
    private static final String PROJECT_NAME = "sample";

    protected Project project;

    @BeforeEach
    public void setUp() throws Exception {
        final TemporaryFolder temporaryFolder = TemporaryFolder.builder().assureDeletion().build();
        temporaryFolder.create();

        this.project = ProjectBuilder.builder()
            .withName(TaskTests.PROJECT_NAME)
            .withProjectDir(temporaryFolder.newFolder())
            .build();

        this.project.getPluginManager().apply("com.github.ayltai.terraform-plugin");
    }

    @Nonnull
    protected <T extends Task> T getTask(@Nonnull final String name) {
        return (T)this.project.getTasks().getByName(name);
    }
}
