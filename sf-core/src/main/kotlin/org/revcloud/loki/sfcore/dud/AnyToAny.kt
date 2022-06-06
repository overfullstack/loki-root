@file:JvmName("AnyToAny")

package org.revcloud.loki.sfcore.dud

import org.mockito.Mockito

private val anyToAnyCache = mutableMapOf<Any, Any?>()

fun <T : Any> get(key: Any, type: Class<T>): T = anyToAnyCache.computeIfAbsent(key) { Mockito.mock(type) } as T

fun put(key: Any, value: Any) {
    anyToAnyCache[key] = value
}
