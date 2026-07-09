package com.daily.plan.DailyActivityTracker.User.Builder;

import com.daily.plan.DailyActivityTracker.Role.Entity.Role;
import com.daily.plan.DailyActivityTracker.Role.Enum.RoleEnum;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    private String username = "user";
    private String password = "password";
    private Role role = new Role();

    public UserBuilder withUsername(String username) {

        this.username = username;

        return this;
    }

    public UserBuilder withPassword(String password) {

        this.password = password;

        return this;
    }

    public UserBuilder withRole(Role role) {

        this.role = role;

        return this;
    }

    public User build() {

        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
