package org.revcloud.loki.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UtilsTest {
  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  @DisplayName("json To Pojo")
  void jsonToPojo() {
    final var nestedBeanFromJson =
        Utils.jsonToPOJO(NestedBean.class, TEST_RESOURCES_PATH + "nested-bean.json");
    assertNotNull(nestedBeanFromJson);
    Assertions.assertEquals("container", nestedBeanFromJson.getName());
    Assertions.assertEquals(2, nestedBeanFromJson.getBean().getItems().size());
  }
}
