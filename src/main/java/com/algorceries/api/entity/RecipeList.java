package com.algorceries.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name = "recipeLists")
public class RecipeList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false
    )
    private String id;

    @CreatedDate
    @Column(
        name = "created",
        nullable = false,
        updatable = false
    )
    private LocalDateTime created = LocalDateTime.now();

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @ManyToMany
    @JoinTable(
        name = "recipe_list_liked_recipes",
        joinColumns = @JoinColumn(name = "recipe_list_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> likedRecipes;

    @ManyToMany
    @JoinTable(
        name = "recipe_list_disliked_recipes",
        joinColumns = @JoinColumn(name = "recipe_list_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> dislikedRecipes;

    @ManyToOne
    @JoinColumn(
        name = "household_id",
        nullable = false
    )
    private Household household;

    protected RecipeList() {
        // noop
    }

    public RecipeList(String name, List<Recipe> likedRecipes, List<Recipe> dislikedRecipes) {
        this.name = name;
        this.likedRecipes = likedRecipes != null ? likedRecipes : new ArrayList<>();
        this.dislikedRecipes = dislikedRecipes != null ? dislikedRecipes : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getLikedRecipes() {
        return likedRecipes;
    }

    public void setLikedRecipes(List<Recipe> likedRecipes) {
        this.likedRecipes = likedRecipes;
    }

    public List<Recipe> getDislikedRecipes() {
        return dislikedRecipes;
    }

    public void setDislikedRecipes(List<Recipe> dislikedRecipes) {
        this.dislikedRecipes = dislikedRecipes;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
