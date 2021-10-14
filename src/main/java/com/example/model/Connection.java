package com.example.model;

import javax.persistence.*;

@Entity
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONNECTION_SEQ")
    @SequenceGenerator(name = "CONNECTION_SEQ", sequenceName = "CONNECTION_SEQ", allocationSize = 1)
    Integer id;
    @ManyToOne
    User user1;
    @ManyToOne
    User user2;

    public Connection(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Connection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
