package com.raf.imperial.jpa.enums;

import javax.persistence.AttributeConverter;

/**
 * Converter class for {@link ItemCategoryEnum}.
 *
 * @author RAF
 */
public final class ItemCategoryConverter implements AttributeConverter<ItemCategoryEnum, String> {

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
  public String convertToDatabaseColumn(final ItemCategoryEnum attribute) {
    if (attribute == null) {
      throw new IllegalArgumentException("ItemCategoryEnum is null");
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
  public ItemCategoryEnum convertToEntityAttribute(final String name) {
    final ItemCategoryEnum value = ItemCategoryEnum.get(name);
    if (value == null) {
      throw new IllegalArgumentException("ItemCategoryEnum not found for " + name);
    }
    return value;
  }

}
