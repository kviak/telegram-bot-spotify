package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    public String getUserCurrentlyPlayingTrack_Sync() throws Exception { // add expHandler fix change exp class
        CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();

        if (currentlyPlaying.getIs_playing()){
            IPlaylistItem iPlaylistItem = currentlyPlaying.getItem();
            Track track = spotifyApi.getTrack(iPlaylistItem.getId()).build().execute();
            StringBuilder response = new StringBuilder();

            response.append(track.getName() + " - ");

            Arrays.stream(track.getArtists())
                    .forEach(s -> response.append(s.getName() + ", "));

            return response.toString().substring(0, response.length()-2);
        }
        return "Unhandle error!";
    }
}
