package org.example.obj;

import org.example.mainMenu;

import java.util.Objects;

public class Usuario {
    private String username;
    private String password;
    private String type;

    public Usuario(String type, String username, String password) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public Usuario(){
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String typeTag;
        if (this.type.equals(mainMenu.TYPE_ADMIN)) {
            typeTag = mainMenu.TYPE_ADMIN_TAG;
        } else {
            typeTag = mainMenu.TYPE_USER_TAG;
        }
        return  typeTag  + mainMenu.DB_TYPEUSER_SEPARATOR + mainMenu.DB_SEPARATOR + username + mainMenu.DB_SEPARATOR + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username) && Objects.equals(password, usuario.password) && Objects.equals(type, usuario.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, type);
    }
}
