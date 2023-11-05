package ru.kviak.telegrambotspotify.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kviak.telegrambotspotify.service.SpotifyTelegramBot;

@Component
@RequiredArgsConstructor
public class Initializer {
    private final SpotifyTelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            System.out.println("LENIN");
        } catch (TelegramApiException e) {
            System.out.println(bot.getBotUsername());
            System.out.println(bot.getBotToken());
            e.printStackTrace();
        }
    }
}