package com.raf.imperial.jpa.domain.card;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The embeddable class for the mission's deployments.
 * 
 * @author RAF
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class MissionDeployment {

  /** The index (for unicity). */
  @Column(name = "INDEX", nullable = false, precision = 1)
  private int index;

  /** The Deployment. */
  @ManyToOne
  @JoinColumn(name = "DEPLOYMENT_ID", nullable = false)
  private Deployment deployment;

  /**
   * Generate the toString.
   *
   * @see Object#toString()
   */
  @Override
  public final String toString() {
    final ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE);
    builder.append("index", this.index);
    if (deployment != null) {
      builder.append("deployment", this.deployment.getName()).append("elite", this.deployment.isElite());
    }
    return builder.toString();
  }

}
