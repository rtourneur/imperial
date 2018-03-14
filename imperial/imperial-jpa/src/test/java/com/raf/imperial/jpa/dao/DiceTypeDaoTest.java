package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.DiceType;

/**
 * Test class for {@link DiceTypeDao}.
 *
 * @author RAF
 */
public class DiceTypeDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private DiceTypeDao diceTypeDao;
  
  /**
   * Test method for {@link DiceTypeDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Attack";
    final DiceType diceType = this.diceTypeDao.getById(name);
    assertNotNull(diceType);
    assertNotNull(diceType.toString());
    assertEquals(name, diceType.getName());
    assertEquals("dicetype.attack", diceType.getMessageCode());
  }

  /**
   * Test method for {@link DiceTypeDao#findByExample(DiceType)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "At";
    final DiceType example = new DiceType();
    example.setIdentifier(name);
    final List<DiceType> result = this.diceTypeDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
  }

  /**
   * Test method for {@link DiceTypeDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<DiceType> result = this.diceTypeDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }

  /**
   * Test method for {@link DiceTypeDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<DiceType> result = this.diceTypeDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }
}
