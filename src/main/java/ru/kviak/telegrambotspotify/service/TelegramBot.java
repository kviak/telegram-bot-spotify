package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kviak.telegrambotspotify.config.TelegramBotConfiguration;

@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotConfiguration botConfiguration;
    private final RefreshAuthorizationCodeSpotify refreshAuthorizationCodeSpotify;
    private final SpotifyService spotifyService;

    @Override
    public String getBotUsername() { return botConfiguration.getName(); }
    @Override
    public String getBotToken() { return botConfiguration.getToken(); }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText())
        {
            Message inMess = update.getMessage();
            String response = parseMessage(update.getMessage().getText());
            SendMessage outMess = new SendMessage();

            outMess.setChatId(inMess.getChatId());
            outMess.setText(response);

            execute(outMess);
        }
    }
    @Scheduled(fixedRate = 60000)
    public void scheduledSpotifyListTrack() throws Exception {
        refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync();
        spotifyService.addToList();
    }

    public String parseMessage(String textMsg) throws Exception {
        String response;

        switch (textMsg) {
            case "/start" -> {
                response = "Welcome to 'Kviak Bot'. List available commands: /now, /last, /top-track, /top-artist, /help";
            }
            case "/help" -> {
                response = "Command list: /now, /last, /top-track, /top-artist, /help";
            }
            case "/now" -> {
                refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync();
                response = spotifyService.getUserCurrentlyPlayingTrack_Sync();
            }
            case "/last" -> {
                refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync();
                response = spotifyService.getLastListenTracks();
            }
            case "/top-track" -> {
                refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync();
                response = spotifyService.getTopTracks();
            }
            case "/top-artist" -> {
                refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync();
                response = spotifyService.getTopArtist();
            }
            default -> response = "Sorry, but I do not know this command!";
        }
        return response;
    }
}
