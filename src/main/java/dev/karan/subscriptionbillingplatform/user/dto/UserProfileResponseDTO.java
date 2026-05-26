package dev.karan.subscriptionbillingplatform.user.dto;

import dev.karan.subscriptionbillingplatform.auth.entity.Role;
import dev.karan.subscriptionbillingplatform.auth.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDTO {

    private long id;

    private String name;

    private String email;

    private String organizationName;

    private Role role;

    private UserStatus userStatus;

}
