package com.raf.imperial.jpa.domain.card;

import com.raf.fwk.jpa.domain.DomainIdEntity;

/**
 * Interface for Card entities.
 *
 * @author RAF
 */
public interface CardEntity extends DomainIdEntity {

  /**
   * Return the name of the card.
   * 
   * @return the name
   */
  String getName();
}
