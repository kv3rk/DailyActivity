package com.daily.plan.DailyActivityTracker.User.Repository;

import com.daily.plan.DailyActivityTracker.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    List<User> findAllByTelegramIsNotNullAndTelegramNot(String telegram);

    User findByTelegram(String telegram);

    boolean existsByTelegram(String telegram);
}
