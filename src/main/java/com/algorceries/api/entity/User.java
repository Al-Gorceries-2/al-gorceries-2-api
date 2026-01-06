package com.algorceries.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false
    )
    private String id;

    @Column(
        name = "provider",
        nullable = false
    )
    private String provider; // e.g., GOOGLE, GITHUB

    @Column(
        name = "email",
        nullable = false
    )
    private String email;

    @Column(
        name = "displayName",
        nullable = false
    )
    private String displayName = "";

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    protected User() {
        // noop
    }

    public User(String provider, String email) {
        this.provider = provider;
        this.email = email;
    }

    public User(String provider, String email, String displayName) {
        this(provider, email);
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
