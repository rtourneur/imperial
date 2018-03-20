package com.raf.imperial.jpa.domain.hero;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the hero's statistics.
 * 
 * @author RAF
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class HeroStat implements Serializable {

  /** Serial UID. */
  private static final long serialVersionUID = -2754115856461487815L;

  /** The health. */
  @Column
  private int health;

  /** The endurance. */
  @Column
  private int endurance;

  /** The speed. */
  @Column
  private int speed;

  /**
   * Generate the toString.
   *
   * @see Object#toString()
   */
  @Override
  public final String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE);
    builder.append("health", this.health).append("endurance", this.endurance).append("speed", this.speed);
    return builder.toString();
  }

}
