package com.raf.imperial.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;
import com.raf.imperial.jpa.enums.ExpansionConverter;
import com.raf.imperial.jpa.enums.ExpansionEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The domain class for the EXPANSION table.
 *
 * @author RAF
 */
@Entity
@Table(name = "EXPANSION", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Expansion extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -8367856386816287411L;
  
  /** The type name. */
  @Convert(converter = ExpansionConverter.class)
  @Column(name = "TYPE", nullable = false, length = 30)
  private ExpansionEnum type;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractNamedEntity#appendNamed(ToStringBuilder)
   */
  @Override
  protected void appendNamed(final ToStringBuilder builder) {
    builder.append("type", this.type);
  }

}
