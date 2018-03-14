package com.raf.imperial.jpa.domain.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the dice sides.
 * 
 * @author RAF
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DiceSide implements Serializable {

  /** Serial UID. */
  private static final long serialVersionUID = -419660719094981627L;

  /** The side of the dice (for unicity). */
  @Column(name = "SIDE", nullable = false, precision = 1)
  private int side;

  /** The Dodge (for defense dice). */
  @Column(name = "DODGE")
  private Boolean dodge;

  /** The accuracy value. */
  @Column(name = "ACCURACY", precision = 1)
  private Integer accuracy;

  /** The damage value. */
  @Column(name = "DAMAGE", precision = 1)
  private Integer damage;

  /** The surge value. */
  @Column(name = "SURGE", precision = 1)
  private Integer surge;

  /** The block value. */
  @Column(name = "BLOCK", precision = 1)
  private Integer block;

  /** The evade value. */
  @Column(name = "EVADE", precision = 1)
  private Integer evade;

  /** The icon. */
  @Column(name = "ICON", length = 30)
  private String icon;

  /**
   * Generate the toString.
   *
   * @see Object#toString()
   */
  @Override
  public final String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE);
    builder.append("side", this.side).append("dodge", this.dodge).append("accuracy", this.accuracy)
        .append("damage", this.damage).append("surge", this.surge).append("block", this.block)
        .append("evade", this.evade).append("icon", this.icon);
    return builder.toString();
  }
}
