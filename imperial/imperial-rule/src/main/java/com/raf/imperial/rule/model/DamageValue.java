package com.raf.imperial.rule.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class for damages value statistic.
 * 
 * @author RAF
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DamageValue implements Serializable {

  /** Serial UID. */
  private static final long serialVersionUID = -4097847764031627860L;

  /** The accuracy value. */
  private int accuracy;

  /** The damage value. */
  private int damage;

  /** The surge value. */
  private int surge;

  /** The pierce value. */
  private int pierce;

  /** The block value. */
  private int block;

  /** The evade value. */
  private int evade;

  /** The dodge indicator. */
  private boolean dodge;

  /** The dices names. */
  private String dicesNames;

}
