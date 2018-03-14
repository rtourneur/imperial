package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.enums.ExpansionEnum;

/**
 * Test class for {@link ExpansionDao}.
 *
 * @author RAF
 */
public class ExpansionDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private ExpansionDao expansionDao;
  
  /**
   * Test method for {@link ExpansionDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Imperial Assault";
    final Expansion expansion = this.expansionDao.getById(name);
    assertNotNull(expansion);
    assertNotNull(expansion.toString());
    assertEquals(name, expansion.getName());
    assertEquals("expansion.imperial-assault", expansion.getMessageCode());
    assertEquals(ExpansionEnum.CORE, expansion.getType());
  }

  /**
   * Test method for {@link ExpansionDao#findByExample(Expansion)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Rebel";
    final Expansion example = new Expansion();
    example.setIdentifier(name);
    List<Expansion> result = this.expansionDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
    example.setIdentifier(null);
    example.setType(ExpansionEnum.EXPANSION);
    result = this.expansionDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(5, result.size());
  }

  /**
   * Test method for {@link ExpansionDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Expansion> result = this.expansionDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(42, result.size());
  }

  /**
   * Test method for {@link ExpansionDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Expansion> result = this.expansionDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
