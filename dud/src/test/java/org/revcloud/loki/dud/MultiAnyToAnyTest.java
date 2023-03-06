package org.revcloud.loki.dud;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class MultiAnyToAnyTest {
  @Test
  void insertNull() {
    assertDoesNotThrow(() -> MultiAnyToAny.put("", "", null));
  }
}
