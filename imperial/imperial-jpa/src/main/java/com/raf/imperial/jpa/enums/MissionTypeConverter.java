package com.raf.imperial.jpa.enums;

import javax.persistence.AttributeConverter;

/**
 * Converter class for {@link MissionTypeEnum}.
 *
 * @author RAF
 */
public final class MissionTypeConverter implements AttributeConverter<MissionTypeEnum, String> {

  /**
   * Return the toString value for the enum.
   *
   * @param attribute
   *          the enum attribute
   * @throws IllegalArgumentException
   *           if enum attribute is null
   * @see AttributeConverter#convertToDatabaseColumn(Object)
   */
  @Override
  public String convertToDatabaseColumn(final MissionTypeEnum attribute) {
    if (attribute == null) {
      throw new IllegalArgumentException("MissionTypeEnum is null");
    }
    return attribute.getCode();
  }

  /**
   * Return the enum found in the enum map from the provided value.
   *
   * @param name
   *          the provided value for the name
   * @throws IllegalArgumentException
   *           if enum is not found
   * @see AttributeConverter#convertToEntityAttribute(Object)
   */
  @Override
  public MissionTypeEnum convertToEntityAttribute(final String name) {
    final MissionTypeEnum value = MissionTypeEnum.get(name);
    if (value == null) {
      throw new IllegalArgumentException("MissionTypeEnum not found for " + name);
    }
    return value;
  }

}
