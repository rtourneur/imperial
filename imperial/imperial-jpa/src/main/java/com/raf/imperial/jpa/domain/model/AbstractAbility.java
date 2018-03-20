package com.raf.imperial.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractDescriptionEntity;
import com.raf.fwk.jpa.domain.AbstractNamedEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract super class for abilities.
 * 
 * @author RAF
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class AbstractAbility extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = 6959744446412079762L;

  /** The ability code for internationalisation. */
  @Column(name = "ABILITY_CODE", nullable = false, length = 45)
  private String abilityCode;

  /** The action count. */
  @Column(name = "ACTION", precision = 1)
  private Integer action;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractDescriptionEntity#appendDescription(ToStringBuilder)
   */
  @Override
  protected final void appendNamed(final ToStringBuilder builder) {
    builder.append("abilityCode", this.abilityCode).append("action", this.action);
    appendAbility(builder);
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   */
  protected void appendAbility(final ToStringBuilder builder) {
    // RAS
  }

}
