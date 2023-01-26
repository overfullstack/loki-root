@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.moshix)
}
dependencies {
  api(libs.moshix.adapters)
}
