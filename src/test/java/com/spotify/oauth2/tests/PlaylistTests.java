package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.application.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests extends BaseTest {

    @Test()
    public void ShouldBeAbleToCreatePlaylist() {
        Playlist requestPlaylist = Playlist.builder()
                .name(generateName())
                .description(generateDescription())
                ._public(false)
                .build();

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Test
    public void ShouldBeAbleToGetPlaylist() {
        Playlist requestPlaylist = Playlist.builder()
                .name("Playlist")
                .description("Updated description")
                ._public(false)
                .build();

        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Test
    public void ShouldBeAbleToUpdatePlaylist() {
        Playlist requestPlaylist = Playlist.builder()
                .name(generateName())
                .description(generateDescription())
                ._public(false)
                .build();

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
    }

    @Test
    public void ShouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = Playlist.builder()
                .description(generateDescription())
                ._public(false)
                .build();

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_400.getCode()));
        Error error = response.as(Error.class);

        assertThat(error.getInnerError().getMessage(), equalTo("Missing required field: name"));
        assertThat(error.getInnerError().getStatus(), equalTo(StatusCode.CODE_400.getCode()));
    }


    @Test
    public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken() {
        Playlist requestPlaylist = Playlist.builder()
                .name(generateName())
                .description(generateDescription())
                ._public(false)
                .build();

        Response response = PlaylistApi.post("invalid_token", requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.getCode()));
        Error error = response.as(Error.class);

        assertThat(error.getInnerError().getMessage(), equalTo("Invalid access token"));
        assertThat(error.getInnerError().getStatus(), equalTo(StatusCode.CODE_401.getCode()));
    }
}
