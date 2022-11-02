@file:JvmName("AnyToAny")

package org.revcloud.loki.sfcore.dud

import org.revcloud.loki.sfcore.Utils.randomForPrimitiveType

private val anyToAnyCache = mutableMapOf<Any, Any?>()

fun <T : Any> get(key: Any, valueType: Class<T>): T =
  anyToAnyCache.computeIfAbsent(key) { randomForPrimitiveType(valueType) } as T

fun put(key: Any, value: Any) {
  anyToAnyCache[key] = value
}
