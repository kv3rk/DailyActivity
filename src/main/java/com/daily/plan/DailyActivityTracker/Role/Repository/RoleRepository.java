package com.daily.plan.DailyActivityTracker.Role.Repository;

import com.daily.plan.DailyActivityTracker.Role.Entity.Role;
import com.daily.plan.DailyActivityTracker.Role.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByRole(RoleEnum role);
}
