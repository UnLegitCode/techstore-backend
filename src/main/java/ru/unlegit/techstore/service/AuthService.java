package ru.unlegit.techstore.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unlegit.techstore.dto.auth.*;
import ru.unlegit.techstore.exception.user.EmailAlreadyExistsException;
import ru.unlegit.techstore.exception.user.InvalidCredentialsException;
import ru.unlegit.techstore.exception.user.InvalidOrExpiredTokenException;
import ru.unlegit.techstore.model.PasswordResetToken;
import ru.unlegit.techstore.model.User;
import ru.unlegit.techstore.repository.PasswordResetTokenRepository;
import ru.unlegit.techstore.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    PasswordResetTokenRepository resetTokenRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    private static final long RESET_TOKEN_TTL_MINUTES = 30;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved);

        return new AuthResponse(saved.getId(), saved.getEmail(), token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), user.getEmail(), token);
    }

    @Transactional
    public String requestPasswordReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        resetTokenRepository.deleteByUser(user);

        String rawToken = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(rawToken);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(RESET_TOKEN_TTL_MINUTES));
        resetTokenRepository.save(resetToken);

        log.debug("Password reset token for {}: {}", user.getEmail(), rawToken);

        return rawToken;
    }

    @Transactional
    public void confirmPasswordReset(PasswordResetConfirmRequest request) {
        PasswordResetToken resetToken = resetTokenRepository.findByTokenAndUsedFalse(request.token())
                .orElseThrow(InvalidOrExpiredTokenException::new);

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidOrExpiredTokenException();
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        resetTokenRepository.save(resetToken);
    }
}