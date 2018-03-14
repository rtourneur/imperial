package com.raf.imperial.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.raf.fwk.jpa.dao.AbstractDao;
import com.raf.fwk.jpa.dao.AbstractIdDao;
import com.raf.imperial.jpa.dao.CapacityDao;
import com.raf.imperial.jpa.domain.model.Capacity;

/**
 * Implementation DAO for {@link Capacity}.
 *
 * @author RAF
 */
@Repository
public final class CapacityDaoImpl extends AbstractIdDao<Capacity> implements CapacityDao {

  /**
   * Constructor.
   */
  public CapacityDaoImpl() {
    super(Capacity.class);
  }


  /**
   * Set the predicate for the findByExample request.
   * <ul>
   * <li>key1</li>
   * <li>key2</li>
   * <li>surge</li>
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
  protected Predicate[] getPredicates(final Root<Capacity> root, final Capacity example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getKey1() != null) {
      predicatesList.add(getEquals(root, "key1", example.getKey1()));
    }
    if (example.getKey2() != null) {
      predicatesList.add(getEquals(root, "key2", example.getKey2()));
    }
    if (example.getSurge() != null) {
      predicatesList.add(getEquals(root, "surge", example.getSurge()));
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
  protected Predicate[] getUniquePredicates(final Root<Capacity> root, final Capacity example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getIdent() != null) {
      predicatesList.add(getEquals(root, IDENT, example.getIdent()));
    }
    return predicatesList.toArray(new Predicate[predicatesList.size()]);
  }


}
