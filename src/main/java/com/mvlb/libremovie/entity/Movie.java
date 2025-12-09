package com.mvlb.libremovie.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched to Long (Standard practice)

    private String title;
    private String genre;
    private String director;
    private Integer year;

    // ---------------------------------------------
    // 🔗 NEW: Relationship to User
    // ---------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This creates the foreign key column in the database
    private User user;

    // ---------------------------------------------
    // GETTERS AND SETTERS
    // ---------------------------------------------

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    // New Getters/Setters for User
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}