package com.raf.imperial.jpa.enums;

import javax.persistence.AttributeConverter;

/**
 * Converter class for {@link ModeEnum}.
 *
 * @author RAF
 */
public final class ModeConverter implements AttributeConverter<ModeEnum, String> {

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
  public String convertToDatabaseColumn(final ModeEnum attribute) {
    if (attribute == null) {
      throw new IllegalArgumentException("ModeEnum is null");
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
  public ModeEnum convertToEntityAttribute(final String name) {
    final ModeEnum value = ModeEnum.get(name);
    if (value == null) {
      throw new IllegalArgumentException("ModeEnum not found for " + name);
    }
    return value;
  }

}
