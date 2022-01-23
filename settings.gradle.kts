pluginManagement {
  repositories {
    gradlePluginPortal()
  }
  val kotlinVersion: String by settings
  plugins {
    kotlin("jvm") version kotlinVersion
  }
}

rootProject.name = "loki-root"
include("sf-core")
include("common")
