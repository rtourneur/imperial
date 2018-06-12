package com.raf.imperial.jpa.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;
import com.raf.imperial.jpa.Constant;

import lombok.NoArgsConstructor;

/**
 * The persistent class for the DICE_TYPE database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "DICE_TYPE", schema = Constant.SCHEMA)
@NoArgsConstructor
public class DiceType extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -8221088146289290144L;

}
