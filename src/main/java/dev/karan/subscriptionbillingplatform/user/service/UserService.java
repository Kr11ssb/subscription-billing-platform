package dev.karan.subscriptionbillingplatform.user.service;

import dev.karan.subscriptionbillingplatform.user.dto.UserProfileResponseDTO;
import org.springframework.stereotype.Service;

public interface UserService {

    UserProfileResponseDTO getCurrentUserProfile();
}
