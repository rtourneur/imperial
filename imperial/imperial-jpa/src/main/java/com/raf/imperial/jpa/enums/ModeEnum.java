package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Modes.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum ModeEnum {

  /** All mode. */
  ALL("all"),
  /** Campagn mode. */
  CAMPAGN("campagn"),
  /** Skirmish mode. */
  SKIRMISH("skirmish");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, ModeEnum> MODES = new HashMap<>(ModeEnum.values().length);
  static {
    for (final ModeEnum modeEnum : ModeEnum.values()) {
      MODES.put(modeEnum.code, modeEnum);
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
  public static ModeEnum get(final String code) {
    return MODES.get(code);
  }

}
