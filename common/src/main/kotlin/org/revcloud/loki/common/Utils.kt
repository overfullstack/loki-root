@file:JvmName("Utils")

package org.revcloud.loki.common

import com.squareup.moshi.Moshi
import java.io.File

fun <POJOT> jsonToPOJO(pojoClass: Class<POJOT>, jsonFilePath: String, vararg adapters: Any): POJOT? {
  val moshiBuilder = Moshi.Builder()
  for (adapter in adapters) {
    moshiBuilder.add(adapter)
  }
  val moshi = moshiBuilder.build()
  val adapter = moshi.adapter(pojoClass)
  return runCatching {
    adapter.fromJson(
      readFileToString(jsonFilePath)
    )
  }.getOrNull()
}

fun readFileToString(fileRelativePath: String): String {
  return File(fileRelativePath).readText(Charsets.UTF_8)
}
