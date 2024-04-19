package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshAuthorizationCodeSpotify {
    private final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;
    private final SpotifyApi spotifyApi;
    private final String INVALID_REFRESH_TOKEN="Invalid refresh token";

    public void authorizationCodeRefresh_Sync(){
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            if (INVALID_REFRESH_TOKEN.equals(e.getMessage())) {
                log.error("Spotify refresh token has expired, please replace it by hand\n" +
                        "You can look at the README.md file to understand how to do this");
            } else System.out.println("Error: " + e.getMessage());
        }
    }
}
