package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.DiceSide;

/**
 * Test class for {@link DiceDao}.
 *
 * @author RAF
 */
public class DiceDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private DiceDao diceDao;
  
  /**
   * Test method for {@link DiceDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Blue";
    final Dice dice = this.diceDao.getById(name);
    assertNotNull(dice);
    assertNotNull(dice.toString());
    assertEquals(name, dice.getName());
    assertEquals("dice.blue", dice.getMessageCode());
    assertNotNull(dice.getDiceType());
    assertEquals("Attack", dice.getDiceTypeName());
    assertNotNull(dice.getDiceSides());
    assertEquals(6, dice.getDiceSides().size());
    final DiceSide diceSide = dice.getDiceSides().get(3);
    assertEquals(4, diceSide.getSide());
    assertNull(diceSide.getDodge());
    assertNull(diceSide.getEvade());
    assertNull(diceSide.getBlock());
    assertEquals(Integer.valueOf(3), diceSide.getAccuracy());
    assertEquals(Integer.valueOf(1), diceSide.getDamage());
    assertEquals(Integer.valueOf(1), diceSide.getSurge());
    assertEquals("blue_4.png", diceSide.getIcon());
  }

  /**
   * Test method for {@link DiceDao#findByExample(Dice)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Bl";
    final Dice example = new Dice();
    example.setIdentifier(name);
    final List<Dice> result = this.diceDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }

  /**
   * Test method for {@link DiceDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Dice> result = this.diceDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(7, result.size());
  }

  /**
   * Test method for {@link DiceDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Dice> result = this.diceDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(7, result.size());
  }
}
