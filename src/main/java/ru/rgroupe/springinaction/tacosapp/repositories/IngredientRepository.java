package ru.rgroupe.springinaction.tacosapp.repositories;

import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
