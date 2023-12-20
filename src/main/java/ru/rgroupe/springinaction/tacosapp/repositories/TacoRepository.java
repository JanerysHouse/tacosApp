package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rgroupe.springinaction.tacosapp.entities.Tacos;

public interface TacoRepository extends PagingAndSortingRepository<Tacos, Long> {
}
