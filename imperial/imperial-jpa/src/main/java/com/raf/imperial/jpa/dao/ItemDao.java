package com.raf.imperial.jpa.dao;

import java.util.List;

import com.raf.fwk.jpa.dao.EntityIdDao;
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.enums.ItemCategoryEnum;

/**
 * Interface DAO for {@link Deployment}.
 *
 * @author RAF
 */
public interface ItemDao extends EntityIdDao<Item> {

  /**
   * Return the list of item cards for an item category.
   * 
   * @param itemCategory
   *          the item category
   * @return the list of item cards
   */
  List<Item> getWeapons(ItemCategoryEnum itemCategory);

  /**
   * Return the list of attack dices for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @return the list of dices
   */
  List<Dice> getAttack(Item item, boolean focus);
}
