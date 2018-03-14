package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.Trait;

/**
 * Test class for {@link TraitDao}.
 *
 * @author RAF
 */
public class TraitDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private TraitDao traitDao;
  
  /**
   * Test method for {@link TraitDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Trooper";
    final Trait trait = this.traitDao.getById(name);
    assertNotNull(trait);
    assertNotNull(trait.toString());
    assertEquals(name, trait.getName());
    assertEquals("trait.trooper", trait.getMessageCode());
  }

  /**
   * Test method for {@link TraitDao#findByExample(Trait)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Tr";
    final Trait example = new Trait();
    example.setIdentifier(name);
    final List<Trait> result = this.traitDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
  }

  /**
   * Test method for {@link TraitDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Trait> result = this.traitDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(39, result.size());
  }

  /**
   * Test method for {@link TraitDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Trait> result = this.traitDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
