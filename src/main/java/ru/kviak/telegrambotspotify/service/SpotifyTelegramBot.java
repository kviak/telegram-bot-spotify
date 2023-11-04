package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kviak.telegrambotspotify.config.BotConfiguration;

@RequiredArgsConstructor
@Component
public class SpotifyTelegramBot extends TelegramLongPollingBot {

    private final BotConfiguration botConfiguration;

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
