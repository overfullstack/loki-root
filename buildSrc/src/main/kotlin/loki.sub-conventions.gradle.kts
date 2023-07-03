plugins {
  application
  `maven-publish`
  id("org.jetbrains.kotlinx.kover")
}

java {
  withSourcesJar()
  toolchain { languageVersion.set(JavaLanguageVersion.of(11)) }
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) { useJUnitJupiter("5.9.3") }
  }
}

tasks {
  withType<PublishToMavenRepository>().configureEach {
    doLast {
      logger.lifecycle(
        "Successfully uploaded ${publication.groupId}:${publication.artifactId}:${publication.version} to ${repository.name}"
      )
    }
  }
  withType<PublishToMavenLocal>().configureEach {
    doLast {
      logger.lifecycle(
        "Successfully created ${publication.groupId}:${publication.artifactId}:${publication.version} in MavenLocal"
      )
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
