package com.raf.imperial.jpa.domain.card;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.raf.fwk.jpa.domain.AbstractIdEntity;
import com.raf.imperial.jpa.Constant;
import com.raf.imperial.jpa.domain.model.Ability;
import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Faction;
import com.raf.imperial.jpa.domain.model.Trait;
import com.raf.imperial.jpa.enums.AttackTypeConverter;
import com.raf.imperial.jpa.enums.AttackTypeEnum;
import com.raf.imperial.jpa.enums.ModeConverter;
import com.raf.imperial.jpa.enums.ModeEnum;
import com.raf.imperial.jpa.enums.SizeConverter;
import com.raf.imperial.jpa.enums.SizeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the DEPLOYMENT database table.
 *
 * @author RAF
 */
@Entity
@Table(name = "DEPLOYMENT", schema = Constant.SCHEMA)
@Getter
@Setter
@NoArgsConstructor
public class Deployment extends AbstractIdEntity implements CardEntity {

  /** Serial UID. */
  private static final long serialVersionUID = 5373728016376065491L;

  /** The name. */
  @Column(name = "NAME", length = 30, nullable = false)
  private String name;

  /** The name code. */
  @Column(name = "NAME_CODE", length = 40, nullable = false)
  private String nameCode;

  /** The title code. */
  @Column(name = "TITLE_CODE", length = 40)
  private String titleCode;

  /** The Elite indicator. */
  @Column(name = "ELITE", nullable = false)
  private boolean elite;

  /** The Unique indicator. */
  @Column(name = "UNIQUENESS", nullable = false)
  private boolean unique;

  /** The mode. */
  @Convert(converter = ModeConverter.class)
  @Column(name = "MODE", length = 10, nullable = false)
  private ModeEnum mode;

  /** The faction name. */
  @Column(name = "FACTION", length = 30, nullable = false)
  private String factionName;

  /** The faction. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FACTION", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_FACTION"))
  private Faction faction;

  /** The group figures count. */
  @Column(name = "COUNT", nullable = false, precision = 1)
  private int count;

  /** The deployment cost. */
  @Column(name = "COST", nullable = false, precision = 2)
  private int cost;

  /** The reinforce cost. */
  @Column(name = "REINFORCE", precision = 1)
  private Integer reinforce;

  /** The traits. */
  @ManyToMany
  @JoinTable(name = "DEPLOYMENT_TRAIT", schema = Constant.SCHEMA, joinColumns = {
      @JoinColumn(name = "DEPLOYMENT_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_TRAIT_DEPLOYMENT")) }, inverseJoinColumns = {
          @JoinColumn(name = "TRAIT_NAME", referencedColumnName = "NAME", foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_TRAIT_TRAIT")) })
  private List<Trait> traits;

  /** The figure Size. */
  @Convert(converter = SizeConverter.class)
  @Column(name = "SIZE", nullable = false, length = 8)
  private SizeEnum size;

  /** The embedded capacities. */
  @ElementCollection
  @CollectionTable(name = "DEPLOYMENT_CAPACITY", schema = Constant.SCHEMA, joinColumns = {
      @JoinColumn(name = "DEPLOYMENT_ID", referencedColumnName = "ID") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_DEPLOYMENT_CAPACITY", columnNames = { "DEPLOYMENT_ID",
              "RANK" }) }, foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_CAPACITY_DEPLOYMENT"))
  @OrderBy("rank")
  private List<EmbedCapacity> embedCapacities;

  /** The capacities. */
  @Transient
  private List<Capacity> capacities;

  /** The abilities. */
  @ManyToMany
  @JoinTable(name = "DEPLOYMENT_ABILITY", schema = Constant.SCHEMA, joinColumns = {
      @JoinColumn(name = "DEPLOYMENT_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_ABILITY_DEPLOYMENT")) }, inverseJoinColumns = {
          @JoinColumn(name = "ABILITY_NAME", referencedColumnName = "NAME", foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_ABILITY_ABILITY")) })
  private List<Ability> abilities;

  /** The health. */
  @Column(name = "HEALTH", nullable = false, precision = 2)
  private int health;

  /** The speed. */
  @Column(name = "SPEED", nullable = false, precision = 1)
  private int speed;

  /** The embedded defense pool dices. */
  @ElementCollection
  @CollectionTable(name = "DEPLOYMENT_DEFENSE", schema = Constant.SCHEMA, joinColumns = {
      @JoinColumn(name = "DEPLOYMENT_ID", referencedColumnName = "ID") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_DEPLOYMENT_DEFENSE", columnNames = { "DEPLOYMENT_ID",
              "RANK" }) }, foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_DEFENSE_DEPLOYMENT"))
  private List<EmbedDice> embedDefenses;

  /** The defense pool dices. */
  @Transient
  private List<Dice> defenses;

  /** The attack type. */
  @Convert(converter = AttackTypeConverter.class)
  @Column(name = "ATTACK_TYPE", nullable = false, length = 6)
  private AttackTypeEnum attackType;

  /** The embedded attack pool dices. */
  @ElementCollection
  @CollectionTable(name = "DEPLOYMENT_ATTACK", schema = Constant.SCHEMA, joinColumns = {
      @JoinColumn(name = "DEPLOYMENT_ID", referencedColumnName = "ID") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_DEPLOYMENT_ATTACK", columnNames = { "DEPLOYMENT_ID",
              "RANK" }) }, foreignKey = @ForeignKey(name = "FK_DEPLOYMENT_ATTACK_DEPLOYMENT"))
  private List<EmbedDice> embedAttacks;

  /** The attack pool dices. */
  @Transient
  private List<Dice> attacks;

  /**
   * Build the lists.
   */
  @PostLoad
  public void postLoad() {
    this.capacities = new ArrayList<>(this.embedCapacities.size());
    for (final EmbedCapacity embedCapacity : this.embedCapacities) {
      this.capacities.add(embedCapacity.getCapacity());
    }
    this.defenses = new ArrayList<>(this.embedDefenses.size());
    for (final EmbedDice embedDice : this.embedDefenses) {
      this.defenses.add(embedDice.getDice());
    }
    this.attacks = new ArrayList<>(this.embedAttacks.size());
    for (final EmbedDice embedDice : this.embedAttacks) {
      this.attacks.add(embedDice.getDice());
    }
  }

  /**
   * Append the properties for the to string builder.
   * 
   * @param builder
   *          the builder
   * @see AbstractIdEntity#appendId(ToStringBuilder)
   */
  @Override
  protected void appendId(final ToStringBuilder builder) {
    builder.append("name", this.name).append("nameCode", this.nameCode).append("titleCode", this.titleCode)
        .append("elite", this.elite).append("unique", this.unique).append("mode", this.mode);
    if (this.faction != null && Faction.class.equals(this.faction.getClass())) {
      builder.append("faction", this.faction);
    }
    builder.append("count", this.count).append("cost", this.cost).append("reinforce", this.reinforce)
        .append("traits", this.traits).append("size", this.size).append("capacities", this.capacities)
        .append("abilities", this.abilities).append("health", this.health).append("speed", this.speed)
        .append("defenses", this.defenses).append("attackType", this.attackType).append("attacks", this.attacks);
  }

}
