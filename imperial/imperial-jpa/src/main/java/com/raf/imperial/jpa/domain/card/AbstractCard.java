package com.raf.imperial.jpa.domain.card;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractIdEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract super class for cards.
 *
 * @author RAF
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCard extends AbstractIdEntity implements CardEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -7826519511175667029L;

  /** The name. */
  @Column(name = "NAME", length = 30, nullable = false)
  private String name;

  /** The name code. */
  @Column(name = "NAME_CODE", length = 40, nullable = false)
  private String nameCode;

  /** The description code. */
  @Column(name = "DESCRIPTION_CODE", length = 40, nullable = false)
  private String descriptionCode;

  /** The rule code. */
  @Column(name = "RULE_CODE", length = 40)
  private String ruleCode;

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
        .append("ruleCode", this.ruleCode);
    appendCard(builder);
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   */
  protected void appendCard(final ToStringBuilder builder) {
    // RAS
  }

}
