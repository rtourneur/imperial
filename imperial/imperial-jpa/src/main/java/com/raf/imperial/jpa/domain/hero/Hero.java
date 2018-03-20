package com.raf.imperial.jpa.domain.hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.enums.AttributEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the HERO database table.
 *
 * @author RAF
 */
@Entity
@Table(name = "HERO", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Hero extends AbstractNamedEntity {

  /** The title code. */
  @Column(name = "TITLE_CODE", length = 40)
  private String titleCode;

  /** The name of the expansion. */
  @Column(name = "EXPANSION", nullable = false, length = 30)
  private String expansionName;

  /** The expansion. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "EXPANSION", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_HERO_EXPANSION"))
  private Expansion expansion;

  /** The statistics. */
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "health", column = @Column(name = "HEALTH", nullable = false, precision = 2)),
      @AttributeOverride(name = "endurance", column = @Column(name = "ENDURANCE", nullable = false, precision = 1)),
      @AttributeOverride(name = "speed", column = @Column(name = "SPEED", nullable = false, precision = 1)) })
  private HeroStat healthyStat;

  /** The Dice. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "DICE_NAME", referencedColumnName = "NAME", foreignKey = @ForeignKey(name = "FK_HERO_DICE"))
  private Dice healthyDefense;

  /** The wounded statistics. */
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "health", column = @Column(name = "WOUNDED_HEALTH", nullable = false, precision = 2)),
      @AttributeOverride(name = "endurance", column = @Column(name = "WOUNDED_ENDURANCE", nullable = false, precision = 1)),
      @AttributeOverride(name = "speed", column = @Column(name = "WOUNDED_SPEED", nullable = false, precision = 1)) })
  private HeroStat woundedStat;

  /** The Dice. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "WOUNDED_DICE_NAME", referencedColumnName = "NAME", foreignKey = @ForeignKey(name = "FK_HERO_WOUNDED_DICE"))
  private Dice woundedDefense;

  /** The abilities. */
  @OneToMany
  @JoinColumn(name = "HERO_NAME", nullable = false, foreignKey = @ForeignKey(name = "FK_HERO_ABILITY_HERO"))
  private List<HeroAbility> abilities;

  /** The capacities. */
  @ElementCollection
  @CollectionTable(name = "HERO_ATTRIBUT", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "HERO_NAME", referencedColumnName = "NAME") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_HERO_HERO_ATTRIBUT", columnNames = { "HERO_NAME", "RANK", "ATTRIBUT",
              "WOUNDED" }) }, foreignKey = @ForeignKey(name = "FK_HERO_ATTRIBUT_HERO"))
  @OrderBy("rank")
  private List<HeroAttribut> attributs;

  /** The healthy attributes. */
  @Transient
  private transient Map<AttributEnum, List<Dice>> healthAttr;

  /** The wounded attributes. */
  @Transient
  private transient Map<AttributEnum, List<Dice>> woundedAttr;

  /**
   * Build the map of attributes.
   */
  @PostLoad
  public void postLoad() {
    this.healthAttr = new HashMap<>();
    this.woundedAttr = new HashMap<>();
    Map<AttributEnum, List<Dice>> local;
    List<Dice> dices;
    for (final HeroAttribut heroAttribut : attributs) {
      if (heroAttribut.isWounded()) {
        local = this.woundedAttr;
      } else {
        local = this.healthAttr;
      }
      dices = local.get(heroAttribut.getAttribut());
      if (dices == null) {
        dices = new ArrayList<>();
        local.put(heroAttribut.getAttribut(), dices);
      }
      dices.add(heroAttribut.getDice());
    }
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractNamedEntity#appendNamed(ToStringBuilder)
   */
  @Override
  protected void appendNamed(final ToStringBuilder builder) {
    builder.append("titleCode", this.titleCode);
    if (this.expansion != null && Expansion.class.equals(this.expansion.getClass())) {
      builder.append("expansion", this.expansion);
    } else {
      builder.append("expansionName", this.expansionName);
    }
    builder.append("healthyStat", this.healthyStat).append("healthyDefense", this.healthyDefense)
        .append("woundedStat", this.woundedStat).append("woundedDefense", this.woundedDefense)
        .append("abilities", this.abilities).append("healthAttr", this.healthAttr)
        .append("woundedAttr", this.woundedAttr);
  }

}
