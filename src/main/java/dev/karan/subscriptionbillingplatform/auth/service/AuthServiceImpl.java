package dev.karan.subscriptionbillingplatform.auth.service;

import dev.karan.subscriptionbillingplatform.auth.dto.AuthResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginRequestDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.RegisterRequestDTO;
import dev.karan.subscriptionbillingplatform.auth.entity.Role;
import dev.karan.subscriptionbillingplatform.auth.entity.User;
import dev.karan.subscriptionbillingplatform.auth.entity.UserStatus;
import dev.karan.subscriptionbillingplatform.auth.repository.UserRepository;
import dev.karan.subscriptionbillingplatform.auth.security.JwtService;
import dev.karan.subscriptionbillingplatform.common.exception.EmailAlreadyExistsException;
import dev.karan.subscriptionbillingplatform.common.exception.InvalidCredentialsException;
import dev.karan.subscriptionbillingplatform.config.JwtProperties;
import dev.karan.subscriptionbillingplatform.organization.entity.Organization;
import dev.karan.subscriptionbillingplatform.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email Already registered");
        }

        Organization organization = organizationRepository.
                findByName(request.getOrganizationName()).orElseGet(()-> {
                    Organization org = new Organization();
                    org.setName(request.getOrganizationName());
                    return organizationRepository.save(org);
                });

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        user.setOrganization(organization);
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return new AuthResponseDTO(null,"User is Registered", null);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

         if(user.getUserStatus() != UserStatus.ACTIVE){
             throw new InvalidCredentialsException("Invalid credentials");
         }

         String accessToken = jwtService.generateAccessToken(user);
         String refreshToken = jwtService.generateRefreshToken(user);
         Long expiresIn = jwtProperties.getAccessTokenExpiration();

         LoginResponseDTO response = new LoginResponseDTO();
         response.setAccessToken(accessToken);
         response.setRefreshToken(refreshToken);
         response.setExpiresIn(expiresIn);

         return response;
    }

}
