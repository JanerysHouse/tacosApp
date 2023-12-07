package ru.rgroupe.springinaction.tacosapp.repositories;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.rgroupe.springinaction.tacosapp.entities.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Repository
public class IngredientRepositoryImpl implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {

        String sql = "SELECT id,name,type FROM Ingredient";
        return jdbcTemplate.query(sql,
                this::rowMapToIngredient);
    }

    private Ingredient rowMapToIngredient(ResultSet rowMap, int iRow) throws SQLException {

        return new Ingredient(
                rowMap.getString("id"),
                rowMap.getString("name"),
                Ingredient.Type.valueOf(rowMap.getString("type")));

    }

    @Override
    public Optional<Ingredient> findById(String id) {

        String sql = "SELECT id, name, type FROM Ingredient WHERE id = ?";
        List<Ingredient> result =
                jdbcTemplate.query(sql, this::rowMapToIngredient, id);
        return result.size() == 0 ?
                Optional.empty() :
                Optional.of(result.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {

        String sql = "INSERT INTO Ingredient (id, name, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                        ingredient.getId(),
                        ingredient.getName(),
                        ingredient.getType());

        return ingredient;
    }
}
