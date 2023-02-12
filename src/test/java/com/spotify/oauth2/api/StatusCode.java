package com.spotify.oauth2.api;

public enum StatusCode {
    CODE_200(200),
    CODE_201(201),
    CODE_400(400),
    CODE_401(401);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
