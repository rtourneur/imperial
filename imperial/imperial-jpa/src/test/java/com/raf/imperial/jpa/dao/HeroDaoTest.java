package com.raf.imperial.jpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.fwk.util.Paged;
import com.raf.imperial.jpa.domain.hero.Hero;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.enums.AttributEnum;

/**
 * Test class for {@link HeroDao}.
 *
 * @author RAF
 */
public class HeroDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private HeroDao heroDao;

  /** The Expansion dao. */
  @Resource
  private ExpansionDao expansionDao;

  /**
   * Test method for {@link HeroDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final String name = "Diala Passil";
    final Hero hero = this.heroDao.getById(name);
    assertNotNull(hero);
    assertNotNull(hero.toString());
    assertEquals(name, hero.getName());
    assertEquals("hero.diala-passil", hero.getMessageCode());
    assertEquals("hero.diala-passil.title", hero.getTitleCode());
    assertEquals("Imperial Assault", hero.getExpansionName());
    assertNotNull(hero.getExpansion());
    assertEquals("Imperial Assault", hero.getExpansion().getName());
    assertNotNull(hero.getHealthyStat());
    assertEquals(12, hero.getHealthyStat().getHealth());
    assertEquals(5, hero.getHealthyStat().getEndurance());
    assertEquals(4, hero.getHealthyStat().getSpeed());
    assertNotNull(hero.getHealthyDefense());
    assertEquals("White", hero.getHealthyDefense().getName());
    assertNotNull(hero.getWoundedStat());
    assertEquals(12, hero.getWoundedStat().getHealth());
    assertEquals(4, hero.getWoundedStat().getEndurance());
    assertEquals(3, hero.getWoundedStat().getSpeed());
    assertNotNull(hero.getWoundedDefense());
    assertEquals("White", hero.getWoundedDefense().getName());
    assertNotNull(hero.getAbilities());
    assertFalse(hero.getAbilities().isEmpty());
    assertEquals(2, hero.getAbilities().size());
    assertEquals("Precise Strike", hero.getAbilities().get(0).getName());
    assertEquals("hero.ability.precise-strike", hero.getAbilities().get(0).getMessageCode());
    assertEquals("hero.ability.precise-strike.code", hero.getAbilities().get(0).getAbilityCode());
    assertNull(hero.getAbilities().get(0).getAction());
    assertEquals(Integer.valueOf(2), hero.getAbilities().get(0).getStrain());
    assertFalse(hero.getAbilities().get(0).isWounded());
    assertEquals("Foresight", hero.getAbilities().get(1).getName());
    assertEquals("hero.ability.foresight", hero.getAbilities().get(1).getMessageCode());
    assertEquals("hero.ability.foresight.code", hero.getAbilities().get(1).getAbilityCode());
    assertNull(hero.getAbilities().get(1).getAction());
    assertEquals(Integer.valueOf(1), hero.getAbilities().get(1).getStrain());
    assertTrue(hero.getAbilities().get(1).isWounded());
    assertNotNull(hero.getAttributs());
    assertNotNull(hero.getHealthAttr());
    assertEquals(3, hero.getHealthAttr().size());
    assertNotNull(hero.getHealthAttr().get(AttributEnum.STRENGTH));
    assertEquals(2, hero.getHealthAttr().get(AttributEnum.STRENGTH).size());
    assertNotNull(hero.getHealthAttr().get(AttributEnum.INSIGHT));
    assertEquals(3, hero.getHealthAttr().get(AttributEnum.INSIGHT).size());
    assertNotNull(hero.getHealthAttr().get(AttributEnum.TECH));
    assertEquals(1, hero.getHealthAttr().get(AttributEnum.TECH).size());
    assertNotNull(hero.getWoundedAttr());
    assertEquals(3, hero.getWoundedAttr().size());
    assertNotNull(hero.getWoundedAttr().get(AttributEnum.STRENGTH));
    assertEquals(2, hero.getWoundedAttr().get(AttributEnum.STRENGTH).size());
    assertNotNull(hero.getWoundedAttr().get(AttributEnum.INSIGHT));
    assertEquals(3, hero.getWoundedAttr().get(AttributEnum.INSIGHT).size());
    assertNotNull(hero.getWoundedAttr().get(AttributEnum.TECH));
    assertEquals(1, hero.getWoundedAttr().get(AttributEnum.TECH).size());
    assertNotNull(hero.toString());
  }

  /**
   * Test method for {@link HeroDao#findByExample(Hero)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "BI";
    final Hero example = new Hero();
    example.setIdentifier(name);
    List<Hero> result = this.heroDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    example.setIdentifier(null);
    final String expansionName = "Return to Hoth";
    final Expansion expansion = this.expansionDao.getById(expansionName);
    assertNotNull(expansion);
    example.setExpansion(expansion);
    example.setName(null);
    result = this.heroDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(3, result.size());
  }

  /**
   * Test method for {@link HeroDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Hero> result = this.heroDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(19, result.size());
  }

  /**
   * Test method for {@link HeroDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Hero> result = this.heroDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }
}
