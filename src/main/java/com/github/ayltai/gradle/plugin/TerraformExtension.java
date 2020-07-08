package com.github.ayltai.gradle.plugin;

import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;

import groovy.lang.Closure;

public class TerraformExtension {
    //region Constants

    private static final String EXTENSION_INIT      = "init";
    private static final String EXTENSION_FMT       = "fmt";
    private static final String EXTENSION_VALIDATE  = "validate";
    private static final String EXTENSION_PLAN      = "plan";
    private static final String EXTENSION_APPLY     = "apply";
    private static final String EXTENSION_DESTROY   = "destroy";
    private static final String EXTENSION_VARIABLES = "variables";

    private static final String DEFAULT_SOURCE    = "src/main/terraform";
    private static final String DEFAULT_WORKSPACE = "default";

    //endregion

    //region Gradle plugin properties

    protected final Property<String>    backend;
    protected final Property<String>    apiToken;
    protected final Property<String>    toolVersion;
    protected final Property<Boolean>   forceDownload;
    protected final Property<String>    source;
    protected final Property<String>    workspace;
    protected final RegularFileProperty configFile;
    protected final InitOptions         initOptions;
    protected final FmtOptions          fmtOptions;
    protected final ValidateOptions     validateOptions;
    protected final PlanOptions         planOptions;
    protected final ApplyOptions        applyOptions;
    protected final ApplyOptions        destroyOptions;
    protected final Variables           variables;

    //endregion

    @Inject
    public TerraformExtension(@Nonnull final ObjectFactory factory) {
        this.backend         = factory.property(String.class);
        this.apiToken        = factory.property(String.class);
        this.toolVersion     = factory.property(String.class);
        this.forceDownload   = factory.property(Boolean.class);
        this.source          = factory.property(String.class);
        this.workspace       = factory.property(String.class);
        this.configFile      = factory.fileProperty();
        this.initOptions     = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_INIT, InitOptions.class, TerraformExtension.EXTENSION_INIT);
        this.fmtOptions      = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_FMT, FmtOptions.class, TerraformExtension.EXTENSION_FMT);
        this.validateOptions = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_VALIDATE, ValidateOptions.class, TerraformExtension.EXTENSION_VALIDATE);
        this.planOptions     = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_PLAN, PlanOptions.class, TerraformExtension.EXTENSION_PLAN);
        this.applyOptions    = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_APPLY, ApplyOptions.class, TerraformExtension.EXTENSION_APPLY);
        this.destroyOptions  = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_DESTROY, ApplyOptions.class, TerraformExtension.EXTENSION_DESTROY);
        this.variables       = ((ExtensionAware)this).getExtensions().create(TerraformExtension.EXTENSION_VARIABLES, Variables.class, TerraformExtension.EXTENSION_VARIABLES);
    }

    //region Properties

    @Nullable
    public String getBackend() {
        return this.backend.getOrNull();
    }

    public void setBackend(@Nullable final String backend) {
        this.backend.set(backend);
    }

    @Nullable
    public String getApiToken() {
        return this.apiToken.getOrNull();
    }

    public void setApiToken(@Nullable final String apiToken) {
        this.apiToken.set(apiToken);
    }

    @Nullable
    public String getToolVersion() {
        return this.toolVersion.getOrNull();
    }

    public void setToolVersion(@Nonnull final String toolVersion) {
        this.toolVersion.set(toolVersion);
    }

    public boolean getForceDownload() {
        return this.forceDownload.getOrElse(false);
    }

    public void setForceDownload(final boolean forceDownload) {
        this.forceDownload.set(forceDownload);
    }

    @Nonnull
    public String getSource() {
        return this.source.getOrElse(TerraformExtension.DEFAULT_SOURCE);
    }

    public void setSource(@Nonnull final String source) {
        this.source.set(source);
    }

    @Nullable
    public String getWorkspace() {
        return this.workspace.getOrElse(TerraformExtension.DEFAULT_WORKSPACE);
    }

    public void setWorkspace(@Nullable final String workspace) {
        this.workspace.set(workspace);
    }

    @Nullable
    public File getConfigFile() {
        return this.configFile.getAsFile().getOrNull();
    }

    public void setConfigFile(@Nullable final File configFile) {
        this.configFile.set(configFile);
    }

    public void init(@Nonnull final Closure<InitOptions> closure) {
        closure.setDelegate(this.initOptions);
        closure.call();
    }

    public void fmt(@Nonnull final Closure<InitOptions> closure) {
        closure.setDelegate(this.fmtOptions);
        closure.call();
    }

    public void plan(@Nonnull final Closure<PlanOptions> closure) {
        closure.setDelegate(this.planOptions);
        closure.call();
    }

    public void apply(@Nonnull final Closure<ApplyOptions> closure) {
        closure.setDelegate(this.applyOptions);
        closure.call();
    }

    public void destroy(@Nonnull final Closure<ApplyOptions> closure) {
        closure.setDelegate(this.destroyOptions);
        closure.call();
    }

    public void validate(@Nonnull final Closure<InitOptions> closure) {
        closure.setDelegate(this.validateOptions);
        closure.call();
    }

    public void variables(@Nonnull final Closure<Variables> closure) {
        closure.setDelegate(this.variables);
        closure.call();
    }

    //endregion
}
