package com.github.ayltai.gradle.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.Property;
import org.gradle.api.resources.ResourceException;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;
import org.gradle.internal.os.OperatingSystem;

import org.slf4j.LoggerFactory;

@CacheableTask
public class DownloadTask extends DefaultTask {
    //region Constants

    static final String TASK_NAME = "tfDownload";

    private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(DownloadTask.class);

    private static final String DOWNLOAD_URL        = "https://releases.hashicorp.com/terraform/%1$s/%2$s";
    private static final String COMPRESSED_FILENAME = "terraform_%1$s_%2$s_%3$s.zip";
    private static final String AMD64               = "amd64";
    private static final String ARM                 = "arm";

    private static final int BUFFER_SIZE = 8192;

    //endregion

    //region Properties

    protected final Property<String>  toolVersion;
    protected final Property<Boolean> forceDownload;

    //endregion

    public DownloadTask() {
        this.toolVersion   = this.getProject().getObjects().property(String.class);
        this.forceDownload = this.getProject().getObjects().property(Boolean.class);

        this.setGroup(Constants.GROUP_BUILD_SETUP);
        this.setDescription("Download and cache a copy of Terraform CLI of the specified version if it is not already downloaded");
    }

    //region Task inputs and outputs

    @Nonnull
    @Input
    public Property<String> getToolVersion() {
        return this.toolVersion;
    }

    @Nonnull
    @Optional
    @Input
    public Property<Boolean> getForceDownload() {
        return this.forceDownload;
    }

    @Nonnull
    @OutputDirectory
    public File getOutputDirectory() {
        return Paths.get(this.getProject().getBuildDir().getAbsolutePath(), Constants.TERRAFORM, this.toolVersion.get()).toFile();
    }

    //endregion

    //region Getters

    @Nonnull
    @Internal
    protected String getPlatform() {
        if (OperatingSystem.current().isMacOsX()) return OperatingSystem.current().getNativePrefix();

        if (OperatingSystem.current().isLinux()) return "linux";

        if (OperatingSystem.current().isWindows()) return "windows";

        if (OperatingSystem.current().isUnix()) {
            if (OperatingSystem.current().equals(OperatingSystem.SOLARIS)) return OperatingSystem.current().getFamilyName();
            if (OperatingSystem.current().equals(OperatingSystem.FREE_BSD)) return "freebsd";

            return "openbsd";
        }

        if (DownloadTask.LOGGER.isEnabled(LogLevel.WARN)) DownloadTask.LOGGER.warn(String.format(Locale.US, "Failed to determine the type of OS: %s", OperatingSystem.current().getFamilyName()));

        return "linux";
    }

    @Nonnull
    @Internal
    protected String getArchitecture() {
        if (OperatingSystem.current().isUnix() && (OperatingSystem.current().isMacOsX() || OperatingSystem.current().equals(OperatingSystem.SOLARIS))) return DownloadTask.AMD64;

        final String architecture = System.getProperty("os.arch");

        if ("i386".equals(architecture) || "x86".equals(architecture)) return "386";
        if ("x86_64".equals(architecture)) return DownloadTask.AMD64;
        if (architecture.startsWith(DownloadTask.ARM)) return DownloadTask.ARM;

        return DownloadTask.AMD64;
    }

    //endregion

    @TaskAction
    public void run() {
        final File compressedFile = this.download();

        try {
            if (compressedFile != null) this.explodeZip(compressedFile, this.getOutputDirectory());

            final File file = new File(this.getOutputDirectory(), Constants.TERRAFORM);

            if (!file.setExecutable(true)) DownloadTask.LOGGER.warn("Failed to change executable permission: " + file.getAbsolutePath());
        } catch (final IOException e) {
            throw new ResourceException(e.getMessage(), e);
        }
    }

    @Nullable
    protected File download() {
        final File outputDir = this.getOutputDirectory();
        if (!outputDir.exists() && !outputDir.mkdirs()) throw new ResourceException("Failed to create output directory: " + outputDir.getAbsolutePath());

        final String compressedFilename = String.format(Locale.US, DownloadTask.COMPRESSED_FILENAME, this.toolVersion.get(), this.getPlatform(), this.getArchitecture());
        final String downloadUrl        = String.format(Locale.US, DownloadTask.DOWNLOAD_URL, this.toolVersion.get(), compressedFilename);
        final File   compressedFile     = new File(outputDir, compressedFilename);
        final File   decompressedFile   = new File(outputDir, Constants.TERRAFORM);

        if (decompressedFile.exists() && !this.forceDownload.getOrElse(false)) {
            DownloadTask.LOGGER.info("Skip downloading Terraform CLI as it is already downloaded");

            return null;
        }

        if (DownloadTask.LOGGER.isEnabled(LogLevel.WARN)) DownloadTask.LOGGER.info(String.format(Locale.US, "Download Terraform CLI from %s", downloadUrl));

        try (
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(downloadUrl).openStream());
            FileOutputStream    outputStream        = new FileOutputStream(compressedFile);
            FileChannel         fileChannel         = outputStream.getChannel()) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (final MalformedURLException e) {
            throw new TaskExecutionException(this, e);
        } catch (final FileNotFoundException e) {
            throw new ResourceException("Failed to create output directory: " + outputDir.getAbsolutePath(), e);
        } catch (final IOException e) {
            throw new ResourceException("Failed to download Terraform CLI", e);
        }

        return compressedFile;
    }

    protected void explodeZip(@Nonnull final File archive, @Nonnull final File outputDir) throws IOException {
        DownloadTask.LOGGER.info("Decompress Terraform CLI archive");

        try (JarFile jarFile = new JarFile(archive)) {
            jarFile.stream()
                .sorted((entry1, entry2) -> (entry1.isDirectory() ? -1 : 0) + (entry2.isDirectory() ? 1 : 0))
                .forEachOrdered(entry -> {
                    try {
                        this.explodeZipEntry(outputDir, jarFile, entry);
                    } catch (final IOException e) {
                        throw new ResourceException("Failed to decompress JAR entry: " + entry.getName(), e);
                    }
                });
        }
    }

    protected void explodeZipEntry(@Nonnull final File outputDir, @Nonnull final JarFile jarFile, @Nonnull final ZipEntry entry) throws IOException {
        final File file = new File(outputDir, entry.getName());

        if (entry.isDirectory()) {
            if (!file.mkdirs()) throw new IOException("Failed to create folder(s): " + file.getAbsolutePath());
        } else {
            final byte[] buffer = new byte[DownloadTask.BUFFER_SIZE];

            try (
                InputStream inputStream   = new BufferedInputStream(jarFile.getInputStream(entry));
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                int length;
                while ((length = inputStream.read(buffer)) >= 0) outputStream.write(buffer, 0, length);
            }
        }
    }
}
