package ru.rgroupe.springinaction.tacosapp.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient.*;
import ru.rgroupe.springinaction.tacosapp.entities.Tacos;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;
import ru.rgroupe.springinaction.tacosapp.repositories.IngredientRepository;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class DesignTacosController {

    private final IngredientRepository repository;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = repository.findAll();
        Type [] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacosOrder")
    public TacosOrder order() {
        return new TacosOrder();
    }

    @ModelAttribute(name = "tacos")
    public Tacos tacos() {
        return new Tacos();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String inActionTacos(@Valid Tacos tacos, Errors errors, @ModelAttribute TacosOrder tacosOrder) {

        if (errors.hasErrors()) {
            return "design";
        }
        tacosOrder.addTaco(tacos);
        log.info("Processing tacos:{}", tacos);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(it -> it.getType().equals(type))
                .collect(Collectors.toList());
    }
}
