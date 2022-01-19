package com.example.kr3.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "goods")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CGood {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;
    public UUID getId() { return id; }

    @Column(name = "name")
    public String name;

    @Column(name = "category")
    public String category;

    @Column(name = "cost_of_good")
    public double cost_of_good;

    @ManyToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    public List<COrder> orders;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCost_of_good() {
        return cost_of_good;
    }

    public CGood() {
        id = UUID.randomUUID();
        name = "";
        orders = new ArrayList<>();
    }

    public CGood(UUID id, String name, double cost_of_good, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.cost_of_good = cost_of_good;
        orders = new ArrayList<>();
    }
}
