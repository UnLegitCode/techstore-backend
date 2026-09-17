package ru.unlegit.techstore.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;

@UtilityClass
public class AuthenticationUtil {

    public int currentUserId(Authentication authentication) {
        return Integer.parseInt(authentication.getName());
    }
}