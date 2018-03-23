package com.raf.imperial.jpa.domain.card;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.imperial.jpa.enums.AttackTypeConverter;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.jpa.enums.ItemCategoryConverter;
import com.raf.imperial.jpa.enums.ItemCategoryEnum;
import com.raf.imperial.jpa.enums.ItemTypeConverter;
import com.raf.imperial.jpa.enums.ItemTypeEnum;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract super class for items.
 *
 * @author RAF
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractItem extends AbstractCard {

  /** Serial UID. */
  private static final long serialVersionUID = -8626597083810110862L;

  /** The Item Category type. */
  @Convert(converter = ItemCategoryConverter.class)
  @Column(name = "ITEM_CATEGORY", nullable = false, length = 6)
  private ItemCategoryEnum itemCategory;

  /** The attack type. */
  @Convert(converter = ItemTypeConverter.class)
  @Column(name = "ITEM_TYPE", nullable = false, length = 12)
  private ItemTypeEnum itemType;

  /** The attack type. */
  @Convert(converter = AttackTypeConverter.class)
  @Column(name = "ATTACK_TYPE", nullable = false, length = 6)
  private AttackTypeEnum attackType;

  /** The modification slot, only for weapons. */
  @Column(name = "MODIFICATIONS", nullable = true, precision = 1)
  private Integer modifications;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractCard#appendCard(ToStringBuilder)
   */
  @Override
  protected final void appendCard(final ToStringBuilder builder) {
    builder.append("itemCategory", this.itemCategory).append("itemType", this.itemType)
        .append("attackType", this.attackType).append("modifications", this.modifications);
    appendItem(builder);
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   */
  protected void appendItem(final ToStringBuilder builder) {
    // RAS
  }

}
