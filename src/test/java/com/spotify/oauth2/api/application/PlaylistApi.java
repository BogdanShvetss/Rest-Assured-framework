package com.spotify.oauth2.api.application;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.TokenManager.getToken;
import static com.spotify.oauth2.api.application.Route.PLAYLISTS;
import static com.spotify.oauth2.api.application.Route.USERS;

public class PlaylistApi {


    public static Response post(Playlist playlist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), playlist);
    }

    public static Response post(String token, Playlist playlist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token, playlist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(String playlistId, Playlist playlist) {
        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), playlist);
    }
}

