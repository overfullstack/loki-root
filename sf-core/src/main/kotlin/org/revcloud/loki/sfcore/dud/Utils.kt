package org.revcloud.loki.sfcore.dud

import org.apache.commons.lang.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang.RandomStringUtils.randomNumeric
import org.mockito.Mockito

private const val randomStringLength = 18
private const val randomNumericLength = 3

object Utils {
  fun <T : Any> randomForPrimitiveType(type: Class<T>): Any = when (type) {
    String::class.java -> randomAlphabetic(randomStringLength)
    Int::class.javaObjectType -> randomNumeric(randomNumericLength).toInt()
    Long::class.javaObjectType -> randomNumeric(randomNumericLength).toLong()
    Double::class.javaObjectType -> randomNumeric(randomNumericLength).toDouble()
    Float::class.javaObjectType -> randomNumeric(randomNumericLength).toFloat()
    Boolean::class.javaObjectType -> true
    else -> Mockito.mock(type)
  }
}
