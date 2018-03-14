package com.raf.imperial.jpa.domain.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the DICE database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "DICE", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Dice extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -3559189752331196920L;

  /** The dice type name. */
  @Column(name = "DICE_TYPE", nullable = false, length = 30)
  private String diceTypeName;

  /** The dice sides. */
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "DICE_SIDE", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "DICE", foreignKey = @ForeignKey(name = "FK_DICE_SIDE_DICE")) }, indexes = {
          @Index(name = "IDX_DICE_SIDE", columnList = "DICE, SIDE", unique = true) })
  @OrderBy("side")
  private List<DiceSide> diceSides;

  /** The dice type. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DICE_TYPE", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_DICE_DICE_TYPE"))
  private DiceType diceType;

  /**
   * Defines the dice type.
   * 
   * @param diceType
   *          the dice type to set.
   */
  public void setDiceType(final DiceType diceType) {
    this.diceType = diceType;
    if (this.diceType != null) {
      this.diceTypeName = this.diceType.getName();
    }
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractNamedEntity#appendNamed(ToStringBuilder)
   */
  @Override
  protected final void appendNamed(final ToStringBuilder builder) {
    builder.append("diceTypeName", this.diceTypeName).append("diceSides", this.diceSides);
    if (this.diceType != null && DiceType.class.equals(this.diceType.getClass())) {
      builder.append("diceType", this.diceType);
    }
  }

}
