import com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML
import io.gitlab.arturbosch.detekt.Detekt

plugins {
  kotlin("jvm")
  `maven-publish`
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
  id("com.adarshr.test-logger") version "3.1.0"
  id("com.diffplug.spotless") version "6.0.1"
}

allprojects {
  group = "com.salesforce.ccspayments"
  version = "0.2-SNAPSHOT"
  repositories {
    mavenCentral()
  }
  apply(plugin = "com.diffplug.spotless")
  spotless {
    kotlin {
      target("src/main/java/**/*.kt", "src/test/java/**/*.kt")
      targetExclude("$buildDir/generated/**/*.*")
      ktlint().userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
    kotlinGradle {
      target("*.gradle.kts")
      ktlint().userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
    java {
      target("src/main/java/**/*.java", "src/test/java/**/*.java")
      targetExclude("$buildDir/generated/**/*.*")
      importOrder()
      removeUnusedImports()
      googleJavaFormat()
      trimTrailingWhitespace()
      indentWithSpaces(2)
      endWithNewline()
    }
    format("xml") {
      targetExclude("pom.xml")
      target("*.xml")
      eclipseWtp(XML)
    }
    format("documentation") {
      target("*.md", "*.adoc")
      trimTrailingWhitespace()
      indentWithSpaces(2)
      endWithNewline()
    }
  }
}

subprojects {
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "maven-publish")
  apply(plugin = "com.adarshr.test-logger")

  dependencies {
    val implementation by configurations
    implementation(platform("org.springframework:spring-framework-bom:5.3.13"))
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-test")
    implementation(platform("io.arrow-kt:arrow-stack:1.0.1"))
    implementation("io.arrow-kt:arrow-core")
    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation("org.jetbrains:annotations:22.0.0")
    val testImplementation by configurations
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    val kotestVersion = "5.0.1"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
  }

  tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
      }
    }
    test.get().useJUnitPlatform()
    testlogger.theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
    withType<PublishToMavenRepository>().configureEach {
      doLast {
        logger.lifecycle("Successfully uploaded ${publication.groupId}:${publication.artifactId}:${publication.version} to ${repository.name}")
      }
    }
    withType<PublishToMavenLocal>().configureEach {
      doLast {
        logger.lifecycle("Successfully uploaded ${publication.groupId}:${publication.artifactId}:${publication.version} to MavenLocal.")
      }
    }
  }
  publishing {
    publications.create<MavenPublication>("mavenJava") {
      val subprojectJarName = tasks.jar.get().archiveBaseName.get()
      artifactId = if (subprojectJarName == "loki") "loki" else "loki-$subprojectJarName"
      from(components["java"])
      pom {
        name.set(artifactId)
        description.set(project.description)
        url.set("https://git.soma.salesforce.com/CCSPayments/Loki")
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("gopala.akshintala@salesforce.com")
            name.set("Gopal S Akshintala")
            email.set("gopala.akshintala@salesforce.com")
          }
        }
        scm {
          connection.set("scm:git:https://git.soma.salesforce.com/ccspayments/Loki")
          developerConnection.set("scm:git:git@git.soma.salesforce.com:ccspayments/Loki.git")
          url.set("https://git.soma.salesforce.com/ccspayments/loki")
        }
      }
    }
    repositories {
      maven {
        name = "Nexus"
        val releasesRepoUrl =
          uri("https://nexus.soma.salesforce.com/nexus/content/repositories/releases")
        val snapshotsRepoUrl =
          uri("https://nexus.soma.salesforce.com/nexus/content/repositories/snapshots")
        url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        val nexusUsername: String by project
        val nexusPassword: String by project
        credentials {
          username = nexusUsername
          password = nexusPassword
        }
      }
    }
  }
  tasks {
    register<Detekt>("detektAll") {
      parallel = true
      ignoreFailures = false
      autoCorrect = false
      buildUponDefaultConfig = true
      basePath = projectDir.toString()
      setSource(subprojects.map { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
      include("**/*.kt")
      include("**/*.kts")
      exclude("**/resources/**")
      exclude("**/build/**")
      config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
      baseline.set(File("$rootDir/config/baseline.xml"))
    }
    withType<Detekt>().configureEach {
      reports {
        xml.required.set(true)
      }
    }
  }
  afterEvaluate {
    tasks {
      check.configure {
        dependsOn(named("detektAll"))
      }
    }
  }
}
