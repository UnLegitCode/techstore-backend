package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}