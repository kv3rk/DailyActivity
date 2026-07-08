package com.daily.plan.DailyActivityTracker.Role.Seeder;

import com.daily.plan.DailyActivityTracker.Role.Entity.Role;
import com.daily.plan.DailyActivityTracker.Role.Enum.RoleEnum;
import com.daily.plan.DailyActivityTracker.Role.Repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){


        loadRoles();
    }

    public void loadRoles(){

        RoleEnum[] roleNames = new RoleEnum[]{

                RoleEnum.ADMIN, RoleEnum.USER
        };

        Map<RoleEnum, String> roleDescription = Map.of(

                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.USER, "User role"

        );

        Arrays.stream(roleNames).forEach((roleName) -> {

            Optional<Role> optionalRole = Optional.ofNullable(
                    roleRepository.findByRole(roleName)
            );
            optionalRole.ifPresentOrElse((x) -> {

                log.info("[{} created at: {}]", x.getRole(), x.getCreatedAt());

            }, () -> {

                Role roleToCreate = new Role();
                roleToCreate.setRole(roleName);
                roleToCreate.setDescription(roleDescription.get(roleName));
                roleRepository.save(roleToCreate);

            });

        });

    }
}
