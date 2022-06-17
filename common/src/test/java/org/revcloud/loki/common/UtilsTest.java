package org.revcloud.loki.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {
  private static final String TEST_RESOURCES_PATH = "src/test/resources/";
  
  @Test
  @DisplayName("json To Pojo")
  void jsonToPojo() {
    final var nestedBeanFromJson = Utils.jsonToPOJO(NestedBean.class, TEST_RESOURCES_PATH + "nested-bean.json");
    assertNotNull(nestedBeanFromJson);
    Assertions.assertEquals("container", nestedBeanFromJson.name);
    Assertions.assertEquals(2, nestedBeanFromJson.bean.items.size());
  }
  
  public record Bean(String name, List<String> items) {}
  public record NestedBean(String name, Bean bean) {}
  
}
