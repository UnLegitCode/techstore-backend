package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {}