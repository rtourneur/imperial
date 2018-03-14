package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Sizes.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum SizeEnum {
  /** Small Size 1x1. */
  SMALL("Small"),
  /** Medium Size 1x2. */
  MEDIUM("Medium"),
  /** Large Size 2x2. */
  LARGE("Large"),
  /** Massive Size 2x3. */
  MASSIVE("Massive");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, SizeEnum> SIZES = new HashMap<>(SizeEnum.values().length);
  static {
    for (final SizeEnum sizeEnum : SizeEnum.values()) {
      SIZES.put(sizeEnum.code, sizeEnum);
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
  public static SizeEnum get(final String code) {
    return SIZES.get(code);
  }

}
