package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.KeyWord;

/**
 * Test class for {@link KeyWordDao}.
 *
 * @author RAF
 */
public class KeyWordDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private KeyWordDao keyWordDao;
  
  /**
   * Test method for {@link KeyWordDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Damage";
    final KeyWord faction = this.keyWordDao.getById(name);
    assertNotNull(faction);
    assertNotNull(faction.toString());
    assertEquals(name, faction.getName());
    assertEquals("keyword.damage", faction.getMessageCode());
  }

  /**
   * Test method for {@link KeyWordDao#findByExample(KeyWord)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Hab";
    final KeyWord example = new KeyWord();
    example.setIdentifier(name);
    final List<KeyWord> result = this.keyWordDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }

  /**
   * Test method for {@link KeyWordDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<KeyWord> result = this.keyWordDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(19, result.size());
  }

  /**
   * Test method for {@link KeyWordDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<KeyWord> result = this.keyWordDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
