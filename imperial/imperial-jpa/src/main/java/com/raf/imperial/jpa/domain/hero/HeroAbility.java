package com.raf.imperial.jpa.domain.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.imperial.jpa.Constant;
import com.raf.imperial.jpa.domain.model.AbstractAbility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the hero's abilities.
 * 
 * @author RAF
 */
@Entity
@Table(name = "HERO_ABILITY", schema = Constant.SCHEMA)
@Getter
@Setter
@NoArgsConstructor
public class HeroAbility extends AbstractAbility {

  /** Serial UID. */
  private static final long serialVersionUID = 1627268302678054716L;

  /** The surge count. */
  @Column(name = "STRAIN", precision = 1)
  private Integer strain;

  /** The present if wounded indicator. */
  @Column(name = "WOUNDED", nullable = false)
  private boolean wounded;

  /** The hero. */
  @ManyToOne
  @JoinColumn(name = "HERO_NAME", nullable = false, insertable = false, updatable = false)
  private Hero hero;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractAbility#appendAbility(ToStringBuilder)
   */
  @Override
  protected void appendAbility(final ToStringBuilder builder) {
    builder.append("wounded", this.wounded);
  }

}
