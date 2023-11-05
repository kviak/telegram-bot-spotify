package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kviak.telegrambotspotify.config.BotConfiguration;

@RequiredArgsConstructor
@Component
public class SpotifyTelegramBot extends TelegramLongPollingBot {

    private final BotConfiguration botConfiguration;
    private final RefreshAuthorizationCodeSpotify refreshAuthorizationCodeSpotify;
    private final SpotifyService spotifyService;

    @Override
    public String getBotUsername() { return botConfiguration.getBotName(); }
    @Override
    public String getBotToken() { return botConfiguration.getToken(); }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText())
        {
            //Извлекаем из объекта сообщение пользователя
            Message inMess = update.getMessage();
            //Получаем текст сообщения пользователя, отправляем в написанный обработчик
            String response = parseMessage(update.getMessage().getText());
            //Создаем объект класса SendMessage - наш будущий ответ пользователю
            SendMessage outMess = new SendMessage();
            //Добавляем в наше сообщение id чата а также наш ответ
            outMess.setChatId(inMess.getChatId());
            outMess.setText(response);
            //Отправка в чат
            execute(outMess);
        }
    }

    public String parseMessage(String textMsg) throws Exception {
        String response;

        if(textMsg.equals("/start"))
            response = "Welcome to 'Kviak Bot', if you want to get Denis listen track send me /track.";
        else if(textMsg.equals("/track")){
            refreshAuthorizationCodeSpotify.authorizationCodeRefresh_Sync(); // При каждом сообщении вызывается метод для обновления токена спотифай
            response = spotifyService.getUserCurrentlyPlayingTrack_Sync();}
        else response = "Sorry, but I do not know this command!";

        return response;
    }
}
