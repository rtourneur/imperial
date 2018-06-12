package com.raf.imperial.jpa.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;
import com.raf.imperial.jpa.Constant;

import lombok.NoArgsConstructor;

/**
 * The persistent class for the TRAIT database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "KEYWORD", schema = Constant.SCHEMA)
@NoArgsConstructor
public class KeyWord extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -7003885136647453811L;

}
