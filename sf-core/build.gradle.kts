@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.moshix)
}
val nexusUsername: String by lazy {
  System.getenv("NEXUS_USERNAME") ?: providers.gradleProperty("nexusUsername").get()
}
val nexusPassword: String by lazy {
  System.getenv("NEXUS_PASSWORD") ?: providers.gradleProperty("nexusPassword").get()
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
dependencies {
  implementation("com.force.api:swag:0.4.13")
  implementation(project(":common"))
  api(libs.moshix.adapters)
}
