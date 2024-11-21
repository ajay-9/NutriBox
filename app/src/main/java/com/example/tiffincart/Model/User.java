package com.example.tiffincart.Model;

public class User {

    //request Fields
    String username, email, password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //response Fields
    public String _id;
    public String createdAt;
    public String updatedAt;
    public int __v;

}
