package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for item category.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum ItemCategoryEnum {
  /** The class item category. */
  CLASS("class"),
  /** The tier 1 item category. */
  TIER_1("tier1"),
  /** The tier 2 item category. */
  TIER_2("tier2"),
  /** The tier 3 item category. */
  TIER_3("tier3"),
  /** The reward item category. */
  REWARD("reward");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, ItemCategoryEnum> ITEM_CATEGORIES = new HashMap<>(ItemCategoryEnum.values().length);
  static {
    for (final ItemCategoryEnum itemCategoryEnum : ItemCategoryEnum.values()) {
      ITEM_CATEGORIES.put(itemCategoryEnum.code, itemCategoryEnum);
    }
  }

  /** Code for the enum. */
  private final String code;

  /**
   * Return the enum corresponding to the code.
   *
   * @param code
   *          Code of the enum
   * @return the enum.
   */
  public static ItemCategoryEnum get(final String code) {
    return ITEM_CATEGORIES.get(code);
  }

}
