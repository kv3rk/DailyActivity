package com.daily.plan.DailyActivityTracker.TelegramBot.PrepareAnswer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class NumberIntoEmoji {

    private final Map<Character, String> emojiStorage = new HashMap<>(
            Map.of(
                    '0', "0\uFE0F⃣",
                    '1', "1\uFE0F⃣",
                    '2', "2\uFE0F⃣",
                    '3', "3\uFE0F⃣",
                    '4', "4\uFE0F⃣",
                    '5', "5\uFE0F⃣",
                    '6', "6\uFE0F⃣",
                    '7', "7\uFE0F⃣",
                    '8', "8\uFE0F⃣",
                    '9', "9\uFE0F⃣"
            )
    );

    public String convertNumToEmoji(Number number) {

        String value = number.toString();

        StringBuilder result = new StringBuilder();

        for (char symbol : value.toCharArray()) {

            if (emojiStorage.containsKey(symbol)) {
                result.append(emojiStorage.get(symbol));
            } else {
                result.append(symbol);
            }

        }

        return result.toString();
    }

}
