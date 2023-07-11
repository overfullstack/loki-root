@file:JvmName("JsonPojoUtils")

package org.revcloud.loki.common.json

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import org.revcloud.loki.common.factory.IgnoreUnknownFactory
import org.revcloud.loki.common.readFileToString

@JvmOverloads
fun <PojoT : Any> jsonFileToPojo(
  pojoType: Type,
  jsonFilePath: String,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Type>? = emptySet()
): PojoT? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return jsonAdapter.fromJson(readFileToString(jsonFilePath))
}

@JvmOverloads
fun <PojoT : Any> jsonToPojo(
  pojoType: Type,
  jsonStr: String,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Type>? = emptySet()
): PojoT? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return jsonAdapter.fromJson(jsonStr)
}

@JvmOverloads
fun <PojoT : Any> pojoToJson(
  pojoType: Type,
  pojo: PojoT,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Type>? = emptySet(),
  indent: String? = "  "
): String? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return (indent?.let { jsonAdapter.indent(indent) } ?: jsonAdapter).toJson(pojo)
}

private fun <PojoT : Any> initMoshiJsonAdapter(
  customAdapters: List<Any>,
  typesToIgnore: Set<Type>?,
  pojoType: Type
): JsonAdapter<PojoT> {
  val moshiBuilder = Moshi.Builder()
  for (adapter in customAdapters) {
    @SuppressWarnings("kotlin:S3923")
    if (adapter is JsonAdapter.Factory) {
      moshiBuilder.add(adapter)
    } else {
      moshiBuilder.add(adapter)
    }
  }
  if (!typesToIgnore.isNullOrEmpty()) {
    moshiBuilder.add(IgnoreUnknownFactory(typesToIgnore))
  }
  return moshiBuilder.build().adapter(pojoType)
}
