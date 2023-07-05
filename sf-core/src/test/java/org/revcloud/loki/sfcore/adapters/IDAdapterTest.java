package org.revcloud.loki.sfcore.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.force.swag.id.ID;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.revcloud.loki.common.json.JsonPojoUtils;

class IDAdapterTest {

  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  @DisplayName("stringToSfId")
  void stringToSfId() {
    final var sfId =
        JsonPojoUtils.<IDHolder>jsonFileToPojo(
            IDHolder.class, TEST_RESOURCES_PATH + "sf-id.json", List.of(new IDAdapter()));
    assertNotNull(sfId);
    assertEquals("01sxx0000005wB3AAI", sfId.id.toString());
  }

  private static class IDHolder {
    private final ID id;

    private IDHolder(ID id) {
      this.id = id;
    }
  }
}
