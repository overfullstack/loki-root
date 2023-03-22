@file:JvmName("Utils")

package org.revcloud.loki.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.revcloud.loki.common.factory.IgnoreUnknownFactory
import java.io.File
import java.lang.reflect.Type

@JvmOverloads
fun <PojoT: Any> jsonToPojo(
  pojoType: Type,
  jsonFilePath: String,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): PojoT? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return runCatching { jsonAdapter.fromJson(readFileToString(jsonFilePath)) }.getOrNull()
}

@JvmOverloads
fun <PojoT: Any> pojoToJson(
  pojoType: Type,
  pojo: PojoT,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): String? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return runCatching { jsonAdapter.indent("  ").toJson(pojo) }.getOrNull()
}

private fun <PojoT: Any> initMoshiJsonAdapter(
  customAdapters: List<Any>,
  typesToIgnore: Set<Class<out Any>>?,
  pojoType: Type
): JsonAdapter<PojoT> {
  val moshiBuilder = Moshi.Builder()
  for (adapter in customAdapters) {
    moshiBuilder.add(adapter)
  }
  if (!typesToIgnore.isNullOrEmpty()) {
    moshiBuilder.add(IgnoreUnknownFactory(typesToIgnore))
  }
  return moshiBuilder.build().adapter(pojoType)
}


fun readFileToString(fileRelativePath: String): String {
  return File(fileRelativePath).readText(Charsets.UTF_8)
}
