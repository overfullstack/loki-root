package org.revcloud.loki.sfcore.adapters

import com.force.swag.id.ID
import com.squareup.moshi.FromJson

class IDAdapter {
  @FromJson
  fun fromJson(id: String?): ID = ID(id)
}
