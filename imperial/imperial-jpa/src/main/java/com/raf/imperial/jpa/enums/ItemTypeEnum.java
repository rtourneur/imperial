package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for item type.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum ItemTypeEnum {
  /** The Weapon item type. */
  WEAPON("Weapon"),
  /** The Modification item type. */
  MODIFICATION("Modification"),
  /** The Armor item type. */
  ARMOR("Armor"),
  /** The Accessory item type. */
  ACCESSORY("Accessory");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, ItemTypeEnum> ITEM_TYPES = new HashMap<>(ItemTypeEnum.values().length);
  static {
    for (final ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()) {
      ITEM_TYPES.put(itemTypeEnum.code, itemTypeEnum);
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
  public static ItemTypeEnum get(final String code) {
    return ITEM_TYPES.get(code);
  }

}
