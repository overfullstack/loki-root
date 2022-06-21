@file:JvmName("AnyToString")

package org.revcloud.loki.sfcore.dud

import org.revcloud.loki.sfcore.dud.Utils.randomForPrimitiveType

private val anyToStringCache = mutableMapOf<Any, String?>()

fun get(key: Any): String? =
  anyToStringCache.computeIfAbsent(key) { randomForPrimitiveType(String::class.java) as String? } 
