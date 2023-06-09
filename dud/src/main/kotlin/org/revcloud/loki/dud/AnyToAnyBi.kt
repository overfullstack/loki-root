@file:JvmName("AnyToAnyBi")

package org.revcloud.loki.dud

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.revcloud.loki.Utils.randomForPrimitiveType

object AnyToAnyBi {
  private val anyToAnyBiCache: BiMap<Any, Any> = HashBiMap.create()

  @JvmStatic
  fun <T : Any> getRight(left: Any, type: Class<out T>): T =
    anyToAnyBiCache.computeIfAbsent(left) { randomForPrimitiveType(type) } as T

  @JvmStatic
  fun <T : Any> getLeft(right: Any, type: Class<out T>): T =
    anyToAnyBiCache.inverse().computeIfAbsent(right) { randomForPrimitiveType(type) as String } as T

  @JvmStatic
  fun put(left: Any, right: Any) {
    anyToAnyBiCache[left] = right
  }
}
