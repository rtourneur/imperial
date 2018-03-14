package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.KeyWord;

/**
 * Test class for {@link CapacityDao}.
 *
 * @author RAF
 */
public class CapacityDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private CapacityDao capacityDao;

  /** The KeyWord dao. */
  @Resource
  private KeyWordDao keyWordDao;

  /**
   * Test method for {@link CapacityDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final Integer ident = Integer.valueOf(18);
    final Capacity capacity = this.capacityDao.getById(ident);
    assertNotNull(capacity);
    assertNotNull(capacity.toString());
    assertEquals(ident, capacity.getIdent());
    assertNotNull(capacity.getKey1());
    assertEquals("Damage", capacity.getKey1().getName());
    assertEquals(Integer.valueOf(1), capacity.getValue1());
    assertNull(capacity.getKey2());
    assertNull(capacity.getValue2());
    assertEquals(Integer.valueOf(1), capacity.getSurge());
  }

  /**
   * Test method for {@link ItemDao#getByExample(Item)}.
   */
  @Test
  public void testGetByExample() {
    final Capacity example = new Capacity();
    example.setIdent(Integer.valueOf(18));
    final Capacity capacity = this.capacityDao.getByExample(example);
    assertNotNull(capacity);
    assertNotNull(capacity.toString());
    assertEquals(example.getIdent(), capacity.getIdent());
    assertNotNull(capacity.getKey1());
    assertEquals("Damage", capacity.getKey1().getName());
  }

  /**
   * Test method for {@link CapacityDao#findByExample(Capacity)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Damage";
    final KeyWord keyWord = this.keyWordDao.getById(name);
    assertNotNull(keyWord);
    final Capacity example = new Capacity();
    example.setKey1(keyWord);
    List<Capacity> result = this.capacityDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(11, result.size());
    example.setKey1(null);
    example.setKey2(keyWord);
    result = this.capacityDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(4, result.size());
    example.setKey2(null);
    example.setSurge(Integer.valueOf(2));
    result = this.capacityDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }

  /**
   * Test method for {@link CapacityDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Capacity> result = this.capacityDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(63, result.size());
  }

  /**
   * Test method for {@link CapacityDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Capacity> result = this.capacityDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
