package dev.karan.subscriptionbillingplatform.auth.service;

import dev.karan.subscriptionbillingplatform.auth.entity.RefreshToken;
import dev.karan.subscriptionbillingplatform.auth.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken validateRefreshToken(String token);

    void revokeRefreshToken(String token);
}
