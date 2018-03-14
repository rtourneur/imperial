package com.raf.imperial.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractDescriptionEntity;
import com.raf.fwk.jpa.domain.AbstractNamedEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the CAPACITY database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "ABILITY", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Ability extends AbstractNamedEntity {
  
  /** Serial UID. */
  private static final long serialVersionUID = -7372351731837878883L;

  /** The ability code for internationalisation. */
  @Column(name = "ABILITY_CODE", nullable = false, length = 45)
  private String abilityCode;

  /** The action count. */
  @Column(name = "ACTION", precision = 1)
  private Integer action;
  
  /** The surge count. */
  @Column(name = "SURGE", precision = 1)
  private Integer surge;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractDescriptionEntity#appendDescription(ToStringBuilder)
   */
  @Override
  protected void appendNamed(final ToStringBuilder builder) {
    builder.append("abilityCode", this.abilityCode).append("action", this.action).append("surge", this.surge);
  }

  
}
