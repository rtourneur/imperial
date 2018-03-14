package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Attack types.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum AttackTypeEnum {
  /** Melee. */
  MELEE("Melee"),
  /** Ranged. */
  RANGED("Ranged"),
  /** None. */
  NONE("None");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, AttackTypeEnum> ATTACK_TYPES = new HashMap<>(AttackTypeEnum.values().length);
  static {
    for (final AttackTypeEnum sizeEnum : AttackTypeEnum.values()) {
      ATTACK_TYPES.put(sizeEnum.code, sizeEnum);
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
  public static AttackTypeEnum get(final String code) {
    return ATTACK_TYPES.get(code);
  }

}
