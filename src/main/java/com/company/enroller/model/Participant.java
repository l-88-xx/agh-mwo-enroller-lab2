package com.company.enroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "participant")
public class Participant {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    @NotBlank(message = "Password must be not empty")
    @Size(min = 4, max = 100,
            message = "Password must have 4-100 characters")
    private String password;

    @Id
    @NotBlank(message = "Login should be not empty")
    @Size(min = 3, max = 15,
            message = "Login must have 3-15 characters")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
