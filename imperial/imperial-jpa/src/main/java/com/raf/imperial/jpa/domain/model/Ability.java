package com.raf.imperial.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class Ability extends AbstractAbility {

  /** Serial UID. */
  private static final long serialVersionUID = 282372842190787939L;

  /** The surge count. */
  @Column(name = "SURGE", precision = 1)
  private Integer surge;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractAbility#appendAbility(ToStringBuilder)
   */
  @Override
  protected void appendAbility(final ToStringBuilder builder) {
    builder.append("surge", this.surge);
  }

}
