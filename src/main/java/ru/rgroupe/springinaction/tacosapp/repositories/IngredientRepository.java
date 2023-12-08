package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
