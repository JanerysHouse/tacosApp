package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;


public interface OrderRepository extends CrudRepository<TacosOrder, Long> {

}
