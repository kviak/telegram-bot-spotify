package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RefreshAuthorizationCodeSpotify {
    private final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;
    private final SpotifyApi spotifyApi;

    public void authorizationCodeRefresh_Sync(){
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            System.out.println("Access Token: " + authorizationCodeCredentials.getAccessToken());
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
