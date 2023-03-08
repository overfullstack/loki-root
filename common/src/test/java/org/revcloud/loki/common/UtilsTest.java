package org.revcloud.loki.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.util.List;
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
  
  private static class Bean {
    private final String name;
    private final List<String> items;

    private Bean(String name, List<String> items) {
      this.name = name;
      this.items = items;
    }

    public String getName() {
      return name;
    }

    public List<String> getItems() {
      return items;
    }
  }
  
  private static class NestedBean {
    private final String name;
    private final Bean bean;

    private NestedBean(String name, Bean bean) {
      this.name = name;
      this.bean = bean;
    }

    public String getName() {
      return name;
    }

    public Bean getBean() {
      return bean;
    }
  }

}
