# Terraform Gradle plugin

[![GitHub workflow status](https://img.shields.io/github/workflow/status/ayltai/terraform-plugin/CI?style=flat)](https://github.com/ayltai/terraform-plugin/actions)
[![Codacy grade](https://img.shields.io/codacy/grade/00d0e2c6a6a64021816b3f668afbb9bb.svg?style=flat)](https://app.codacy.com/app/AlanTai/terraform-plugin/dashboard)
[![Sonar quality gate](https://img.shields.io/sonar/quality_gate/ayltai_terraform-plugin?style=flat&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Sonar violations (short format)](https://img.shields.io/sonar/violations/ayltai_terraform-plugin?style=flat&format=short&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Sonar Test Success Rate](https://img.shields.io/sonar/test_success_density/ayltai_terraform-plugin?style=flat&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Code Coverage](https://img.shields.io/codecov/c/github/ayltai/terraform-plugin.svg?style=flat)](https://codecov.io/gh/ayltai/terraform-plugin)
[![Sonar Coverage](https://img.shields.io/sonar/coverage/ayltai_terraform-plugin?style=flat&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ayltai_terraform-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ayltai_terraform-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Sonar Tech Debt](https://img.shields.io/sonar/tech_debt/ayltai_terraform-plugin?style=flat&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ayltai_terraform-plugin&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=ayltai_terraform-plugin)
![Maintenance](https://img.shields.io/maintenance/yes/2020?style=flat)
[![Release](https://img.shields.io/github/release/ayltai/terraform-plugin.svg?style=flat)](https://github.com/ayltai/terraform-plugin/releases)
[![License](https://img.shields.io/github/license/ayltai/terraform-plugin.svg?style=flat)](https://github.com/ayltai/terraform-plugin/blob/master/LICENSE)

Supports for executing [Terraform](https://www.terraform.io) [command-line tools](https://www.terraform.io/docs/commands/index.html).

[https://plugins.gradle.org/plugin/com.github.ayltai.terraform-plugin](https://plugins.gradle.org/plugin/com.github.ayltai.terraform-plugin)

[![Buy me a coffee](https://img.shields.io/static/v1?label=Buy%20me%20a&message=coffee&color=important&style=flat&logo=buy-me-a-coffee&logoColor=white)](https://buymeacoff.ee/ayltai)

## Quick start

### Apply Gradle plugin

#### Groovy
Using the [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block):
```groovy
plugins {
    id 'com.github.ayltai.terraform-plugin' version '0.1.0'
}
```

Using [legacy plugin application](https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application):
```groovy
buildscript {
    repositories {
        maven {
            url 'https://plugins.gradle.org/m2/'
        }
    }

    dependencies {
        classpath 'gradle.plugin.com.github.ayltai:terraform-plugin:0.1.0'
    }
}

apply plugin: 'com.github.ayltai.terraform-plugin'
```

#### Kotlin
Using the [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block):
```groovy
plugins {
    id('com.github.ayltai.terraform-plugin') version '0.1.0'
}
```

Using [legacy plugin application](https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application):
```groovy
buildscript {
    repositories {
        maven {
            url = uri('https://plugins.gradle.org/m2/')
        }
    }

    dependencies {
        classpath('gradle.plugin.com.github.ayltai:terraform-plugin:0.1.0')
    }
}
```

### Specify options

## Configurations

### General
| Property        | Type           | Description |
|-----------------|----------------|-------------|
| `backend`       | `String`       | The endpoint of remote state |
| `apiToken`      | `String`       | The API token to authenticate with the remote state endpoint |
| `toolVersion`   | `String`       | The version of the Terraform command-line tools to be downloaded |
| `forceDownload` | `boolean`      | Terraform command-line tools will be re-downloaded even if they are already downloaded |
| `source`        | `String`       | The path to the Terraform source set |
| `configFile`    | `java.io.File` | The path to the Terraform configuration file |
| `init`          |                | See [init](#init) |
| `fmt`           |                | See [fmt](#fmt) |
| `validate`      |                | See [validate](#validate) |
| `plan`          |                | See [plan](#plan) |
| `apply`         |                | See [apply](#apply) |
| `destroy`       |                | See [destroy](#destroy) |
| `variables`     |                | See [variables](#variables) |

### init
| Property      | Type      | Description |
|---------------|-----------|-------------|
| `noColor`     | `boolean` | Disables color codes in the command output. |
| `input`       | `boolean` | Asks for input if necessary |
| `lock`        | `boolean` | Disables locking of state files during state-related operations
| `lockTimeout` | `int`     | Overrides the time Terraform will wait to acquire a state lock
| `useBackend`  | `boolean` | Initializes a working directory for validation without accessing any configured remote backend |
| `upgrade`     | `boolean` | Opts to upgrade modules and plugins as part of their respective installation steps |

### fmt
| Property    | Type      | Description |
|-------------|-----------|-------------|
| `noColor`   | `boolean` | Disables color codes in the command output. |
| `list`      | `boolean` | Lists the files containing formatting inconsistencies |
| `write`     | `boolean` | Overwrites the input files |
| `diff`      | `boolean` | Displays diffs of formatting changes |
| `check`     | `boolean` | Checks if the input is formatted |
| `recursive` | `boolean` | Processes files in subdirectories |

### validate
| Property  | Type      | Description |
|-----------|-----------|-------------|
| `noColor` | `boolean` | Disables color codes in the command output. |
| `json`    | `boolean` | Produces output in a machine-readble JSON format, suitable for use in text editor integrations and other automated systems |

### plan
| Property           | Type             | Description |
|--------------------|------------------|-------------|
| `noColor`          | `boolean`        | Disables color codes in the command output. |
| `input`            | `boolean`        | Asks for input if necessary |
| `lock`             | `boolean`        | Disables locking of state files during state-related operations
| `lockTimeout`      | `int`            | Overrides the time Terraform will wait to acquire a state lock
| `backup`           | `String`         | The path to backup the existing state file |
| `compactWarnings`  | `boolean`        | If the warnings Terraform produces are not accompanied by errors, shows warning in a more compact form that includes only the summary messages |
| `parallelism`      | `int`            | Limits the number of concurrent operation as Terraform [walks the graph](https://www.terraform.io/docs/internals/graph.html#walking-the-graph) |
| `state`            | `String`         | The path to the state file |
| `targets`          | `java.util.List` | [Resource Addresses](https://www.terraform.io/docs/internals/resource-addressing.html) to target |
| `destroy`          | `boolean`        | Generates a plan to destroy all the known resources |
| `detailedExitCode` | `boolean`        | Returns a detailed exit code when the command exits |
| `out`              | `String`         | The path to save the generated execution plan |
| `refresh`          | `boolean`        | Updates the state prior to checking for differences |

### apply
| Property          | Type             | Description |
|-------------------|------------------|-------------|
| `noColor`         | `boolean`        | Disables color codes in the command output. |
| `input`           | `boolean`        | Asks for input if necessary |
| `lock`            | `boolean`        | Disables locking of state files during state-related operations
| `lockTimeout`     | `int`            | Overrides the time Terraform will wait to acquire a state lock
| `backup`          | `String`         | The path to backup the existing state file |
| `compactWarnings` | `boolean`        | If the warnings Terraform produces are not accompanied by errors, shows warning in a more compact form that includes only the summary messages |
| `parallelism`     | `int`            | Limits the number of concurrent operation as Terraform [walks the graph](https://www.terraform.io/docs/internals/graph.html#walking-the-graph) |
| `state`           | `String`         | The path to the state file |
| `targets`         | `java.util.List` | [Resource Addresses](https://www.terraform.io/docs/internals/resource-addressing.html) to target |
| `refresh`         | `boolean`        | Updates the state prior to checking for differences |
| `stateOut`        | `String`         | The path to write updated state file |
| `in`              | `String`         | The path to the Terraform source scripts or an execution plan to apply |

### destroy
| Property          | Type             | Description |
|-------------------|------------------|-------------|
| `noColor`         | `boolean`        | Disables color codes in the command output. |
| `input`           | `boolean`        | Asks for input if necessary |
| `lock`            | `boolean`        | Disables locking of state files during state-related operations
| `lockTimeout`     | `int`            | Overrides the time Terraform will wait to acquire a state lock
| `backup`          | `String`         | The path to backup the existing state file |
| `compactWarnings` | `boolean`        | If the warnings Terraform produces are not accompanied by errors, shows warning in a more compact form that includes only the summary messages |
| `parallelism`     | `int`            | Limits the number of concurrent operation as Terraform [walks the graph](https://www.terraform.io/docs/internals/graph.html#walking-the-graph) |
| `state`           | `String`         | The path to the state file |
| `targets`         | `java.util.List` | [Resource Addresses](https://www.terraform.io/docs/internals/resource-addressing.html) to target |
| `refresh`         | `boolean`        | Updates the state prior to checking for differences |
| `stateOut`        | `String`         | The path to write updated state file |
| `in`              | `String`         | The path to the Terraform source scripts to destroy |

### variables
Example:
```groovy
variables {
    var 'foo', 'bar'
    var 'name', 'value'
    file 'vars-file.tf'
}
```

See the [official documentation](https://www.terraform.io/docs/commands/index.html) for more details.

## License
[MIT](https://github.com/ayltai/terraform-plugin/blob/master/LICENSE)

## References
* [Terraform](https://www.terraform.io)
* [Terraform command-line tools](https://www.terraform.io/docs/commands/index.html)
