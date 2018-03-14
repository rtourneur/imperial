package com.raf.imperial.jpa.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;

import lombok.NoArgsConstructor;

/**
 * The domain class for the FACTION table.
 *
 * @author RAF
 */
@Entity
@Table(name = "FACTION", schema = "IMPERIAL")
@NoArgsConstructor
public class Faction extends AbstractNamedEntity {

  /** Serial UID. */
  private static final long serialVersionUID = -1591527035424776742L;

}
