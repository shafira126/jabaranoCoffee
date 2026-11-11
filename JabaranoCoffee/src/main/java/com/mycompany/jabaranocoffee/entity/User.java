/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.entity;

import java.time.LocalDateTime;

/**
 *
 * @author kayla
 */

/*
 * Merepresentasikan data akun staff Jabarano Coffee.
 */

public class User {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private int           id;
    private String        username;
    private String        password;
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public User() {}

    public User(int id, String username, String password, LocalDateTime createdAt) {
        this.id        = id;
        this.username  = username;
        this.password  = password;
        this.createdAt = createdAt;
    }

    // ----------------------------------------------------------------
    // Getter
    // ----------------------------------------------------------------
    public int getId()                  { return id; }
    public String getUsername()         { return username; }
    public String getPassword()         { return password; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ----------------------------------------------------------------
    // Setter
    // ----------------------------------------------------------------
    public void setId(int id)                          { this.id = id; }
    public void setUsername(String username)            { this.username = username; }
    public void setPassword(String password)            { this.password = password; }
    public void setCreatedAt(LocalDateTime createdAt)  { this.createdAt = createdAt; }

    // ----------------------------------------------------------------
    // Output
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}
