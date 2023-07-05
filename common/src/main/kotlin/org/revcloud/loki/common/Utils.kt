@file:JvmName("Utils")

package org.revcloud.loki.common

import java.io.File

fun readFileToString(fileRelativePath: String): String {
  return File(fileRelativePath).readText(Charsets.UTF_8)
}
