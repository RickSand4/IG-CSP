package com.system.proyect.models;

public class Usuario {
    private String username;
    private String role;   // "ESTUDIANTE" o "ADMINISTRADOR"
    private String avatar; // inicial simple para despues ajustar una pp o algo xd

    public Usuario(String username, String role) {
        this.username = username;
        this.role = role;
        this.avatar = username.substring(0,1).toUpperCase();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}