package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
