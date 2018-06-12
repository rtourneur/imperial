package com.raf.imperial.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.raf.imperial.jpa.dao.CapacityDao;
import com.raf.imperial.jpa.dao.DeploymentDao;
import com.raf.imperial.jpa.dao.DiceDao;
import com.raf.imperial.jpa.dao.FactionDao;
import com.raf.imperial.jpa.dao.ItemDao;
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Ability;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Faction;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.rule.model.DamageValue;
import com.raf.imperial.rule.model.Damages;

import lombok.extern.slf4j.Slf4j;

/**
 * Test class for {@link DamageRule}.
 * 
 * @author RAF
 */
@Slf4j
public class DamageRuleTest extends AbstractRuleTest {

  /** The tab character. */
  private static final char TAB = '\t';

  /** The damage rule. */
  @Resource
  private DamageRule damageRule;

  /** The deployment dao. */
  @Resource
  private DeploymentDao deploymentDao;

  /** The item DAO. */
  @Resource
  private ItemDao itemDao;

  /** The faction dao. */
  @Resource
  private FactionDao factionDao;

  /** The dice dao. */
  @Resource
  private DiceDao diceDao;

  /** The capacity dao. */
  @Resource
  private CapacityDao capacityDao;

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesDeployment() {
    final Deployment deployment = this.deploymentDao.getById(Integer.valueOf(7));
    final boolean ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
    final Damages damages = this.damageRule.getDamages(deployment);
    assertNotNull(damages);
    final StringBuilder builder = new StringBuilder();
    calculStat(builder, deployment, null, damages, ranged);
    log.info(builder.toString());
    for (DamageValue damageValue : damages.getValues()) {
      log.info(damageValue.toString());
    }
    assertEquals(1, damages.getMinDamage());
    assertEquals(5, damages.getMaxDamage());
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesDeploymentRange() {
    final StringBuilder builder = new StringBuilder();
    final List<String> stats = new ArrayList<>();
    final Deployment deployment = this.deploymentDao.getById(Integer.valueOf(1));
    final boolean ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
    Damages damages = this.damageRule.getDamages(deployment);
    assertNotNull(damages);
    calculStat(builder, deployment, null, damages, ranged);
    stats.add(builder.toString());
    for (int i = 1; i <= 10; i++) {
      damages = this.damageRule.getDamages(deployment, i);
      assertNotNull(damages);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
    }
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesDeploymentAll() {
    final List<Deployment> deployments = this.deploymentDao.listAll();
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
      if (ranged) {
        for (int i = 1; i <= 10; i++) {
          damages = this.damageRule.getDamages(deployment, i);
          assertNotNull(damages);
          calculStat(builder, deployment, null, damages, ranged);
          stats.add(builder.toString());
        }
      }
    }
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesDeploymentAllDefense() {
    final List<Deployment> deployments = this.deploymentDao.listAll();
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment);
      calculStat(builder, deployment, "Basic", damages, ranged);
      stats.add(builder.toString());
      List<Dice> dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("Black"));
      final List<Capacity> capacities = deployment.getCapacities();
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black", damages, ranged);
      stats.add(builder.toString());
      dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White", damages, ranged);
      stats.add(builder.toString());
    }
    final StringBuilder logBuilder = new StringBuilder().append('\n');
    for (String string : stats) {
      logBuilder.append(string).append('\n');
    }
    log.info(logBuilder.toString());
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesFocusAllDefense() {
    final List<Deployment> deployments = this.deploymentDao.getCampagn(null);
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment, false);
      calculStat(builder, deployment, "Basic", damages, ranged);
      stats.add(builder.toString());
      damages = this.damageRule.getDamages(deployment, true);
      calculStat(builder, deployment, "Basic - Focus", damages, ranged);
      stats.add(builder.toString());
      List<Dice> dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("Black"));
      final List<Capacity> capacities = deployment.getCapacities();
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black", damages, ranged);
      stats.add(builder.toString());
      dices.add(this.diceDao.getById("Green"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black - Focus", damages, ranged);
      stats.add(builder.toString());
      dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White", damages, ranged);
      stats.add(builder.toString());
      dices.add(this.diceDao.getById("Green"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White - Focus", damages, ranged);
      stats.add(builder.toString());
    }
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment, boolean)}.
   */
  @Test
  public final void testGetDamagesFocusAll() {
    final List<Deployment> deployments = this.deploymentDao.listAll();
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment, true);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
    }
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Deployment)}.
   */
  @Test
  public final void testGetDamagesCampagn() {
    final Faction rebel = this.factionDao.getById("Rebel");
    List<Deployment> deployments = this.deploymentDao.getCampagn(rebel);
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
      List<Dice> dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("Black"));
      final List<Capacity> capacities = deployment.getCapacities();
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black", damages, ranged);
      stats.add(builder.toString());
      dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White", damages, ranged);
      stats.add(builder.toString());
    }
    final Faction imperial = this.factionDao.getById("Imperial");
    deployments = this.deploymentDao.getCampagn(imperial);
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
      List<Dice> dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("Black"));
      final List<Capacity> capacities = deployment.getCapacities();
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black", damages, ranged);
      stats.add(builder.toString());
      dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White", damages, ranged);
      stats.add(builder.toString());
    }
    final Faction mercenary = this.factionDao.getById("Mercenary");
    deployments = this.deploymentDao.getCampagn(mercenary);
    for (Deployment deployment : deployments) {
      ranged = AttackTypeEnum.RANGED.equals(deployment.getAttackType());
      damages = this.damageRule.getDamages(deployment);
      calculStat(builder, deployment, null, damages, ranged);
      stats.add(builder.toString());
      List<Dice> dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("Black"));
      final List<Capacity> capacities = deployment.getCapacities();
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "Black", damages, ranged);
      stats.add(builder.toString());
      dices = deployment.getAttacks();
      replaceUndefinedDices(dices, deployment.getAbilities());
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, deployment, "White", damages, ranged);
      stats.add(builder.toString());
    }
    final StringBuilder logBuilder = new StringBuilder().append('\n');
    for (String string : stats) {
      logBuilder.append(string).append('\n');
    }
    log.info(logBuilder.toString());
  }

  /**
   * Test method for {@link DamageRule#getDamages(List, List, boolean)}.
   */
  @Test
  public final void testGetDamagesAttack() {
    final StringBuilder builder = new StringBuilder();
    final List<String> stats = new ArrayList<>();
    final Deployment deployment = this.deploymentDao.getById(Integer.valueOf(47));
    Damages damages = this.damageRule.getDamages(deployment);
    calculStat(builder, deployment, null, damages, true);
    stats.add(builder.toString());
    final List<Dice> dices = new ArrayList<>(2);
    dices.add(this.diceDao.getById("Red"));
    dices.add(this.diceDao.getById("Green"));
    final List<Capacity> capacities = new ArrayList<>(2);
    capacities.add(this.capacityDao.getById(Integer.valueOf(18)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(29)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, null, damages, true);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, null, damages, false);
    stats.add(builder.toString());
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(List, List, boolean, int)}.
   */
  @Test
  public final void testGetDamagesAttackDefense() {
    final StringBuilder builder = new StringBuilder();
    final List<String> stats = new ArrayList<>();
    final Deployment deployment = this.deploymentDao.getById(Integer.valueOf(1));
    Damages damages = this.damageRule.getDamages(deployment, 3);
    calculStat(builder, deployment, null, damages, true);
    stats.add(builder.toString());
    List<Dice> dices = deployment.getAttacks();
    dices.add(this.diceDao.getById("Black"));
    final List<Capacity> capacities = deployment.getCapacities();
    damages = this.damageRule.getDamages(dices, capacities, true, 3);
    String option = "Black defense dice";
    calculStat(builder, deployment, option, damages, true);
    stats.add(builder.toString());
    dices = deployment.getAttacks();
    dices.add(this.diceDao.getById("White"));
    damages = this.damageRule.getDamages(dices, capacities, true, 3);
    option = "White defense dice";
    calculStat(builder, deployment, option, damages, true);
    stats.add(builder.toString());
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(List, List, boolean)}.
   */
  @Test
  public final void testGetDamagesAttackDiala() {
    final StringBuilder builder = new StringBuilder();
    final List<String> stats = new ArrayList<>();
    final Deployment deployment = this.deploymentDao.getById(Integer.valueOf(24));
    // Plasteel Staff
    final List<Dice> dices = new ArrayList<>(2);
    dices.add(this.diceDao.getById("Green"));
    dices.add(this.diceDao.getById("Yellow"));
    final List<Capacity> capacities = new ArrayList<>(2);
    capacities.add(this.capacityDao.getById(Integer.valueOf(17)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(56)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(18)));
    Damages damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, "Plasteel Staff", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addBlack(dices), capacities, false, 0);
    calculStat(builder, deployment, "Plasteel Staff - Black", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addWhite(dices), capacities, false, 0);
    calculStat(builder, deployment, "Plasteel Staff - White", damages, false);
    stats.add(builder.toString());
    dices.add(this.diceDao.getById("Blue"));
    capacities.add(this.capacityDao.getById(Integer.valueOf(30)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, "Plasteel Staff - Dancing Weapon", damages, true);
    stats.add(builder.toString());
    // BD-1 Vibro-Ax
    dices.clear();
    dices.add(this.diceDao.getById("Red"));
    dices.add(this.diceDao.getById("Green"));
    capacities.clear();
    capacities.add(this.capacityDao.getById(Integer.valueOf(17)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(20)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(39)));
    damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, "BD-1 Vibro-Ax", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addBlack(dices), capacities, false, 0);
    calculStat(builder, deployment, "BD-1 Vibro-Ax - Black", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addWhite(dices), capacities, false, 0);
    calculStat(builder, deployment, "BD-1 Vibro-Ax - White", damages, false);
    stats.add(builder.toString());
    dices.add(this.diceDao.getById("Blue"));
    capacities.add(this.capacityDao.getById(Integer.valueOf(30)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, "BD-1 Vibro-Ax - Dancing Weapon", damages, true);
    stats.add(builder.toString());
    // Force Pike
    dices.clear();
    dices.add(this.diceDao.getById("Red"));
    dices.add(this.diceDao.getById("Yellow"));
    dices.add(this.diceDao.getById("Yellow"));
    capacities.clear();
    capacities.add(this.capacityDao.getById(Integer.valueOf(17)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(18)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(18)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(56)));
    damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, "Force Pike", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addBlack(dices), capacities, false, 0);
    calculStat(builder, deployment, "Force Pike - Black", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addWhite(dices), capacities, false, 0);
    calculStat(builder, deployment, "Force Pike - White", damages, false);
    stats.add(builder.toString());
    dices.add(this.diceDao.getById("Blue"));
    capacities.add(this.capacityDao.getById(Integer.valueOf(30)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, "Force Pike - Dancing Weapon", damages, true);
    stats.add(builder.toString());
    // Shu Yen's Lightsaber
    dices.clear();
    dices.add(this.diceDao.getById("Blue"));
    dices.add(this.diceDao.getById("Red"));
    capacities.clear();
    capacities.add(this.capacityDao.getById(Integer.valueOf(52)));
    capacities.add(this.capacityDao.getById(Integer.valueOf(21)));
    damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, "Shu Yen's Lightsaber", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addBlack(dices), capacities, false, 0);
    calculStat(builder, deployment, "Shu Yen's Lightsaber - Black", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addWhite(dices), capacities, false, 0);
    calculStat(builder, deployment, "Shu Yen's Lightsaber - White", damages, false);
    stats.add(builder.toString());
    dices.add(this.diceDao.getById("Blue"));
    capacities.add(this.capacityDao.getById(Integer.valueOf(30)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, "Shu Yen's Lightsaber - Dancing Weapon", damages, true);
    stats.add(builder.toString());
    // Ancient Lightsaber
    dices.clear();
    dices.add(this.diceDao.getById("Blue"));
    dices.add(this.diceDao.getById("Green"));
    dices.add(this.diceDao.getById("Yellow"));
    capacities.clear();
    capacities.add(this.capacityDao.getById(Integer.valueOf(25)));
    damages = this.damageRule.getDamages(dices, capacities, false, 0);
    calculStat(builder, deployment, "Ancient Lightsaber", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addBlack(dices), capacities, false, 0);
    calculStat(builder, deployment, "Ancient Lightsaber - Black", damages, false);
    stats.add(builder.toString());
    damages = this.damageRule.getDamages(addWhite(dices), capacities, false, 0);
    calculStat(builder, deployment, "Ancient Lightsaber - White", damages, false);
    stats.add(builder.toString());
    dices.add(this.diceDao.getById("Blue"));
    capacities.add(this.capacityDao.getById(Integer.valueOf(30)));
    damages = this.damageRule.getDamages(dices, capacities, true, 0);
    calculStat(builder, deployment, "Ancient Lightsaber - Dancing Weapon", damages, true);
    stats.add(builder.toString());
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Item)}.
   */
  @Test
  public final void testGetDamagesItem() {
    final Item item = this.itemDao.getById(Integer.valueOf(17));
    final boolean ranged = AttackTypeEnum.RANGED.equals(item.getAttackType());
    final Damages damages = this.damageRule.getDamages(item);
    assertNotNull(damages);
    final StringBuilder builder = new StringBuilder();
    calculStat(builder, item, null, damages, ranged);
    log.info(builder.toString());
    for (DamageValue damageValue : damages.getValues()) {
      log.info(damageValue.toString());
    }
    assertEquals(2, damages.getMinDamage());
    assertEquals(5, damages.getMaxDamage());
  }

  /**
   * Test method for {@link DamageRule#getDamages(Item)}.
   */
  @Test
  public final void testGetDamagesItemAll() {
    final List<Item> items = new ArrayList<>();
    items.addAll(itemDao.getWeapons(AttackTypeEnum.RANGED));
    items.addAll(itemDao.getWeapons(AttackTypeEnum.MELEE));
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Item item : items) {
      ranged = AttackTypeEnum.RANGED.equals(item.getAttackType());
      final List<Capacity> capacities = item.getCapacities();
      final List<Dice> dices = getItemAttackDices(item);
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, item, null, damages, ranged);
      stats.add(builder.toString());
      if (ranged) {
        for (int i = 1; i <= 10; i++) {
          damages = this.damageRule.getDamages(dices, capacities, ranged, i);
          assertNotNull(damages);
          calculStat(builder, item, null, damages, ranged);
          stats.add(builder.toString());
        }
      }
    }
    for (String string : stats) {
      log.info(string);
    }
  }

  /**
   * Test method for {@link DamageRule#getDamages(Item)}.
   */
  @Test
  public final void testGetDamagesItemAllDefense() {
    final List<Item> items = new ArrayList<>();
    items.addAll(itemDao.getWeapons(AttackTypeEnum.RANGED));
    items.addAll(itemDao.getWeapons(AttackTypeEnum.MELEE));
    Damages damages;
    final StringBuilder builder = new StringBuilder();
    boolean ranged;
    final List<String> stats = new ArrayList<>();
    for (Item item : items) {
      ranged = AttackTypeEnum.RANGED.equals(item.getAttackType());
      final List<Capacity> capacities = item.getCapacities();
      List<Dice> dices;
      dices = getItemAttackDices(item);
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, item, null, damages, ranged);
      stats.add(builder.toString());
      dices = getItemAttackDices(item);
      dices.add(this.diceDao.getById("Black"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, item, "Black", damages, ranged);
      stats.add(builder.toString());
      dices = getItemAttackDices(item);
      dices.add(this.diceDao.getById("White"));
      damages = this.damageRule.getDamages(dices, capacities, ranged, 0);
      calculStat(builder, item, "White", damages, ranged);
      stats.add(builder.toString());
    }
    final StringBuilder logBuilder = new StringBuilder().append('\n');
    for (String string : stats) {
      logBuilder.append(string).append('\n');
    }
    log.info(logBuilder.toString());
  }

  private List<Dice> getItemAttackDices(final Item item) {
    final List<Dice> dices = item.getAttacks();
    if (dices.isEmpty()) {
      dices.add(this.diceDao.getById("Blue"));
      dices.add(this.diceDao.getById("Green"));
      dices.add(this.diceDao.getById("Yellow"));
    }
    return dices;
  }

  private void calculStat(final StringBuilder builder, final Deployment deployment, final String option,
      final Damages damages, final boolean ranged) {
    builder.setLength(0);
    builder.append(deployment.getName()).append(TAB).append(deployment.getCost()).append(TAB)
        .append(deployment.getCount()).append(TAB);
    if (deployment.isElite()) {
      builder.append("elite");
    }
    builder.append(TAB);
    if (option != null) {
      builder.append(option);
    }
    builder.append(TAB);
    if (damages.getAccuracy() > 0) {
      builder.append("range " + damages.getAccuracy());
    }
    builder.append(TAB).append("damages [").append(damages.getMinDamage()).append('-').append(damages.getMaxDamage())
        .append(']').append(TAB).append(String.format("%1$,.2f", damages.getMoyDamage())).append(TAB);
    if (ranged) {
      builder.append("accuracy [").append(damages.getMinAccuracy()).append('-').append(damages.getMaxAccuracy())
          .append(']').append(TAB).append(String.format("%1$,.2f", damages.getMoyAccuracy())).append(TAB);
    } else {
      builder.append(TAB).append(TAB);
    }
    builder.append("surges [").append(damages.getMinSurge()).append('-').append(damages.getMaxSurge()).append(']')
        .append(TAB).append(String.format("%1$,.2f", damages.getMoySurge()));
  }

  private void calculStat(final StringBuilder builder, final Item item, final String option, final Damages damages,
      final boolean ranged) {
    builder.setLength(0);
    builder.append(item.getName()).append(TAB).append(item.getCost()).append(TAB).append(TAB).append(TAB);
    if (option != null) {
      builder.append(option);
    }
    builder.append(TAB);
    if (damages.getAccuracy() > 0) {
      builder.append(" range " + damages.getAccuracy());
    }
    builder.append(TAB).append("damages [").append(damages.getMinDamage()).append('-').append(damages.getMaxDamage())
        .append(']').append(TAB).append(String.format("%1$,.2f", damages.getMoyDamage())).append(TAB);
    if (ranged) {
      builder.append("accuracy [").append(damages.getMinAccuracy()).append('-').append(damages.getMaxAccuracy())
          .append(']').append(TAB).append(String.format("%1$,.2f", damages.getMoyAccuracy())).append(TAB);
    } else {
      builder.append(TAB).append(TAB);
    }
    builder.append("surges [").append(damages.getMinSurge()).append('-').append(damages.getMaxSurge()).append(']')
        .append(TAB).append(String.format("%1$,.2f", damages.getMoySurge()));
  }

  /**
   * Overload the list of dice with special abilities.
   * 
   * @param dices
   *          the list of dices
   * @param abilities
   *          the list of abilities
   */
  private void replaceUndefinedDices(final List<Dice> dices, final List<Ability> abilities) {
    for (final Ability ability : abilities) {
      if ("Epic Arsenal".equals(ability.getName())) {
        dices.clear();
        dices.add(diceDao.getById("Blue"));
        dices.add(diceDao.getById("Red"));
        dices.add(diceDao.getById("Red"));
      } else if ("Arsenal".equals(ability.getName())) {
        dices.clear();
        dices.add(diceDao.getById("Blue"));
        dices.add(diceDao.getById("Red"));
      }
    }
  }

  private List<Dice> addBlack(final List<Dice> dices) {
    final List<Dice> black = new ArrayList<>(dices);
    black.add(this.diceDao.getById("Black"));
    return black;
  }

  private List<Dice> addWhite(final List<Dice> dices) {
    final List<Dice> white = new ArrayList<>(dices);
    white.add(this.diceDao.getById("White"));
    return white;
  }

}
