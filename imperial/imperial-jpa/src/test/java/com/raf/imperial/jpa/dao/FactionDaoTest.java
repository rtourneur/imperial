package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.Faction;

/**
 * Test class for {@link FactionDao}.
 *
 * @author RAF
 */
public class FactionDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private FactionDao factionDao;
  
  /**
   * Test method for {@link FactionDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Rebel";
    final Faction faction = this.factionDao.getById(name);
    assertNotNull(faction);
    assertNotNull(faction.toString());
    assertEquals(name, faction.getName());
    assertEquals("faction.rebel", faction.getMessageCode());
  }

  /**
   * Test method for {@link FactionDao#findByExample(Faction)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "I";
    final Faction example = new Faction();
    example.setIdentifier(name);
    final List<Faction> result = this.factionDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
  }

  /**
   * Test method for {@link FactionDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Faction> result = this.factionDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(4, result.size());
  }

  /**
   * Test method for {@link FactionDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Faction> result = this.factionDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(4, result.size());
  }
}
