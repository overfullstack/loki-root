@file:JvmName("JsonReaderUtils")

package org.revcloud.loki.common.json

import com.squareup.moshi.JsonReader
import java.util.function.BiConsumer

fun nextString(reader: JsonReader): String = reader.nextString()

fun nextBoolean(reader: JsonReader): Boolean = reader.nextBoolean()

fun nextInt(reader: JsonReader): Int = reader.nextInt()

fun nextLong(reader: JsonReader): Long = reader.nextLong()

fun skipValue(reader: JsonReader) = reader.skipValue()

fun nextName(reader: JsonReader): String = reader.nextName()

fun <T> obj(mk: () -> T, reader: JsonReader, block: BiConsumer<T, String>): T = with(reader) {
  beginObject()
  val item = mk()
  while (hasNext()) {
    block.accept(item, nextName())
  }
  endObject()
  item
}

fun <T> list(mk: () -> T, reader: JsonReader, block: BiConsumer<T, String>): List<T?>? = reader.skipNullOr {
  val items = mutableListOf<T?>()
  beginArray()
  while (hasNext()) items += obj(mk, this, block)
  endArray()
  items
}

fun anyMap(reader: JsonReader): Map<String, Any?>? =
  reader.skipNullOr {
    beginObject()
    val map = mutableMapOf<String, Any?>()
    while (hasNext()) map += nextName() to readJsonValue()
    endObject()
    map
  }

private fun <T> JsonReader.skipNullOr(fn: JsonReader.() -> T): T? =
  if (peek() == JsonReader.Token.NULL) skipValue().let { null } else fn()
