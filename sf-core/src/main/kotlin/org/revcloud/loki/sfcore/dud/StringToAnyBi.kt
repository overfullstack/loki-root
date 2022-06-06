@file:JvmName("StringToAnyBi")

package org.revcloud.loki.sfcore.dud

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.apache.commons.lang3.RandomStringUtils
import org.mockito.Mockito

private val stringToAnyCache: BiMap<String, Any> = HashBiMap.create()

fun <T : Any> getRight(left: String, clazz: Class<out T>): T = stringToAnyCache.computeIfAbsent(left) { Mockito.mock(clazz) } as T

fun getLeft(right: Any): String = stringToAnyCache.inverse().computeIfAbsent(right) { RandomStringUtils.random(18, true, true) }

fun put(left: String, right: Any) {
  stringToAnyCache[left] = right
}
