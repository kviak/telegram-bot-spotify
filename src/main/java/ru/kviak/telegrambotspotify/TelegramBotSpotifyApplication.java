package ru.kviak.telegrambotspotify;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TelegramBotSpotifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotSpotifyApplication.class, args);
    }
}
