package com.algorceries.api.entity;

import java.util.List;
import com.algorceries.api.entity.converter.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false
    )
    private String id;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @Convert(converter = StringListConverter.class)
    @Column(
        name = "tags",
        nullable = false
    )
    private List<String> tags;

    @ManyToOne
    @JoinColumn(
        name = "household_id",
        nullable = false
    )
    private Household household;

    protected Recipe() {
        // noop
    }

    public Recipe(String name, List<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
