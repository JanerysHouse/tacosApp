package ru.rgroupe.springinaction.tacosapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;
import ru.rgroupe.springinaction.tacosapp.repositories.IngredientRepository;


@Component
@RequiredArgsConstructor
public class ConverterIngredientsById implements Converter<String, Ingredient> {

    private final IngredientRepository repository;

    @Override
    public Ingredient convert(String id) {
        return repository.findById(id).orElse(null);
    }
}
