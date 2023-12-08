package ru.rgroupe.springinaction.tacosapp.entities;

import jakarta.persistence.*;
import lombok.Data;


import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Tacos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date createAt = new Date();

    @NotNull
    private String name;

    @NotNull
    @Size(min = 1 , message = "You must choose at least 1 ingredient")
    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
