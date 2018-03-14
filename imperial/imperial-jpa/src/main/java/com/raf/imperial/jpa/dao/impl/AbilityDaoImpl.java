package com.raf.imperial.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.raf.fwk.jpa.dao.AbstractDao;
import com.raf.imperial.jpa.dao.AbilityDao;
import com.raf.imperial.jpa.domain.model.Ability;

/**
 * Implementation DAO for {@link Ability}.
 *
 * @author RAF
 */
@Repository
public final class AbilityDaoImpl extends AbstractDao<Ability, String> implements AbilityDao {

  /**
   * Constructor.
   */
  public AbilityDaoImpl() {
    super(Ability.class);
  }

  /**
   * Set the predicate for the findByExample request.
   * <ul>
   * <li>name</li>
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
  protected Predicate[] getPredicates(final Root<Ability> root, final Ability example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getName() != null) {
      predicatesList.add(getLike(root, NAME, example.getName()));
    }
    return predicatesList.toArray(new Predicate[predicatesList.size()]);
  }

  /**
   * Returns the criteria default order.
   * 
   * @param builder
   *          the criteria builder
   * @param root
   *          the root type
   * @return the criteria order
   * @see AbstractDao#getOrder(CriteriaBuilder, Root)
   */
  @Override
  protected List<Order> getOrder(final CriteriaBuilder builder, final Root<Ability> root) {
    final List<Order> orders = new ArrayList<>();
    orders.add(builder.asc(root.get(NAME)));
    return orders;
  }

}
