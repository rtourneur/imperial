package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.jpa.enums.ItemCategoryEnum;
import com.raf.imperial.jpa.enums.ItemTypeEnum;

/**
 * Test class for {@link ItemDao}.
 *
 * @author RAF
 */
public class ItemDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private ItemDao itemDao;

  /** The Expansion dao. */
  @Resource
  private ExpansionDao expansionDao;

  /**
   * Test method for {@link ItemDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final Integer ident = Integer.valueOf(17);
    final Item item = this.itemDao.getById(ident);
    assertNotNull(item);
    assertNotNull(item.toString());
    assertEquals(ident, item.getIdent());
    assertEquals("BD-1 Vibro-Ax", item.getName());
    assertEquals("item.bd-1-vibro-ax", item.getNameCode());
    assertEquals("item.bd-1-vibro-ax.desc", item.getDescriptionCode());
    assertEquals("item.bd-1-vibro-ax.rule", item.getRuleCode());
    assertEquals("Imperial Assault", item.getExpansionName());
    assertNotNull(item.getExpansion());
    assertEquals("Imperial Assault", item.getExpansion().getName());
    assertEquals(ItemCategoryEnum.TIER_2, item.getItemCategory());
    assertEquals(ItemTypeEnum.WEAPON, item.getItemType());
    assertEquals(AttackTypeEnum.MELEE, item.getAttackType());
    assertEquals("Imperial Assault", item.getExpansion().getName());
    assertEquals(Integer.valueOf(2), item.getModifications());
    assertEquals(Integer.valueOf(600), item.getCost());
    assertNotNull(item.getTraits());
    assertFalse(item.getTraits().isEmpty());
    assertNotNull(item.getCapacities());
    assertFalse(item.getCapacities().isEmpty());
    assertNotNull(item.getAttacks());
    assertFalse(item.getAttacks().isEmpty());
  }

  /**
   * Test method for {@link ItemDao#getByExample(Item)}.
   */
  @Test
  public void testGetByExample() {
    final Item example = new Item();
    example.setIdent(Integer.valueOf(17));
    final Item item = this.itemDao.getByExample(example);
    assertNotNull(item);
    assertNotNull(item.toString());
    assertEquals(example.getIdent(), item.getIdent());
    assertEquals("BD-1 Vibro-Ax", item.getName());
  }

  /**
   * Test method for {@link ItemDao#findByExample(Item)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Combat";
    final Item example = new Item();
    example.setName(name);
    List<Item> result = this.itemDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(4, result.size());
    final String expansionName = "Return to Hoth";
    final Expansion expansion = this.expansionDao.getById(expansionName);
    assertNotNull(expansion);
    example.setExpansion(expansion);
    example.setName(null);
    result = this.itemDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(9, result.size());
    example.setExpansion(null);
    example.setItemType(ItemTypeEnum.MODIFICATION);
    result = this.itemDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(17, result.size());
    example.setItemType(null);
    example.setItemCategory(ItemCategoryEnum.TIER_1);
    result = this.itemDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(22, result.size());
  }

  /**
   * Test method for {@link ItemDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Item> result = this.itemDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(67, result.size());
  }

  /**
   * Test method for {@link ItemDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Item> result = this.itemDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }

  /**
   * Test method for {@link ItemDao#getWeapons(ItemCategoryEnum)}.
   */
  @Test
  public void testGetWeapons() {
    List<Item> result = this.itemDao.getWeapons(ItemCategoryEnum.TIER_3);
    assertNotNull(result);
    assertEquals(9, result.size());
    result = this.itemDao.getWeapons();
    assertNotNull(result);
    assertEquals(29, result.size());
    result = this.itemDao.getWeapons(AttackTypeEnum.MELEE);
    assertNotNull(result);
    assertEquals(12, result.size());
  }

  /**
   * Test method for {@link ItemDao#getCapacities(Item)}.
   */
  @Test
  public void testGetCapacities() {
    final Integer ident = Integer.valueOf(17);
    final Item item = this.itemDao.getById(ident);
    assertNotNull(item);
    final List<Capacity> capacities = item.getCapacities();
    assertNotNull(capacities);
    assertFalse(capacities.isEmpty());
    assertEquals(Integer.valueOf(17), capacities.get(0).getIdentifier());
  }

  /**
   * Test method for {@link ItemDao#getAttack(Item)}.
   */
  @Test
  public void testGetAttack() {
    final Integer ident = Integer.valueOf(28);
    final Item item = this.itemDao.getById(ident);
    assertNotNull(item);
    final List<Dice> dices = item.getAttacks();
    assertNotNull(dices);
    assertEquals(3, dices.size());
    assertEquals("Red", dices.get(0).getIdentifier());
    assertEquals("Yellow", dices.get(1).getIdentifier());
    assertEquals("Yellow", dices.get(2).getIdentifier());
  }

  /**
   * Test method for {@link ItemDao#getAttack(Item, boolean)}.
   */
  @Test
  public void testGetAttackFocus() {
    final Integer ident = Integer.valueOf(28);
    final Item item = this.itemDao.getById(ident);
    assertNotNull(item);
    final List<Dice> dices = this.itemDao.getAttack(item, true);
    assertNotNull(dices);
    assertEquals(4, dices.size());
    assertEquals("Red", dices.get(0).getIdentifier());
    assertEquals("Yellow", dices.get(1).getIdentifier());
    assertEquals("Yellow", dices.get(2).getIdentifier());
    assertEquals("Green", dices.get(3).getIdentifier());
  }
}
