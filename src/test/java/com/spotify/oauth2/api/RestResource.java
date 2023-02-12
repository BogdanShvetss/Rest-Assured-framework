package com.spotify.oauth2.api;

import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static com.spotify.oauth2.api.application.Route.API;
import static com.spotify.oauth2.api.application.Route.TOKEN;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String token, Object payload) {
        return given()
                .spec(getRequestSpec())
                .body(payload)
                .auth().oauth2(token)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }

    public static Response get(String path, String token) {
        return given()
                .spec(getRequestSpec())
                .when()
                .auth().oauth2(token)
                .get(path)
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }


    public static Response update(String path, String token, Object payload) {
        return given()
                .spec(getRequestSpec())
                .auth().oauth2(token)
                .body(payload)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }

    public static Response postAccount(HashMap<String, String> formParams, Header authHeader) {
        return given(getAccountRequestSpec())
                .header(authHeader)
                .formParams(formParams)
                .when()
                .post(API + TOKEN)
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }
}

