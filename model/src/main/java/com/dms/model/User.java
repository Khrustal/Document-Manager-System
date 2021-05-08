package com.dms.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private boolean admin;

    @OneToMany
    private List<Storable> reader;

    @OneToMany
    private List<Storable> editor;

    @OneToMany
    private List<Storable> moderator;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Storable> getReader() {
        return reader;
    }

    public void addReader(Storable storable) {
        this.reader.add(storable);
    }

    public List<Storable> getEditor() {
        return editor;
    }

    public void addEditor(Storable storable) {
        this.editor.add(storable);
    }

    public List<Storable> getModerator() {
        return moderator;
    }

    public void addModerator(Storable storable) {
        this.moderator.add(storable);
    }
}
