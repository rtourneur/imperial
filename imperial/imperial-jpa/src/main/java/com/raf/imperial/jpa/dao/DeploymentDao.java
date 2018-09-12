package com.raf.imperial.jpa.dao;

import java.util.List;

import com.raf.fwk.jpa.dao.EntityIdDao;
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Faction;

/**
 * Interface DAO for {@link Deployment}.
 *
 * @author RAF
 */
public interface DeploymentDao extends EntityIdDao<Deployment> {

  /**
   * Return the list of deployment cards for a faction in campagn mode .
   * 
   * @param faction
   *          the faction
   * @return the list of deployment cards
   */
  List<Deployment> getCampagn(Faction faction);

  /**
   * Return the list of deployment cards for a faction in skirmish mode.
   * 
   * @param faction
   *          the faction
   * @return the list of deployment cards
   */
  List<Deployment> getSkirmish(Faction faction);

  /**
   * Return the list of attack dices for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @param defense
   *          the defense dice
   * @return the list of dices
   */
  List<Dice> getAttack(Deployment deployment, boolean focus, String defense);
}
