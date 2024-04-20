package fr.ancyracademy.esportsclash.auth.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity<User> {

    @Column
    private String emailAddress;

    @Column
    private String password;

    public User(String id, String emailAddress, String password) {
        super(id);
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User() {

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public User deepClone() {
        return new User(this.id, this.emailAddress, this.password);
    }
}
