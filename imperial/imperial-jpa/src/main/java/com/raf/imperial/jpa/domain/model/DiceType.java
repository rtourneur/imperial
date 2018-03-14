package com.raf.imperial.jpa.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;

import lombok.NoArgsConstructor;

/**
 * The persistent class for the DICE_TYPE database table.
 * 
 * @author RAF
 */
@Entity
@Table(name = "DICE_TYPE", schema = "IMPERIAL")
@NoArgsConstructor
public class DiceType extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -8221088146289290144L;

}
