package com.csx.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    private String bio;
    private String profilePicture; // Store URL to the profile image
    private int grandmaster = 0;

    //RELATIONSHIPS
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questionList;


    public User(Long id, String username, String email, String passwordHash, Date createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public User(){}

}
