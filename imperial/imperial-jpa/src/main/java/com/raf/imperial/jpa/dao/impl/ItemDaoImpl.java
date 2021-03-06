package com.raf.imperial.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.raf.fwk.jpa.dao.AbstractDao;
import com.raf.fwk.jpa.dao.AbstractIdDao;
import com.raf.imperial.jpa.dao.DiceDao;
import com.raf.imperial.jpa.dao.ItemDao;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.jpa.enums.ItemCategoryEnum;
import com.raf.imperial.jpa.enums.ItemTypeEnum;

/**
 * Implementation DAO for {@link Item}.
 *
 * @author RAF
 */
@Repository
public final class ItemDaoImpl extends AbstractIdDao<Item> implements ItemDao {

  /** The Dice dao. */
  @Resource
  private DiceDao diceDao;

  /**
   * Constructor.
   */
  public ItemDaoImpl() {
    super(Item.class);
  }

  /**
   * Return the list of weapon item cards.
   * 
   * @return the list of item cards
   * @see ItemDao#getWeapons()
   */
  @Override
  public List<Item> getWeapons() {
    return getWeaponsImpl(null, null);
  }

  /**
   * Return the list of weapon item cards for an attack type.
   * 
   * @param attackType
   *          the attack type
   * @return the list of item cards
   * @see ItemDao#getWeapons(AttackTypeEnum)
   */
  @Override
  public List<Item> getWeapons(final AttackTypeEnum attackType) {
    return getWeaponsImpl(attackType, null);
  }

  /**
   * Return the list of item cards for an item category.
   * 
   * @param itemCategory
   *          the item category
   * @return the list of item cards
   * @see ItemDao#getWeapons(ItemCategoryEnum)
   */
  @Override
  public List<Item> getWeapons(final ItemCategoryEnum itemCategory) {
    return getWeaponsImpl(null, itemCategory);
  }

  /**
   * Return the list of attack dices for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @return the list of dices
   * @see ItemDao#getAttack(Item, boolean)
   */
  @Override
  public List<Dice> getAttack(final Item item, final boolean focus) {
    final List<Dice> itemDices = item.getAttacks();
    final int size;
    if (focus) {
      size = itemDices.size() + 1;
    } else {
      size = itemDices.size();
    }
    final List<Dice> dices = new ArrayList<>(size);
    for (final Dice dice : itemDices) {
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
   * <li>expansion</li>
   * <li>itemCategory</li>
   * <li>itemType</li>
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
  protected Predicate[] getPredicates(final Root<Item> root, final Item example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getName() != null) {
      predicatesList.add(getLike(root, NAME, example.getName()));
    }
    if (example.getExpansion() != null) {
      predicatesList.add(getEquals(root, "expansion", example.getExpansion()));
    }
    if (example.getItemCategory() != null) {
      predicatesList.add(getEquals(root, "itemCategory", example.getItemCategory()));
    }
    if (example.getItemType() != null) {
      predicatesList.add(getEquals(root, "itemType", example.getItemType()));
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
  protected Predicate[] getUniquePredicates(final Root<Item> root, final Item example) {
    final List<Predicate> predicatesList = new ArrayList<>();
    if (example.getIdent() != null) {
      predicatesList.add(getEquals(root, IDENT, example.getIdent()));
    }
    return predicatesList.toArray(new Predicate[predicatesList.size()]);
  }

  /**
   * Append the criteria default order.
   * <ul>
   * <li>itemCategory</li>
   * </ul>
   * 
   * @param orders
   *          the orders list
   * @param builder
   *          the criteria builder
   * @param root
   *          the root type
   * 
   * @see AbstractIdDao#appendOrder(List, CriteriaBuilder, Root)
   */
  @Override
  protected void appendOrder(final List<Order> orders, final CriteriaBuilder builder, final Root<Item> root) {
    orders.add(builder.asc(root.get("itemCategory")));
  }

  private List<Item> getWeaponsImpl(final AttackTypeEnum attackType, final ItemCategoryEnum itemCategory) {
    final CriteriaBuilder builder = getCriteriaBuilder();
    final CriteriaQuery<Item> criteria = getQuery();
    final Root<Item> from = getRoot(criteria);
    final List<Predicate> predicates = new ArrayList<>();
    predicates.add(builder.equal(from.get("itemType"), ItemTypeEnum.WEAPON));
    if (attackType != null) {
      predicates.add(builder.equal(from.get("attackType"), attackType));
    }
    if (itemCategory != null) {
      predicates.add(builder.equal(from.get("itemCategory"), itemCategory));
    }
    criteria.where(predicates.toArray(new Predicate[predicates.size()]));
    final TypedQuery<Item> query = getTypedQuery(criteria.orderBy(getOrder(builder, from)));
    return query.getResultList();
  }
}
