package dev.karan.subscriptionbillingplatform.auth.repository;

import dev.karan.subscriptionbillingplatform.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository 
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

}
