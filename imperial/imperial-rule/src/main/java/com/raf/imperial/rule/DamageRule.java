package com.raf.imperial.rule;

import java.util.List;

import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.rule.model.Damages;

/**
 * Rule interface for calculing damages.
 * 
 * @author RAF
 */
public interface DamageRule {

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @return the damages
   */
  Damages getDamages(Deployment deployment);

  /**
   * Calcul and returns the damages for a deployment card and a minimum accuracy.
   * 
   * @param deployment
   *          the deployment card
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  Damages getDamages(Deployment deployment, int accuracy);

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @return the damages
   */
  Damages getDamages(Deployment deployment, boolean focus);

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @param defense
   *          the defense dice
   * @return the damages
   */
  Damages getDamages(Deployment deployment, boolean focus, String defense);

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  Damages getDamages(Deployment deployment, boolean focus, int accuracy);

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @param accuracy
   *          the minimum accuracy
   * @param defense
   *          the defense dice
   * @return the damages
   */
  Damages getDamages(Deployment deployment, boolean focus, int accuracy, String defense);

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @return the damages
   */
  Damages getDamages(Item item);

  /**
   * Calcul and returns the damages for a item card and a minimum accuracy.
   * 
   * @param item
   *          the item card
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  Damages getDamages(Item item, int accuracy);

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @return the damages
   */
  Damages getDamages(Item item, boolean focus);

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  Damages getDamages(Item item, boolean focus, int accuracy);

  /**
   * Calcul and returns the damages for an attack.
   * 
   * @param dices
   *          the list of attack dices
   * @param capacities
   *          the list of capacities
   * @param ranged
   *          the ranged indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  Damages getDamages(List<Dice> dices, List<Capacity> capacities, boolean ranged, int accuracy);
}
