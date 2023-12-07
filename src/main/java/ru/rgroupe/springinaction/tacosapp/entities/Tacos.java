package ru.rgroupe.springinaction.tacosapp.entities;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class Tacos {

    private long id;
    private Date createAt = new Date();

    @NotNull
    private String name;

    @NotNull
    @Size(min = 1 , message = "You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients;
}
