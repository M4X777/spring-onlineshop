package com.example.kr3.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class COrder {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    public CUser owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "goods_in_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id"))
    @JsonIdentityReference(alwaysAsId = true)
    List<CGood> goods = new ArrayList<>();

    public LocalDate date_of_purchase;

    public UUID getId()
    {
        return id;
    }

    public CUser getOwner()
    {
        return owner;
    }
    public void setOwner(CUser owner) { this.owner = owner; }

    public LocalDate getDate_of_purchase() {
        return date_of_purchase;
    }

    public List<CGood> getGoods()
    {
        return goods;
    }
    public void addGood(CGood good) {
        getGoods().add(good);
    }

    public COrder() {
        id = null;
        owner = null;
        goods = new ArrayList<>();
    }

    public COrder(UUID id, CUser owner, CGood good, LocalDate date_of_purchase) {
        this.id = id;
        this.date_of_purchase = date_of_purchase;
        setOwner(owner);
        addGood(good);
    }

    public COrder(UUID id, CUser owner, List<CGood> goods, LocalDate date_of_purchase) {
        this.id = id;
        this.owner = owner;
        this.goods = goods;
        this.date_of_purchase = date_of_purchase;
    }
}