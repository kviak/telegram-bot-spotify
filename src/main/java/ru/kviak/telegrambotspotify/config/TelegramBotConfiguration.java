package ru.kviak.telegrambotspotify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("telegram.bot")
public class TelegramBotConfiguration {
    private String name;
    private String token;
}
