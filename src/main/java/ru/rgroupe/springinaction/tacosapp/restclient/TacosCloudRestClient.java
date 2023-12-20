package ru.rgroupe.springinaction.tacosapp.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.relational.core.sql.In;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TacosCloudRestClient {

    private final RestTemplate restTemplate;
    private final Traverson traverson;

    public Ingredient getIngredientById(String ingredientId) {
        return restTemplate.getForObject(
                "http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                ingredientId);
    }

    public List<Ingredient> getAllIngredients() {
        return restTemplate.exchange(
                "http://localhost:8080/ingredients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Ingredient>>() {})
                .getBody();
    }

    public void updateIngredients(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/ingredients/{id}",
                ingredient, Ingredient.class);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }

}
