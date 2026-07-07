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

        log.info("Created instance of current time [{}]", currentTime);

        return currentTime;

    }

    public LocalDate getCurrentDate() {

        LocalDate currentDate = LocalDate.now(

                ZoneId.of(

                        "Europe/Moscow"

                )

        );

        log.info("Created instance of current date [{}]", currentDate);

        return currentDate;

    }

    public String getFormattedTime() {

        String formattedTime = getCurrentTime().format(

                DateTimeFormatter.ofPattern(

                        "dd-MM-yyyy HH:mm"

                )

        );

        log.info("Created instance of current time STRING [{}]", formattedTime);

        return formattedTime;

    }

    public String getFormattedDate() {

        String formattedDate = getCurrentDate().format(

                DateTimeFormatter.ofPattern(

                        "dd-MM-yyyy"

                )

        );

        log.info("Created instance of current date STRING [{}]", formattedDate);

        return formattedDate;

    }
}
