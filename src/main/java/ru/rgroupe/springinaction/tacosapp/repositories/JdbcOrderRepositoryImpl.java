package ru.rgroupe.springinaction.tacosapp.repositories;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rgroupe.springinaction.tacosapp.entities.IngredientRef;
import ru.rgroupe.springinaction.tacosapp.entities.Tacos;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Repository
public class JdbcOrderRepositoryImpl implements OrderRepository {

    private  JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacosOrder save(TacosOrder tacosOrder) {

        String sql =
                "INSERT INTO Taco_Order" +
                "(delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, cc_number,"
                        + " cc_expiration," +
                "cc_cvv, placed_at) + VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        var psFactory = new PreparedStatementCreatorFactory(
                sql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP);

        psFactory.setReturnGeneratedKeys(true);
        tacosOrder.setPlacedAt(new Date());

        var psCreator = psFactory.newPreparedStatementCreator(
                Arrays.asList(
                        tacosOrder.getDeliveryName(),
                        tacosOrder.getDeliveryStreet(),
                        tacosOrder.getDeliveryCity(),
                        tacosOrder.getDeliveryState(),
                        tacosOrder.getDeliveryZip(),
                        tacosOrder.getCcNumber(),
                        tacosOrder.getCcExpiration(),
                        tacosOrder.getCcCVV(),
                        tacosOrder.getPlacedAt()));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psCreator, keyHolder);
        var orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        tacosOrder.setId(orderId);

        List<Tacos> tacosList = tacosOrder.getTacos();
        int i = 0;
        for (Tacos tacos : tacosList) {
            saveTacos(orderId, i++, tacos);
        }
        return tacosOrder;

    }

    private long saveTacos(Long orderId, int orderKey, Tacos tacos) {
        tacos.setCreateAt(new Date());
        var psFactory = new PreparedStatementCreatorFactory(
                "insert into Tacos (name, created_at, tacos_order, tacos_order_key) " +
                        "values (?,?,?,?)",
                Types.VARCHAR, Types.TIMESTAMP, Types.LONGNVARCHAR, Types.LONGNVARCHAR
        );

        psFactory.setReturnGeneratedKeys(true);

        var psCreator = psFactory.newPreparedStatementCreator(
                Arrays.asList(
                        tacos.getName(),
                        tacos.getCreateAt(),
                        orderId,
                        orderKey));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psCreator, keyHolder);
        var tacosId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        tacos.setId(tacosId);

        saveIngredientsRefs(tacosId, tacos.getIngredients());

        return tacosId;

    }

    private void saveIngredientsRefs(long tacosId, List<IngredientRef> ingredientsRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientsRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, tacos, tacos_key) " +
                            "values (?,?,?,?)",
                    ingredientRef.getIngredient(), tacosId, key++);
        }
    }
}
