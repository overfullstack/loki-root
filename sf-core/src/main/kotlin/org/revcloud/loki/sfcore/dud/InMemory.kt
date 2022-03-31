package org.revcloud.loki.sfcore.dud

import arrow.core.memoize
import com.google.common.collect.HashBiMap
import org.apache.commons.lang3.RandomStringUtils.random
import org.mockito.Mockito


private val idToEntityObj: HashBiMap<String, Any> = HashBiMap.create()

@JvmField
val entityObjIdRandomGenerator: (Any) -> String = { id: Any ->
    idToEntityObj.inverse().computeIfAbsent(id) { random(18, true, true) }
}.memoize()
