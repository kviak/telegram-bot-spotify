package ru.kviak.telegrambotspotify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApi spotifyApi;
    private final List<String> songList = new ArrayList<>();

    public String getUserCurrentlyPlayingTrack_Sync() throws Exception{
        String response = getCurrentTrack();
        if (response != null) {
            return  response;
        } else return "Right now Denis doesn't listen to anything. You can use /last to see a list of the most recently listened tracks.";
    }

    public String getLastListenTracks() {
        StringBuilder stringBuilder = new StringBuilder();
        songList.forEach(s -> {
            stringBuilder.append(s).append("\n");
        });
        return stringBuilder.toString();
    }

    public void addToList() throws Exception { // TODO: Rework, look like parawa
        if (!songList.contains(getCurrentTrack())) {
            if (songList.size() < 20) {
                songList.add(getCurrentTrack());
            }
            else songList.remove(0);
        }
    }

    public String getCurrentTrack() throws Exception{ // TODO: rework too
        CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();
        if (currentlyPlaying == null) return null;

        if (currentlyPlaying.getIs_playing()) {
            IPlaylistItem iPlaylistItem = currentlyPlaying.getItem();
            Track track = spotifyApi.getTrack(iPlaylistItem.getId()).build().execute();
            StringBuilder response = new StringBuilder();

            response.append(track.getName()).append(" - ");
            Arrays.stream(track.getArtists())
                    .forEach(s -> response.append(s.getName()).append(", "));

            return response.substring(0, response.length()-2);
        }
        return null;
    }

    public String getTopTracks() throws Exception{
        Track[] arr = spotifyApi.getUsersTopTracks().time_range("long_term").build().execute().getItems(); // Top track all the time.
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            response.append(i+1).append(": ").append(arr[i].getName()).append(" - ").append(arr[i].getArtists()[0].getName()).append("\n");
        }
        return response.toString();
    }

    public String getTopArtist() throws Exception{
        Artist[] arr = spotifyApi.getUsersTopArtists().time_range("long_term").build().execute().getItems();
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            response.append(i+1).append(": ").append(arr[i].getName()).append("\n");
        }
        return response.toString();
    }
}
