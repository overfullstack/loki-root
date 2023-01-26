import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  java
  id(libs.plugins.detekt.pluginId) apply false
}
allprojects {
  apply(plugin = "loki.root-conventions")
  repositories {
    mavenCentral()
  }
}
val detektReportMerge by tasks.registering(ReportMergeTask::class) {
  output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
}
subprojects {
  apply(plugin = "loki.sub-conventions")
  apply(plugin = "loki.kt-conventions")
  tasks.withType<Detekt>().configureEach {
    reports {
      html.required by true
    }
  }
  plugins.withType<DetektPlugin> {
    tasks.withType<Detekt> detekt@{
      finalizedBy(detektReportMerge)
      detektReportMerge.configure {
        input.from(this@detekt.xmlReportFile)
      }
    }
  }
}
