package com.example.GestionStages.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{
    public Admin(String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
    }
}
