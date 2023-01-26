enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("../libs.versions.toml"))
    }
  }

  val nexusUsername: String by lazy {
    System.getenv("NEXUS_USERNAME") ?: settings.providers.gradleProperty("nexusUsername").get()
  }
  val nexusPassword: String by lazy {
    System.getenv("NEXUS_PASSWORD") ?: settings.providers.gradleProperty("nexusPassword").get()
  }
  val nexusBaseUrl: String by lazy {
    System.getenv("NEXUS_BASE_URL") ?: "https://nexus-proxy-prd.soma.salesforce.com/nexus/content"
  }

  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
      name = "NexusPublic"
      url = uri("$nexusBaseUrl/groups/public")
      credentials {
        username = nexusUsername
        password = nexusPassword
      }
    }
  }
}
