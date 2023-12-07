package ru.rgroupe.springinaction.tacosapp.repositories;

import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;

import java.util.Optional;

public interface OrderRepository {

    TacosOrder save(TacosOrder tacosOrder);

}
