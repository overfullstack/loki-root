@file:JvmName("AnyToString")

package org.revcloud.loki.sfcore.dud

import org.apache.commons.lang3.RandomStringUtils

private val anyToStringCache = mutableMapOf<Any, String?>()

fun get(key: Any): String? = anyToStringCache.computeIfAbsent(key) { RandomStringUtils.randomAlphabetic(5) }
