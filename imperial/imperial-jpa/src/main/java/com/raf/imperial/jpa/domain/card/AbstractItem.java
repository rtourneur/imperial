package com.raf.imperial.jpa.domain.card;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractIdEntity;
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
public abstract class AbstractItem extends AbstractIdEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -8684264244155831090L;

  /** The name. */
  @Column(name = "NAME", length = 30, nullable = false)
  private String name;

  /** The name code. */
  @Column(name = "NAME_CODE", length = 40, nullable = false)
  private String nameCode;

  /** The title code. */
  @Column(name = "DESCRIPTION_CODE", length = 40, nullable = false)
  private String descriptionCode;

  /** The rule code. */
  @Column(name = "RULE_CODE", length = 40)
  private String ruleCode;

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
   * @see AbstractIdEntity#appendId(ToStringBuilder)
   */
  @Override
  protected final void appendId(final ToStringBuilder builder) {
    builder.append("name", this.name).append("nameCode", this.nameCode).append("descriptionCode", this.descriptionCode)
        .append("ruleCode", this.ruleCode).append("itemCategory", this.itemCategory).append("itemType", this.itemType)
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
