package com.raf.imperial.jpa.domain.card;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
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

import com.raf.imperial.jpa.domain.model.Capacity;
import com.raf.imperial.jpa.domain.model.Dice;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.domain.model.Trait;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the ITEM database table.
 *
 * @author RAF
 */
@Entity
@Table(name = "ITEM", schema = "IMPERIAL")
@Getter
@Setter
@NoArgsConstructor
public class Item extends AbstractItem implements CardEntity {

  /** Serial UID. */
  private static final long serialVersionUID = 487233968219115199L;

  /** The name of the expansion. */
  @Column(name = "EXPANSION", nullable = false, length = 30)
  private String expansionName;

  /** The expansion. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "EXPANSION", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ITEM_EXPANSION"))
  private Expansion expansion;

  /** The traits. */
  @ManyToMany
  @JoinTable(name = "ITEM_TRAIT", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ITEM_TRAIT_ITEM")) }, inverseJoinColumns = {
          @JoinColumn(name = "TRAIT_NAME", referencedColumnName = "NAME", foreignKey = @ForeignKey(name = "FK_ITEM_TRAIT_TRAIT")) })
  private List<Trait> traits;

  /** The cost of the item. */
  @Column(name = "COST", nullable = true, precision = 4)
  private Integer cost;

  /** The embedded capacities. */
  @ElementCollection
  @CollectionTable(name = "ITEM_CAPACITY", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_ITEM_CAPACITY", columnNames = { "ITEM_ID",
              "RANK" }) }, foreignKey = @ForeignKey(name = "FK_ITEM_CAPACITY_ITEM"))
  @OrderBy("rank")
  private List<EmbedCapacity> embedCapacities;

  /** The capacities. */
  @Transient
  private List<Capacity> capacities;

  /** The embedded attack pool dices. */
  @ElementCollection
  @CollectionTable(name = "ITEM_ATTACK", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID") }, uniqueConstraints = {
          @UniqueConstraint(name = "IDX_ITEM_ATTACK", columnNames = { "ITEM_ID",
              "RANK" }) }, foreignKey = @ForeignKey(name = "FK_ITEM_ATTACK_ITEM"))
  @OrderBy("rank")
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
   * @see AbstractItem#appendItem(ToStringBuilder)
   */
  @Override
  protected final void appendItem(final ToStringBuilder builder) {
    if (this.expansion != null && Expansion.class.equals(this.expansion.getClass())) {
      builder.append("expansion", this.expansion);
    } else {
      builder.append("expansionName", this.expansionName);
    }
    builder.append("traits", this.traits).append("cost", this.cost).append("capacities", this.capacities)
        .append("attacks", this.attacks);
  }

}
