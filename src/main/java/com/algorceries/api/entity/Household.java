package com.algorceries.api.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "households")
public class Household {

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

    @OneToMany(mappedBy = "household")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "household")
    private List<Recipe> recipes = new ArrayList<>();

    public Household() {
        // noop
    }

    public Household(String name) {
        this.name = name;
    }

    // Constructor to use whilst having one global household
    public Household(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<User> getUsers() {
        return users;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
