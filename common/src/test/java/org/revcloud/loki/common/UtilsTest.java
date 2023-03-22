package org.revcloud.loki.common;

import static org.assertj.core.api.Assertions.assertThat;
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
    final var nestedBeanFromJson = Utils.<NestedBean>jsonToPojo(NestedBean.class, TEST_RESOURCES_PATH + "nested-bean.json");
    assertThat(nestedBeanFromJson).isNotNull();
    assertThat(nestedBeanFromJson.getName()).isEqualTo("container");
    assertThat(nestedBeanFromJson.getBean().getItems()).hasSize(2);
  }

  @Test
  @DisplayName("pojo to json")
  void pojoToJson() {
    final var nestedBean = new NestedBean("container", new Bean("bean", List.of("item1", "item2")));
    final var nestedBeanJson = Utils.pojoToJson(NestedBean.class, nestedBean);
    System.out.println(nestedBeanJson);
    assertThat(nestedBeanJson).isNotBlank();
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
