package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Attributs.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum AttributEnum {
  /** Strength. */
  STRENGTH("Strength"),
  /** Insight. */
  INSIGHT("Insight"),
  /** Tech. */
  TECH("Tech");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, AttributEnum> ATTRIBUTS = new HashMap<>(AttributEnum.values().length);
  static {
    for (final AttributEnum attributEnum : AttributEnum.values()) {
      ATTRIBUTS.put(attributEnum.code, attributEnum);
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
  public static AttributEnum get(final String code) {
    return ATTRIBUTS.get(code);
  }

}
