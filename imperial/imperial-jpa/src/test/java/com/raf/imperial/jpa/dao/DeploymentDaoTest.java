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
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Faction;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.jpa.enums.ModeEnum;
import com.raf.imperial.jpa.enums.SizeEnum;

/**
 * Test class for {@link DeploymentDao}.
 *
 * @author RAF
 */
public class DeploymentDaoTest extends AbstractDaoTest {

  /** The dao. */
  @Resource
  private DeploymentDao deploymentDao;

  /** The Faction dao. */
  @Resource
  private FactionDao factionDao;

  /**
   * Test method for {@link DeploymentDao#getById(java.io.Serializable)}.
   */
  @Test
  public void testGetById() {
    final Integer ident = Integer.valueOf(12);
    final Deployment deployment = this.deploymentDao.getById(ident);
    assertNotNull(deployment);
    assertNotNull(deployment.toString());
    assertEquals(ident, deployment.getIdent());
    assertEquals("Darth Vader", deployment.getName());
    assertEquals("deployment.darth-vader", deployment.getNameCode());
    assertEquals("deployment.darth-vader.title", deployment.getTitleCode());
    assertTrue(deployment.isElite());
    assertTrue(deployment.isUnique());
    assertEquals(ModeEnum.ALL, deployment.getMode());
    assertEquals("Imperial", deployment.getFactionName());
    assertNotNull(deployment.getFaction());
    assertEquals("Imperial", deployment.getFaction().getName());
    assertEquals(1, deployment.getCount());
    assertEquals(18, deployment.getCost());
    assertNull(deployment.getReinforce());
    assertEquals(SizeEnum.SMALL, deployment.getSize());
    assertEquals(16, deployment.getHealth());
    assertEquals(4, deployment.getSpeed());
    assertEquals(AttackTypeEnum.MELEE, deployment.getAttackType());
    assertNotNull(deployment.getTraits());
    assertFalse(deployment.getTraits().isEmpty());
    assertNotNull(deployment.getCapacities());
    assertFalse(deployment.getCapacities().isEmpty());
    assertNotNull(deployment.getAbilities());
    assertFalse(deployment.getAbilities().isEmpty());
    assertNotNull(deployment.getDefenses());
    assertFalse(deployment.getDefenses().isEmpty());
    assertNotNull(deployment.getAttacks());
    assertFalse(deployment.getAttacks().isEmpty());
  }

  /**
   * Test method for {@link DeploymentDao#getByExample(Deployment)}.
   */
  @Test
  public void testGetByExample() {
    final Deployment example = new Deployment();
    example.setIdent(Integer.valueOf(12));
    final Deployment deployment = this.deploymentDao.getByExample(example);
    assertNotNull(deployment);
    assertNotNull(deployment.toString());
    assertEquals(example.getIdent(), deployment.getIdent());
  }

  /**
   * Test method for {@link DeploymentDao#findByExample(Deployment)}.
   */
  @Test
  public void testFindByExample() {
    final String name = "Trooper";
    final Deployment example = new Deployment();
    example.setName(name);
    List<Deployment> result = this.deploymentDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
    final String factionName = "Rebel";
    final Faction faction = this.factionDao.getById(factionName);
    assertNotNull(faction);
    example.setFaction(faction);
    example.setName(null);
    result = this.deploymentDao.findByExample(example);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(32, result.size());
  }

  /**
   * Test method for {@link DeploymentDao#listAll()}.
   */
  @Test
  public void testListAll() {
    final List<Deployment> result = this.deploymentDao.listAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(76, result.size());
  }

  /**
   * Test method for {@link DeploymentDao#list(int, int)}.
   */
  @Test
  public void testList() {
    final Paged<Deployment> result = this.deploymentDao.list(10, 1);
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(10, result.size());
  }

  /**
   * Test method for {@link DeploymentDao#getCampagn(Faction)}.
   */
  @Test
  public void testGetCampagn() {
    final String factionName = "Rebel";
    final Faction faction = this.factionDao.getById(factionName);
    final List<Deployment> result = this.deploymentDao.getCampagn(faction);
    assertNotNull(result);
    assertEquals(17, result.size());
  }

  /**
   * Test method for {@link DeploymentDao#getSkirmish(Faction)}.
   */
  @Test
  public void testGetSkirmish() {
    final String factionName = "Rebel";
    final Faction faction = this.factionDao.getById(factionName);
    final List<Deployment> result = this.deploymentDao.getSkirmish(faction);
    assertNotNull(result);
    assertEquals(30, result.size());
  }

  /**
   * Test method for {@link DeploymentDao#getCapacities(Deployment)}.
   */
  @Test
  public void testGetCapacities() {
    final Integer ident = Integer.valueOf(17);
    final Deployment deployment = this.deploymentDao.getById(ident);
    assertNotNull(deployment);
    final List<Capacity> capacities = deployment.getCapacities();
    assertNotNull(capacities);
    assertFalse(capacities.isEmpty());
    assertEquals(Integer.valueOf(16), capacities.get(0).getIdentifier());
  }

  /**
   * Test method for {@link DeploymentDao#getAttack(Deployment)}.
   */
  @Test
  public void testGetAttack() {
    final Integer ident = Integer.valueOf(9);
    final Deployment deployment = this.deploymentDao.getById(ident);
    assertNotNull(deployment);
    final List<Dice> dices = deployment.getAttacks();
    assertNotNull(dices);
    assertEquals(3, dices.size());
    assertEquals("Blue", dices.get(0).getIdentifier());
    assertEquals("Yellow", dices.get(1).getIdentifier());
    assertEquals("Yellow", dices.get(2).getIdentifier());
  }

  /**
   * Test method for {@link DeploymentDao#getAttack(Deployment, boolean)}.
   */
  @Test
  public void testGetAttackFocus() {
    final Integer ident = Integer.valueOf(9);
    final Deployment deployment = this.deploymentDao.getById(ident);
    assertNotNull(deployment);
    final List<Dice> dices = this.deploymentDao.getAttack(deployment, true);
    assertNotNull(dices);
    assertEquals(4, dices.size());
    assertEquals("Blue", dices.get(0).getIdentifier());
    assertEquals("Yellow", dices.get(1).getIdentifier());
    assertEquals("Yellow", dices.get(2).getIdentifier());
    assertEquals("Green", dices.get(3).getIdentifier());
  }
}
