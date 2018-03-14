package com.raf.imperial.jpa.domain.card;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class Item extends AbstractItem {

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

  /** The capacities. */
  @ElementCollection
  @CollectionTable(name = "ITEM_CAPACITY", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "ITEM_ID") }, indexes = {
          @Index(name = "IDX_ITEM_CAPACITY", columnList = "ITEM_ID, RANK", unique = true) })
  @OrderBy("rank")
  private List<EmbedCapacity> capacities;

  /** The attack pool dices. */
  @ElementCollection
  @CollectionTable(name = "ITEM_ATTACK", schema = "IMPERIAL", joinColumns = {
      @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(name = "FK_ITEM_ATTACK_ITEM")) }, indexes = {
          @Index(name = "IDX_ITEM_ATTACK", columnList = "ITEM_ID, RANK", unique = true) })
  private List<EmbedDice> attacks;

  @Override
  protected void appendItem(final ToStringBuilder builder) {
    builder.append("traits", this.traits).append("cost", this.cost).append("capacities", this.capacities)
        .append("attacks", this.attacks);
  }

}
