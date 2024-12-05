package com.desarrollo.appposteos.model;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String fotoperfil;
    private String[] intereses;

    public User(){}
    public User(String username, String email, String password, String fotoperfil){
        this.username = username;
        this.email = email;
        this.password = password;
        this.fotoperfil = fotoperfil;
    }
    public User(String username, String email, String password, String fotoperfil, String[] intereses){
        this.username = username;
        this.email = email;
        this.password = password;
        this.fotoperfil = fotoperfil;
        this.intereses = intereses;
    }
    public User(String id, String username, String email, String password, String fotoperfil){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fotoperfil = fotoperfil;
    }
    public User(String id, String username, String email, String password, String fotoperfil, String[] intereses){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fotoperfil = fotoperfil;
        this.intereses = intereses;
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String[] getIntereses() {
        return intereses;
    }

    public void setIntereses(String[] intereses) {
        this.intereses = intereses;
    }
}
