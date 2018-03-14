package com.raf.imperial.rule.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for damages statistics.
 * 
 * @author RAF
 */
@Getter
@Setter
@NoArgsConstructor
public class Damages {

  /** The ranged indicator. */
  private boolean ranged;

  /** The accuracy. */
  private int accuracy;

  /** The damage values. */
  private List<DamageValue> values;

  /**
   * Returns the min damage.
   * 
   * @return the min
   */
  public int getMinDamage() {
    int min = -1;
    for (final DamageValue value : this.values) {
      if (min == -1) {
        min = value.getDamage();
      } else {
        min = Math.min(min, value.getDamage());
      }
    }
    return min;
  }

  /**
   * Returns the max damage.
   * 
   * @return the max
   */
  public int getMaxDamage() {
    int max = 0;
    for (final DamageValue value : this.values) {
      if (max == 0) {
        max = value.getDamage();
      } else {
        max = Math.max(max, value.getDamage());
      }
    }
    return max;
  }

  /**
   * Returns the average damage.
   * 
   * @return the average
   */
  public double getMoyDamage() {
    int moy = 0;
    for (final DamageValue value : this.values) {
      moy += value.getDamage();
    }
    return (double) moy / this.values.size();
  }

  /**
   * Returns the min range.
   * 
   * @return the min
   */
  public int getMinAccuracy() {
    int min = -1;
    for (final DamageValue value : this.values) {
      if (min == -1) {
        min = value.getAccuracy();
      } else {
        min = Math.min(min, value.getAccuracy());
      }
    }
    return min;
  }

  /**
   * Returns the max range.
   * 
   * @return the max
   */
  public int getMaxAccuracy() {
    int max = 0;
    for (final DamageValue value : this.values) {
      if (max == 0) {
        max = value.getAccuracy();
      } else {
        max = Math.max(max, value.getAccuracy());
      }
    }
    return max;
  }

  /**
   * Returns the average range.
   * 
   * @return the average
   */
  public double getMoyAccuracy() {
    int moy = 0;
    for (final DamageValue value : this.values) {
      moy += value.getAccuracy();
    }
    return (double) moy / this.values.size();
  }

  /**
   * Returns the min surge.
   * 
   * @return the min
   */
  public int getMinSurge() {
    int min = -1;
    for (final DamageValue value : this.values) {
      if (min == -1) {
        min = value.getSurge();
      } else {
        min = Math.min(min, value.getSurge());
      }
    }
    return min;
  }

  /**
   * Returns the max surge.
   * 
   * @return the max
   */
  public int getMaxSurge() {
    int max = 0;
    for (final DamageValue value : this.values) {
      if (max == 0) {
        max = value.getSurge();
      } else {
        max = Math.max(max, value.getSurge());
      }
    }
    return max;
  }

  /**
   * Returns the average surge.
   * 
   * @return the average
   */
  public double getMoySurge() {
    int moy = 0;
    for (final DamageValue value : this.values) {
      moy += value.getSurge();
    }
    return (double) moy / this.values.size();
  }

  /**
   * Generate the toString.
   * 
   * @return the toString
   * 
   * @see Object#toString()
   */
  @Override
  public String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this);
    if (this.accuracy > 0) {
      builder.append("Accuracy", this.accuracy);
    }
    builder.append("Damage", getMinDamage() + "-" + getMaxDamage() + "(" + getMoyDamage() + ")");
    if (this.ranged) {
      builder.append("Range", getMinAccuracy() + "-" + getMaxAccuracy() + "(" + getMoyAccuracy() + ")");
    }
    builder.append("Surge", getMinSurge() + "-" + getMaxSurge() + "(" + getMoySurge() + ")");
    builder.append("values", this.values.toArray());
    return builder.toString();
  }
}
