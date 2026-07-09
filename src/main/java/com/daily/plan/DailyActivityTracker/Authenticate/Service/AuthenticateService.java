package com.daily.plan.DailyActivityTracker.Authenticate.Service;

import com.daily.plan.DailyActivityTracker.Role.Enum.RoleEnum;
import com.daily.plan.DailyActivityTracker.Role.Repository.RoleRepository;
import com.daily.plan.DailyActivityTracker.User.Builder.UserBuilder;
import com.daily.plan.DailyActivityTracker.User.DTO.RegistrationUserDTO;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticateService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean registerUser(RegistrationUserDTO registrationUserDTO) {

        Optional<User> optionalUser = Optional.ofNullable(
                userRepository.findByUsername(registrationUserDTO.username())
        );

        if (optionalUser.isPresent()) {
            log.info("Registration failed, user [{}] already exists", registrationUserDTO.username());
            return false;
        }

        User user = new UserBuilder()
                .withUsername(registrationUserDTO.username())
                .withPassword(passwordEncoder.encode(registrationUserDTO.password()))
                .withRole(roleRepository.findByRole(RoleEnum.USER))
                .build();

        userRepository.save(user);

        log.info("Created user with credentials [{}], [{}]",
                user.getUsername(), user.getRole().getRole());

        return true;
    }

    public String getUsername() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return username;
    }
}
