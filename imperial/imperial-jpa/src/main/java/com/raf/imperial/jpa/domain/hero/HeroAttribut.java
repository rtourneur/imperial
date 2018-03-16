package com.raf.imperial.jpa.domain.hero;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.enums.AttributConverter;
import com.raf.imperial.jpa.enums.AttributEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the hero's attributes.
 * 
 * @author RAF
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class HeroAttribut implements Serializable {

  /** Serial UID. */
  private static final long serialVersionUID = 2303547322216557166L;

  /** The rank (for unicity). */
  @Column(name = "RANK", nullable = false, precision = 1)
  private int rank;

  /** The Attribut. */
  @Convert(converter = AttributConverter.class)
  @Column(name = "ATTRIBUT", nullable = false, length = 8)
  private AttributEnum attribut;

  /** The present if wounded indicator. */
  @Column(name = "WOUNDED", nullable = false)
  private boolean wounded;

  /** The Dice. */
  @ManyToOne
  @JoinColumn(name = "DICE_NAME", nullable = false, foreignKey = @ForeignKey(name = "FK_HERO_ATTRIBUT_DICE"))
  private Dice dice;

  /**
   * Generate the toString.
   *
   * @see Object#toString()
   */
  @Override
  public final String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE);
    builder.append("rank", this.rank).append("attribut", this.attribut).append("wounded", this.wounded).append("dice",
        this.dice);
    return builder.toString();
  }

}
