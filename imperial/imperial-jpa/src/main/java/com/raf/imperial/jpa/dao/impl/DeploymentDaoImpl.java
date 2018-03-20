package com.raf.imperial.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.raf.fwk.jpa.dao.AbstractDao;
import com.raf.fwk.jpa.dao.AbstractIdDao;
import com.raf.imperial.jpa.dao.DeploymentDao;
import com.raf.imperial.jpa.dao.DiceDao;
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Faction;
import com.raf.imperial.jpa.enums.ModeEnum;

/**
 * Implementation DAO for {@link Deployment}.
 *
 * @author RAF
 */
@Repository
public final class DeploymentDaoImpl extends AbstractIdDao<Deployment> implements DeploymentDao {

  /** The Dice dao. */
  @Resource
  private DiceDao diceDao;

  /**
   * Constructor.
   */
  public DeploymentDaoImpl() {
    super(Deployment.class);
  }

  /**
   * Return the list of deployment cards for a faction in campagn mode .
   * 
   * @param faction
   *          the faction
   * @return the list of deployment cards
   * @see DeploymentDao#getCampagn(Faction)
   */
  @Override
  public List<Deployment> getCampagn(final Faction faction) {
    final CriteriaBuilder builder = getCriteriaBuilder();
    final CriteriaQuery<Deployment> criteria = getQuery();
    final Root<Deployment> from = getRoot(criteria);
    final Predicate modePred = builder.or(builder.equal(from.get("mode"), ModeEnum.ALL),
        builder.equal(from.get("mode"), ModeEnum.CAMPAGN));
    if (faction == null) {
      criteria.where(modePred);
    } else {
      final Predicate factionPred = builder.equal(from.get("faction"), faction);
      criteria.where(modePred, factionPred);
    }
    final Query query = getTypedQuery(criteria);
    return query.getResultList();
  }

  /**
   * Return the list of deployment cards for a faction in skirmish mode.
   * 
   * @param faction
   *          the faction
   * @return the list of deployment cards
   * @see DeploymentDao#getCampagn(Faction)
   */
  @Override
  public List<Deployment> getSkirmish(final Faction faction) {
    final CriteriaBuilder builder = getCriteriaBuilder();
    final CriteriaQuery<Deployment> criteria = getQuery();
    final Root<Deployment> from = getRoot(criteria);
    final Predicate modePred = builder.or(builder.equal(from.get("mode"), ModeEnum.ALL),
        builder.equal(from.get("mode"), ModeEnum.SKIRMISH));
    final Predicate factionPred = builder.equal(from.get("faction"), faction);
    criteria.where(modePred, factionPred);
    final Query query = getTypedQuery(criteria);
    return query.getResultList();
  }

  /**
   * Return the list of attack dices for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @return the list of dices
   * @see DeploymentDao#getAttack(Deployment, boolean)
   */
  @Override
  public List<Dice> getAttack(final Deployment deployment, final boolean focus) {
    final List<Dice> deploymentDices = deployment.getAttacks();
    final int size;
    if (focus) {
      size = deploymentDices.size() + 1;
    } else {
      size = deploymentDices.size();
    }
    final List<Dice> dices = new ArrayList<>(size);
    for (final Dice dice : deploymentDices) {
      dices.add(dice);
    }
    if (focus) {
      dices.add(this.diceDao.getById("Green"));
    }
    return dices;
  }

  /**
   * Set the predicate for the findByExample request.
   * <ul>
   * <li>name</li>
   * <li>faction</li>
   * </ul>
   *
   * @param root
   *          the root type
   * @param example
   *          the example
   * @return an array of predicates
   * @see AbstractDao#getPredicates(Root, com.raf.fwk.jpa.domain.DomainEntity)
   */
  @Override
  protected Predicate[] getPredicates(final Root<Deployment> root, final Deployment example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getName() != null) {
      predicatesList.add(getLike(root, NAME, example.getName()));
    }
    if (example.getFaction() != null) {
      predicatesList.add(getEquals(root, "faction", example.getFaction()));
    }
    return predicatesList.toArray(new Predicate[predicatesList.size()]);
  }

  /**
   * Set the predicate for the getByExample request.
   *
   * @param root
   *          the root type
   * @param example
   *          the example
   * @return an array of predicates
   * @see AbstractIdDao#getUniquePredicates(Root, com.raf.fwk.jpa.domain.DomainIdEntity)
   */
  @Override
  protected Predicate[] getUniquePredicates(final Root<Deployment> root, final Deployment example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getIdent() != null) {
      predicatesList.add(getEquals(root, IDENT, example.getIdent()));
    }
    return predicatesList.toArray(new Predicate[predicatesList.size()]);
  }

}
