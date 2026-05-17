import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    jvmToolchain(21)

    androidLibrary {
        namespace = "com.hrm.latex.base"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {}
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "LatexBase"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            // 基础层保持最小依赖
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(true)

    signAllPublications()

    coordinates("io.github.zly2006", "latex-base", rootProject.property("VERSION").toString())

    pom {
        name.set("Kotlin Multiplatform LaTeX Rendering Engine")
        description.set("""
            Cross-platform LaTeX math rendering solution with:
            - Full LaTeX syntax support (math mode)
            - Custom command definitions
            - Chemical formula rendering
            - Compose Multiplatform UI integration
            - Multi-module architecture (base/parser/renderer)
        """.trimIndent())
        inceptionYear.set("2026")
        url.set("https://github.com/zly2006/latex")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("zly2006")
                name.set("zly2006")
                url.set("https://github.com/zly2006/")
            }
        }
        scm {
            url.set("https://github.com/zly2006/latex")
            connection.set("scm:git:git://github.com/zly2006/latex.git")
            developerConnection.set("scm:git:ssh://git@github.com/zly2006/latex.git")
        }
    }
}