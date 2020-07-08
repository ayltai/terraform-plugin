package com.github.ayltai.gradle.plugin;

import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import groovy.lang.Closure;

public class TerraformExtension {
    //region Constants

    private static final String DEFAULT_SOURCE    = "src/main/terraform";
    private static final String DEFAULT_WORKSPACE = "default";

    //endregion

    //region Gradle plugin properties

    protected final Property<String>                            backend;
    protected final Property<String>                            apiToken;
    protected final Property<String>                            toolVersion;
    protected final Property<Boolean>                           forceDownload;
    protected final Property<String>                            source;
    protected final Property<String>                            workspace;
    protected final RegularFileProperty                         configFile;
    protected final NamedDomainObjectContainer<InitOptions>     initOptions;
    protected final NamedDomainObjectContainer<FmtOptions>      fmtOptions;
    protected final NamedDomainObjectContainer<ValidateOptions> validateOptions;
    protected final NamedDomainObjectContainer<PlanOptions>     planOptions;
    protected final NamedDomainObjectContainer<ApplyOptions>    applyOptions;
    protected final NamedDomainObjectContainer<ApplyOptions>    destroyOptions;
    protected final NamedDomainObjectContainer<Variables>       variables;

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
        this.initOptions     = factory.domainObjectContainer(InitOptions.class);
        this.fmtOptions      = factory.domainObjectContainer(FmtOptions.class);
        this.validateOptions = factory.domainObjectContainer(ValidateOptions.class);
        this.planOptions     = factory.domainObjectContainer(PlanOptions.class);
        this.applyOptions    = factory.domainObjectContainer(ApplyOptions.class);
        this.destroyOptions  = factory.domainObjectContainer(ApplyOptions.class);
        this.variables       = factory.domainObjectContainer(Variables.class);
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
        this.initOptions.configure(closure);
    }

    public void fmt(@Nonnull final Closure<InitOptions> closure) {
        this.fmtOptions.configure(closure);
    }

    public void plan(@Nonnull final Closure<PlanOptions> closure) {
        this.planOptions.configure(closure);
    }

    public void apply(@Nonnull final Closure<ApplyOptions> closure) {
        this.applyOptions.configure(closure);
    }

    public void destroy(@Nonnull final Closure<ApplyOptions> closure) {
        this.destroyOptions.configure(closure);
    }

    public void validate(@Nonnull final Closure<InitOptions> closure) {
        this.validateOptions.configure(closure);
    }

    public void variables(@Nonnull final Closure<Variables> closure) {
        this.variables.configure(closure);
    }

    //endregion
}
