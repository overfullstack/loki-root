@file:JvmName("StringToAnyBi")

package org.revcloud.loki.sfcore.dud

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.revcloud.loki.sfcore.Utils.randomForPrimitiveType

private val stringToAnyCache: BiMap<String, Any> = HashBiMap.create()

fun <T : Any> getRight(left: String, type: Class<out T>): T =
  stringToAnyCache.computeIfAbsent(left) { randomForPrimitiveType(type) } as T

fun getLeft(right: Any): String =
  stringToAnyCache.inverse().computeIfAbsent(right) { randomForPrimitiveType(String::class.java) as String }

fun put(left: String, right: Any) {
  stringToAnyCache[left] = right
}
