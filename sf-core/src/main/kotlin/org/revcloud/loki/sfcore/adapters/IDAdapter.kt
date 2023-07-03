package org.revcloud.loki.sfcore.adapters

import com.force.swag.id.ID
import com.squareup.moshi.FromJson

// ! TODO 08/11/22 gopala.akshintala: Move these adapters out into a different repository

class IDAdapter {
  @FromJson fun fromJson(id: String?): ID = ID(id)
}
