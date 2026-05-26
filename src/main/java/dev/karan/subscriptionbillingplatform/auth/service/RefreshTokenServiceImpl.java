package dev.karan.subscriptionbillingplatform.auth.service;

import dev.karan.subscriptionbillingplatform.auth.entity.RefreshToken;
import dev.karan.subscriptionbillingplatform.auth.entity.User;
import dev.karan.subscriptionbillingplatform.auth.repository.RefreshTokenRepository;
import dev.karan.subscriptionbillingplatform.auth.security.JwtService;
import dev.karan.subscriptionbillingplatform.common.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(User user) {

        String refreshJwt = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshJwt)
                .expiryDate(jwtService.extractExpiration(refreshJwt)
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime())
                .user(user)
                .createdAt(LocalDateTime.now())
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken validateRefreshToken(String token) {

        if (!jwtService.validateRefreshToken(token)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                       new InvalidRefreshTokenException("Refresh token not found"));

        if (refreshToken.isRevoked()){
            throw new InvalidRefreshTokenException("Refresh token revoked");
        }

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {
       refreshTokenRepository.findByToken(token)
               .ifPresent(refreshToken -> {
                   refreshToken.setRevoked(true);
                   refreshTokenRepository.save(refreshToken);
               });
    }
}
