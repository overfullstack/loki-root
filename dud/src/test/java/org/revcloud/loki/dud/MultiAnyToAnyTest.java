package org.revcloud.loki.dud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MultiAnyToAnyTest {
  @Test
  void insertNull() {
    assertDoesNotThrow(() -> MultiAnyToAny.put("", "", null));
  }
}
