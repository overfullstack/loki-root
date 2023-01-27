package org.revcloud.loki;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.revcloud.loki.Utils;

class UtilsTest {
  @Test
  void randomForPrimitiveType() {
    assertDoesNotThrow(
        () -> {
          Utils.INSTANCE.randomForPrimitiveType(String.class);
          Utils.INSTANCE.randomForPrimitiveType(Integer.class);
          Utils.INSTANCE.randomForPrimitiveType(Long.class);
          Utils.INSTANCE.randomForPrimitiveType(Double.class);
          Utils.INSTANCE.randomForPrimitiveType(Float.class);
          Utils.INSTANCE.randomForPrimitiveType(Boolean.class);
        });
  }
}
