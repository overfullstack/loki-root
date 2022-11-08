@file:JvmName("AnyToAnyBi")

package org.revcloud.loki.sfcore.dud

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.revcloud.loki.sfcore.Utils.randomForPrimitiveType

object AnyToAnyBi {
  private val anyToAnyBiCache: BiMap<Any, Any> = HashBiMap.create()

  fun <T : Any> getRight(left: Any, type: Class<out T>): T =
    anyToAnyBiCache.computeIfAbsent(left) { randomForPrimitiveType(type) } as T

  fun <T : Any> getLeft(right: Any, type: Class<out T>): T =
    anyToAnyBiCache.inverse().computeIfAbsent(right) { randomForPrimitiveType(type) as String } as T

  fun put(left: Any, right: Any) {
    anyToAnyBiCache[left] = right
  }
}
