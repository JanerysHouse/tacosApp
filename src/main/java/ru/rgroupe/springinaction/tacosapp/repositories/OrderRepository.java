package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;
import ru.rgroupe.springinaction.tacosapp.entities.User;

import java.util.List;


public interface OrderRepository extends CrudRepository<TacosOrder, Long> {

    List<TacosOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
