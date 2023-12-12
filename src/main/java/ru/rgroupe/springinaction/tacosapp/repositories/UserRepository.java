package ru.rgroupe.springinaction.tacosapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rgroupe.springinaction.tacosapp.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
