package com.raf.imperial.rule.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raf.fwk.util.aop.Loggable;
import com.raf.imperial.jpa.dao.DeploymentDao;
import com.raf.imperial.jpa.dao.DiceDao;
import com.raf.imperial.jpa.dao.ItemDao;
import com.raf.imperial.jpa.domain.card.Deployment;
import com.raf.imperial.jpa.domain.card.Item;
import com.raf.imperial.jpa.domain.model.Ability;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.DiceSide;
import com.raf.imperial.jpa.domain.model.KeyWord;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.rule.DamageRule;
import com.raf.imperial.rule.model.DamageValue;
import com.raf.imperial.rule.model.Damages;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule for calculing damages.
 * 
 * @author RAF
 */
@Slf4j
@NoArgsConstructor
@Service
public class DamageRuleImpl implements DamageRule {

  /** The Arsenal ability name. */
  private static final String ARSENAL = "Arsenal";

  /** The Epic Arsenal ability name. */
  private static final String EPIC_ARSENAL = "Epic Arsenal";

  /** The Accuracy keyword. */
  private static final String ACCURACY = "Accuracy";

  /** The Pierce keyword. */
  private static final String PIERCE = "Pierce";

  /** The Damage keyword. */
  private static final String DAMAGE = "Damage";

  /** The Surge keyword. */
  private static final String SURGE = "Surge";

  /** The deployment DAO. */
  @Resource
  private DeploymentDao deploymentDao;

  /** The item DAO. */
  @Resource
  private ItemDao itemDao;

