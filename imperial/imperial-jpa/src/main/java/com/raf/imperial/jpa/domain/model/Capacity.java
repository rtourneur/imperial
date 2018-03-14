package com.raf.imperial.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the CAPACITY database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "CAPACITY", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Capacity extends AbstractIdEntity {

  /** Serial UID. */
  private static final long serialVersionUID = 4016991731718261650L;

  /** The surge count. */
  @Column(name = "SURGE", precision = 1)
  private Integer surge;

  /** The first keyword. */
  @ManyToOne
  @JoinColumn(name = "KEY_1", nullable = false, foreignKey = @ForeignKey(name = "FK_CAPACITY_KEY_1"))
  private KeyWord key1;

  /** The first value. */
  @Column(name = "VALUE_1", precision = 1)
  private Integer value1;

  /** The second keyword. */
  @ManyToOne
  @JoinColumn(name = "KEY_2", foreignKey = @ForeignKey(name = "FK_CAPACITY_KEY_2"))
  private KeyWord key2;

  /** The second value. */
  @Column(name = "VALUE_2", precision = 1)
  private Integer value2;

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractIdEntity#appendId(ToStringBuilder)
   */
  @Override
  protected final void appendId(final ToStringBuilder builder) {
    if (this.surge != null) {
      builder.append("surge", this.surge);
    }
    builder.append("key", this.key1);
    if (this.value1 != null) {
      builder.append("value", this.value1);
    }
    if (this.key2 != null) {
      builder.append("key", this.key2);
      if (this.value2 != null) {
        builder.append("value", this.value2);
      }
    }
  }

}
