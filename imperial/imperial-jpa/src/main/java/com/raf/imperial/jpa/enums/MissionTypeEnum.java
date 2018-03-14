package com.raf.imperial.jpa.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Mission types.
 *
 * @author RAF
 */
@Getter
@RequiredArgsConstructor
public enum MissionTypeEnum {

  /** Story mission. */
  STORY("story"),
  /** Red side mission. */
  RED("red"),
  /** Green side mission. */
  GREEN("green"),
  /** Gray side mission. */
  GRAY("gray"),
  /** Treath mission. */
  THREAT("threat");

  /**
   * Map for enum conversion.
   */
  private static final Map<String, MissionTypeEnum> MISSION_TYPES = new HashMap<>(MissionTypeEnum.values().length);
  static {
    for (final MissionTypeEnum missionTypeEnum : MissionTypeEnum.values()) {
      MISSION_TYPES.put(missionTypeEnum.code, missionTypeEnum);
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
  public static MissionTypeEnum get(final String code) {
    return MISSION_TYPES.get(code);
  }

}