  /** The dice DAO. */
  @Resource
  private transient DiceDao diceDao;

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @return the damages
   * @see DamageRule#getDamages(Deployment)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Deployment deployment) {
    return getDamages(deployment, false, 0);
  }

  /**
   * Calcul and returns the damages for a deployment card and a minimum accuracy.
   * 
   * @param deployment
   *          the deployment card
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   * @see DamageRule#getDamages(Deployment, int)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Deployment deployment, final int accuracy) {
    return getDamages(deployment, false, accuracy);
  }

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @return the damages
   * @see DamageRule#getDamages(Deployment, boolean)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Deployment deployment, final boolean focus) {
    return getDamages(deployment, focus, 0);
  }

  /**
   * Calcul and returns the damages for a deployment card.
   * 
   * @param deployment
   *          the deployment card
   * @param focus
   *          the focus indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   * @see DamageRule#getDamages(Deployment, boolean, int)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Deployment deployment, final boolean focus, final int accuracy) {
    log.info("Calculing damages for deployment {}", deployment);
    final Damages damages;
    final AttackTypeEnum attackType = deployment.getAttackType();
    if (AttackTypeEnum.NONE.equals(attackType)) {
      damages = new Damages();
      damages.setValues(new ArrayList<>());
    } else {
      final boolean ranged = AttackTypeEnum.RANGED.equals(attackType);
      final List<Dice> dices = this.deploymentDao.getAttack(deployment, focus);
      replaceUndefinedDices(dices, deployment.getAbilities(), focus);
      final List<Capacity> capacities = this.deploymentDao.getCapacities(deployment);
      damages = getDamages(dices, capacities, ranged, accuracy);
    }
    return damages;
  }

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @return the damages
   * @see DamageRule#getDamages(Item)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Item item) {
    return getDamages(item, false, 0);
  }

  /**
   * Calcul and returns the damages for a item card and a minimum accuracy.
   * 
   * @param item
   *          the item card
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   * @see DamageRule#getDamages(Item, int)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Item item, final int accuracy) {
    return getDamages(item, false, accuracy);
  }

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @return the damages
   * @see DamageRule#getDamages(Item, boolean)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Item item, final boolean focus) {
    return getDamages(item, focus, 0);
  }

  /**
   * Calcul and returns the damages for a item card.
   * 
   * @param item
   *          the item card
   * @param focus
   *          the focus indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   * @see DamageRule#getDamages(Item, boolean, int)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final Item item, final boolean focus, final int accuracy) {
    log.info("Calculing damages for item {}", item);
    final Damages damages;
    final AttackTypeEnum attackType = item.getAttackType();
    if (AttackTypeEnum.NONE.equals(attackType)) {
      damages = new Damages();
      damages.setValues(new ArrayList<>());
    } else {
      final boolean ranged = AttackTypeEnum.RANGED.equals(attackType);
      final List<Dice> dices = this.itemDao.getAttack(item, focus);
      final List<Capacity> capacities = this.itemDao.getCapacities(item);
      damages = getDamages(dices, capacities, ranged, accuracy);
    }
    return damages;
  }


  /**
   * Calcul and returns the damages for an attack.
   * 
   * @param dices
   *          the list of attack dices
   * @param capacities
   *          the list of capacities
   * @param ranged
   *          the ranged indicator
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   * @see DamageRule#getDamages(List, List, boolean, int)
   */
  @Override
  @Transactional
  @Loggable
  public Damages getDamages(final List<Dice> dices, final List<Capacity> capacities, final boolean ranged,
      final int accuracy) {
    final List<DamageValue> damages = new ArrayList<>(capacities.size());
    int extraSurges = 0;
    for (final Capacity capacity : capacities) {
      extraSurges += initCapacities(capacity, ranged, damages);
    }
    return calcul(dices, damages, ranged, extraSurges, accuracy);

  }

  /**
   * Overload the list of dice with special abilities.
   * 
   * @param dices
   *          the list of dices
   * @param abilities
   *          the list of abilities
   */
  private void replaceUndefinedDices(final List<Dice> dices, final List<Ability> abilities, final boolean focus) {
    for (final Ability ability : abilities) {
      if (EPIC_ARSENAL.equals(ability.getName())) {
        dices.clear();
        dices.add(diceDao.getById("Blue"));
        dices.add(diceDao.getById("Red"));
        dices.add(diceDao.getById("Red"));
        if (focus) {
          dices.add(diceDao.getById("Green"));
        }
      } else if (ARSENAL.equals(ability.getName())) {
        dices.clear();
        dices.add(diceDao.getById("Blue"));
        dices.add(diceDao.getById("Red"));
        if (focus) {
          dices.add(diceDao.getById("Green"));
        }
      }
    }
  }

  /**
   * Initialize the lists of surges.
   * 
   * @param Capacity
   *          the capacity
   * @param ranged
   *          the ranged indicator
   * @param damages
   *          the list of damages surges
   * @return the extra surges count
   */
  private int initCapacities(final Capacity capacity, final boolean ranged, final List<DamageValue> damages) {
    int extraSurge = 0;
    DamageValue value = null;
    final Integer surge = capacity.getSurge();
    final KeyWord keyWord1 = capacity.getKey1();
    if (keyWord1 != null) {
      final Integer value1 = capacity.getValue1();
      if (ranged && ACCURACY.equals(keyWord1.getName())) {
        log.debug("Adding accuracy {} surge {}", value1, surge);
        value = createDamageValue(null, value1, null, surge);
      } else if (PIERCE.equals(keyWord1.getName())) {
        log.debug("Adding pierce {} surge {}", value1, surge);
        value = createDamageValue(null, null, value1, surge);
      } else if (DAMAGE.equals(keyWord1.getName())) {
        log.debug("Adding damage {} surge {}", value1, surge);
        value = createDamageValue(value1, null, null, surge);
      } else if (SURGE.equals(keyWord1.getName()) && value1 != null) {
        log.debug("Extra surge found {}", value1);
        extraSurge = value1.intValue();
      }
      final KeyWord keyWord2 = capacity.getKey2();
      if (keyWord2 != null) {
        final Integer value2 = capacity.getValue2();
        if (ranged && ACCURACY.equals(keyWord2.getName())) {
          log.debug("Adding accuracy {}", value2);
          addDamageValue(value, null, value1, null);
        } else if (PIERCE.equals(keyWord2.getName())) {
          log.debug("Adding pierce {}", value2);
          addDamageValue(value, null, null, value1);
        } else if (DAMAGE.equals(keyWord2.getName())) {
          log.debug("Adding damage {}", value2);
          addDamageValue(value, value2, null, null);
        } else if (SURGE.equals(keyWord2.getName()) && value2 != null) {
          log.debug("Extra surge found {}", value2);
          extraSurge += value2.intValue();
        }
      }
    }
    if (value != null) {
      damages.add(value);
    }
    return extraSurge;
  }

  /**
   * Create a surge damage value.
   * 
   * @param damage
   *          the damage value
   * @param accuracy
   *          the accuracy value
   * @param pierce
   *          the pierce value
   * @param surge
   *          the surge value
   * @return the damage value
   */
  private DamageValue createDamageValue(final Integer damage, final Integer accuracy, final Integer pierce,
      final Integer surge) {
    final DamageValue damageValue = new DamageValue();
    if (damage != null) {
      damageValue.setDamage(damage.intValue());
    }
    if (accuracy != null) {
      damageValue.setAccuracy(accuracy.intValue());
    }
    if (surge != null) {
      damageValue.setSurge(surge.intValue());
    }
    if (pierce != null) {
      damageValue.setPierce(pierce.intValue());
    }
    return damageValue;
  }

  /**
   * Add damage value.
   * 
   * @param damage
   *          the damage value
   * @param accuracy
   *          the accuracy value
   * @param surge
   *          the surge value
   * @param pierce
   *          the pierce value
   */
  private void addDamageValue(final DamageValue damageValue, final Integer damage, final Integer accuracy,
      final Integer pierce) {
    if (damage != null) {
      damageValue.setDamage(damageValue.getDamage() + damage.intValue());
    }
    if (accuracy != null) {
      damageValue.setAccuracy(damageValue.getAccuracy() + accuracy.intValue());
    }
    if (pierce != null) {
      damageValue.setPierce(damageValue.getPierce() + pierce.intValue());
    }
  }

  /**
   * Calculates the damages from list of dices and list of surges.
   * 
   * @param dices
   *          the list of dices
   * @param damageCaps
   *          the list of damages capacities
   * @param ranged
   *          the ranged indicator
   * @param extraSurges
   *          the extra surges count
   * @param accuracy
   *          the minimum accuracy
   * @return the damages
   */
  private Damages calcul(final List<Dice> dices, final List<DamageValue> damageCaps, final boolean ranged,
      final int extraSurges, final int accuracy) {
    final Dice dice = dices.get(0);
    String diceName;
    DamageValue damageCapacity;
    List<Dice> subDices;
    final List<DamageValue> damages = new ArrayList<>((int) Math.pow(6, dices.size()));
    for (final DiceSide diceSide : dice.getDiceSides()) {
      diceName = dice.getName() + '-' + diceSide.getSide();
      damageCapacity = getSideValue(diceSide, extraSurges);
      damageCapacity.setDicesNames(diceName);
      subDices = dices.subList(1, dices.size());
      if (subDices.isEmpty()) {
        log.debug("Adding damages {} for dice {}", damageCapacity, diceName);
        damages.add(damageCapacity);
      } else {
        damages.addAll(calculDices(subDices, damageCapacity, diceName));
      }

    }
    final List<DamageValue> damagesTemp = new ArrayList<>(damageCaps.size());
    for (final DamageValue damageValue : damages) {
      damagesTemp.clear();
      damagesTemp.addAll(damageCaps);
      calculEvade(damageValue);
      if (ranged) {
        log.debug("Calculing surges Accuracy {}", damageValue);
        calculAccuracyCapacity(damageValue, damagesTemp, accuracy);
      }
      log.debug("Calculing surges Pierce {}", damageValue);
      calculPierceCapacity(damageValue, damagesTemp);
      log.debug("Calculing surges Damages {}", damageValue);
      calculDamageCapacity(damageValue, damagesTemp, accuracy);
      if (damageValue.isDodge()) {
        damageValue.setDamage(0);
        damageValue.setPierce(0);
      }
      log.debug("Calculed damages {} ", damageValue);
    }
    final Damages result = new Damages();
    result.setAccuracy(accuracy);
    result.setRanged(ranged);
    result.setValues(damages);
    return result;
  }

  /**
   * Calculates the augments of damages from the dices.
   * 
   * @param dices
   *          the list of dices
   * @param damageCapPrev
   *          the previous damages
   * @param diceNames
   *          the dice names
   * @return the list of damages
   */
  private List<DamageValue> calculDices(final List<Dice> dices, final DamageValue damageCapPrev,
      final String diceNames) {
    final Dice dice = dices.get(0);
    String diceName;
    DamageValue damageCapacity;
    List<Dice> subDices;
    final List<DamageValue> damages = new ArrayList<>();
    for (final DiceSide diceSide : dice.getDiceSides()) {
      diceName = diceNames + ' ' + dice.getName() + '-' + diceSide.getSide();
      damageCapacity = getSideValue(diceSide, damageCapPrev);
      damageCapacity.setDicesNames(diceName);
      subDices = dices.subList(1, dices.size());
      if (subDices.isEmpty()) {
        log.debug("Adding damages {} for dice {}", damageCapacity, diceName);
        damages.add(damageCapacity);
      } else {
        damages.addAll(calculDices(subDices, damageCapacity, diceName));
      }
    }
    return damages;
  }

  /**
   * Returns the side values for damages and surges.
   * 
   * @param diceSide
   *          the dice side
   * @param extraSurges
   *          the extra surges
   * @return the side values
   */
  private DamageValue getSideValue(final DiceSide diceSide, final int extraSurges) {
    final DamageValue damageValue = new DamageValue();
    damageValue.setSurge(extraSurges);
    return getSideValue(diceSide, damageValue);
  }

  /**
   * Returns the side values for damages and surges, with previous damages augment.
   * 
   * @param diceSide
   *          the dice side
   * @param damageCapPrev
   *          the previous damages
   * @return the side values
   */
  private DamageValue getSideValue(final DiceSide diceSide, final DamageValue damageCapPrev) {
    final DamageValue damageCapacity = new DamageValue();
    damageCapacity.setDamage(getValue(diceSide.getDamage()) + damageCapPrev.getDamage());
    damageCapacity.setAccuracy(getValue(diceSide.getAccuracy()) + damageCapPrev.getAccuracy());
    damageCapacity.setSurge(getValue(diceSide.getSurge()) + damageCapPrev.getSurge());
    damageCapacity.setBlock(getValue(diceSide.getBlock()) + damageCapPrev.getBlock());
    damageCapacity.setEvade(getValue(diceSide.getEvade()) + damageCapPrev.getEvade());
    if (Boolean.TRUE.equals(diceSide.getDodge())) {
      damageCapacity.setDodge(true);
    }
    return damageCapacity;
  }

  /**
   * Return the value from the integer, or <code>0</code> if null.
   * 
   * @param integer
   *          the integer
   * @return the value from the integer, or <code>0</code> if null
   */
  private int getValue(final Integer integer) {
    final int value;
    if (integer == null) {
      value = 0;
    } else {
      value = integer.intValue();
    }
    return value;
  }

  /**
   * Substract the evade to the surges.
   * 
   * @param damage
   *          the damage value
   */
  private void calculEvade(final DamageValue damage) {
    final int evade = damage.getEvade();
    if (evade > 0) {
      final int surge = damage.getSurge();
      damage.setSurge(Math.max(0, surge - evade));
      damage.setEvade(Math.max(0, evade - surge));
      log.debug("Capacity evade {} ", damage);
    }
  }

  /**
   * Caculate the damage augment from the list of surges.
   * 
   * @param damage
   *          the damage
   * @param damages
   *          the list of damage capacities
   * @param accuracy
   *          the minimum accuracy
   */
  private void calculDamageCapacity(final DamageValue damage, final List<DamageValue> damages, final int accuracy) {
    if (damage.getAccuracy() >= accuracy) {
      final int surgeCount = damage.getSurge();
      int augment = 0;
      int index = -1;
      int cursor = 0;
      for (final DamageValue surge : damages) {
        if (surge.getSurge() <= surgeCount && surge.getDamage() > augment) {
          augment = surge.getDamage();
          index = cursor;
        }
        cursor++;
      }
      if (index >= 0) {
        final DamageValue surge = damages.get(index);
        updateDamage(damage, surge);
        damages.remove(surge);
        log.debug("Capacity damages {} ", damage);
        if (damage.getSurge() > 0 && damages.size() > 1) {
          calculDamageCapacity(damage, damages, accuracy);
        }
      }
      final int dam = damage.getDamage();
      final int block = damage.getBlock();
      damage.setDamage(Math.max(0, dam - block));
      damage.setBlock(Math.max(0, block - dam));
    } else {
      damage.setDamage(0);
    }
  }

  /**
   * Caculate the accuracy augment from the list of surges.
   * 
   * @param damage
   *          the damage
   * @param damages
   *          the list of damages capacities
   * @param accuracy
   *          the minimum accuracy
   */
  private void calculAccuracyCapacity(final DamageValue damage, final List<DamageValue> damages, final int accuracy) {
    final int surgeCount = damage.getSurge();
    int augment = 0;
    int index = -1;
    int cursor = 0;
    for (final DamageValue surge : damages) {
      if (surge.getSurge() <= surgeCount && surge.getAccuracy() > augment
          && (damage.getAccuracy() < accuracy || surge.getSurge() == 0)) {
        augment = surge.getAccuracy();
        index = cursor;
      }
      cursor++;
    }
    if (index >= 0) {
      final DamageValue surge = damages.get(index);
      updateDamage(damage, surge);
      damages.remove(surge);
      log.debug("Capacity accuracy {} ", damage);
      if (damage.getSurge() > 0 && damages.size() > 1) {
        calculAccuracyCapacity(damage, damages, accuracy);
      }
    }
  }

  /**
   * Caculate the accuracy augment from the list of surges.
   * 
   * @param damage
   *          the damage
   * @param damages
   *          the list of damages capacities
   * @param accuracy
   *          the minimum accuracy
   */
  private void calculPierceCapacity(final DamageValue damage, final List<DamageValue> damages) {
    if (damage.getBlock() > 0) {
      final int surgeCount = damage.getSurge();
      int augment = 0;
      int index = -1;
      int cursor = 0;
      for (final DamageValue surge : damages) {
        if (surge.getSurge() <= surgeCount && surge.getPierce() > augment) {
          augment = surge.getPierce();
          index = cursor;
        }
        cursor++;
      }
      if (index >= 0) {
        final DamageValue surge = damages.get(index);
        updateDamage(damage, surge);
        damages.remove(surge);
        log.debug("Capacity pierce {} ", damage);
        if (damage.getSurge() > 0 && damages.size() > 1) {
          calculPierceCapacity(damage, damages);
        }
      }
    }
  }

  private void updateDamage(final DamageValue damage, final DamageValue surge) {
    damage.setDamage(damage.getDamage() + surge.getDamage());
    damage.setAccuracy(damage.getAccuracy() + surge.getAccuracy());
    damage.setBlock(Math.max(0, damage.getBlock() - surge.getPierce()));
    damage.setSurge(damage.getSurge() - surge.getSurge());
  }

}
