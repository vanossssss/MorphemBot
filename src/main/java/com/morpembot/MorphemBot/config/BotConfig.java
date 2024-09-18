package com.morpembot.MorphemBot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.key}")
    String botKey;

    public String getBotName() {
        return botName;
    }

    public String getBotKey() {
        return botKey;
    }

}