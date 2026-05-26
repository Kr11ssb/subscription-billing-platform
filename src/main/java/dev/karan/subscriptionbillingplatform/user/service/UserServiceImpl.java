package dev.karan.subscriptionbillingplatform.user.service;
import dev.karan.subscriptionbillingplatform.auth.entity.User;
import dev.karan.subscriptionbillingplatform.auth.repository.UserRepository;
import dev.karan.subscriptionbillingplatform.common.exception.UserNotFoundException;
import dev.karan.subscriptionbillingplatform.user.dto.UserProfileResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getCurrentUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("User not found"));

        UserProfileResponseDTO response = new UserProfileResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(email);
        response.setRole(user.getRole());
        response.setUserStatus(user.getUserStatus());
        response.setOrganizationName(user.getOrganization().getName());

        return response;
    }
}
