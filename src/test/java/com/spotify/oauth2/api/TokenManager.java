package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.RestResource.postAccount;

public class TokenManager {
    private static String access_token;
    private static Instant expiryTime;

    public synchronized static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing token...");

                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token time isn't expired");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to get token!");
        }

        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        Header authHeader = new Header("Authorization", ConfigLoader.getInstance().getAuthorization());

        Response response = postAccount(formParams, authHeader);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Abort! Renew token failed.");
        }

        return response;
    }
}
