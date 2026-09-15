package ru.unlegit.techstore.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.PasswordResetToken;
import ru.unlegit.techstore.model.User;

import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    void deleteByUser(User user);

    Optional<PasswordResetToken> findByTokenAndUsedFalse(@NotBlank String token);
}