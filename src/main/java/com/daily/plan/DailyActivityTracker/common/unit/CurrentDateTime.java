package com.daily.plan.DailyActivityTracker.common.unit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class CurrentDateTime {

    public LocalDateTime getCurrentTime() {

        LocalDateTime currentTime = LocalDateTime.now(

                ZoneId.of(
                        "Europe/Moscow"
                )

        );

        return currentTime;

    }

    public LocalDate getCurrentDate() {

        LocalDate currentDate = LocalDate.now(

                ZoneId.of(

                        "Europe/Moscow"

                )

        );

        return currentDate;

    }

    public String getFormattedTime() {

        String formattedTime = getCurrentTime().format(

                DateTimeFormatter.ofPattern(

                        "dd-MM-yyyy HH:mm:ss"

                )

        );

        return formattedTime;

    }

    public String getFormattedDate() {

        String formattedDate = getCurrentDate().format(

                DateTimeFormatter.ofPattern(

                        "dd-MM-yyyy"

                )

        );

        return formattedDate;

    }
}
