package com.raf.imperial.jpa.domain.card;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.imperial.jpa.domain.model.Dice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the deployment's defense dices.
 * 
 * @author RAF
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class EmbedDice implements Serializable {

  /** Serial UID. */
  private static final long serialVersionUID = 2303547322216557166L;

  /** The rank (for unicity). */
  @Column(name = "RANK", nullable = false, precision = 1)
  private int rank;

  /** The Dice. */
  @ManyToOne
  @JoinColumn(name = "DICE_NAME", nullable = false)
  private Dice dice;

  /**
   * Generate the toString.
   *
   * @see Object#toString()
   */
  @Override
  public final String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE);
    builder.append("rank", this.rank).append("dice", this.dice);
    return builder.toString();
  }

}
