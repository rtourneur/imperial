package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.model.Ability;

/**
 * Test class for {@link AbilityDao}.
 *
 * @author RAF
 */
public class AbilityDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private AbilityDao abilityDao;
  
  /**
   * Test method for {@link AbilityDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Aggressive Negotiations";
    final Ability ability = this.abilityDao.getById(name);
    assertNotNull(ability);
    assertNotNull(ability.toString());
    assertEquals(name, ability.getName());
    assertEquals("ability.aggressive-negotiations", ability.getMessageCode());
    assertEquals("ability.aggressive-negotiations.code", ability.getAbilityCode());
    assertNull(ability.getAction());
    assertEquals(Integer.valueOf(1), ability.getSurge());
  }

  /**
   * Test method for {@link AbilityDao#findByExample(Ability)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Co";
    final Ability example = new Ability();
    example.setIdentifier(name);
    final List<Ability> result = this.abilityDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(13, result.size());
  }

  /**
   * Test method for {@link AbilityDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Ability> result = this.abilityDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(178, result.size());
  }

  /**
   * Test method for {@link AbilityDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Ability> result = this.abilityDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
