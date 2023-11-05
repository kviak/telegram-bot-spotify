package ru.kviak.telegrambotspotify.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spotify")
public class SpotifyApiConfig {

    private String clientId;
    private String clientSecret;
    private String refreshToken;

    @Bean
    public SpotifyApi spotifyApi() {
        return SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();
    }

    @Bean
    public AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest() {
        return spotifyApi().authorizationCodeRefresh().build();
    }

//    @Bean
//    public GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest() {
//        return spotifyApi().getUsersCurrentlyPlayingTrack().build();
//    }
}
