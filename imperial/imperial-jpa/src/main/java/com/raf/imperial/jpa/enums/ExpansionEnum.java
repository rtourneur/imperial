package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Expansions.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum ExpansionEnum {
  /** The core game. */
  CORE("core"),
  /** The expansions. */
  EXPANSION("expansion"),
  /** The allies Pack. */
  ALLY("ally"),
  /** The villains Pack. */
  VILLAIN("villain");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, ExpansionEnum> EXPANSIONS = new HashMap<>(ExpansionEnum.values().length);
  static {
    for (final ExpansionEnum expansionEnum : ExpansionEnum.values()) {
      EXPANSIONS.put(expansionEnum.code, expansionEnum);
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
  public static ExpansionEnum get(final String code) {
    return EXPANSIONS.get(code);
  }

}
