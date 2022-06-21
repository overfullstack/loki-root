@file:JvmName("MultiAnyToAny")

package org.revcloud.loki.sfcore.dud

import org.mockito.Mockito
import org.revcloud.loki.sfcore.dud.Utils.randomForPrimitiveType

private val pairOfAnyToAnyCache = mutableMapOf<Pair<Any, Any>, Any?>()

// ! FIXME gopala.akshintala 05/06/22: Think about primitive types
fun <T : Any> get(key1: Any, key2: Any, valueType: Class<T>): T =
  pairOfAnyToAnyCache.computeIfAbsent(Pair(key1, key2)) { randomForPrimitiveType(valueType) } as T

fun get(key1: Any, key2: Any): Any? =
  pairOfAnyToAnyCache.computeIfAbsent(Pair(key1, key2)) { Mockito.mock(Any::class.java) }

fun put(key1: Any, key2: Any, value: Any?) {
  pairOfAnyToAnyCache[Pair(key1, key2)] = value
}
