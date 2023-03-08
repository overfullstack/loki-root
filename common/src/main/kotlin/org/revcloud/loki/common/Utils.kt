@file:JvmName("Utils")

package org.revcloud.loki.common

import com.squareup.moshi.Moshi
import org.revcloud.loki.common.factory.IgnoreUnknownFactory
import java.io.File

@JvmOverloads
fun <PojoT> jsonToPojo(
  pojoClass: Class<PojoT>,
  jsonFilePath: String,
  customAdapters: List<Any>,
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): PojoT? {
  val moshiBuilder = Moshi.Builder()
  for (adapter in customAdapters) {
    moshiBuilder.add(adapter)
  }
  if (!typesToIgnore.isNullOrEmpty()) {
    moshiBuilder.add(IgnoreUnknownFactory(typesToIgnore))
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
