package dev.karan.subscriptionbillingplatform.auth.security;

import dev.karan.subscriptionbillingplatform.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUsername(String token);

    Date extractExpiration(String token);

    boolean validateToken(String token, UserDetails userdetails);
}
