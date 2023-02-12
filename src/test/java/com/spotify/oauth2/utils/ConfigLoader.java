package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }

        return configLoader;
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");

        if (prop != null) return prop;
        else throw new RuntimeException("Property grant_type isn't specified in properties file");
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");

        if (prop != null) return prop;
        else throw new RuntimeException("Property refresh_token isn't specified in properties file");
    }

    public String getAuthorization() {
        String prop = properties.getProperty("authorization");

        if (prop != null) return prop;
        else throw new RuntimeException("Property authorization isn't specified in properties file");
    }

    public String getUserId() {
        String prop = properties.getProperty("user_id");

        if (prop != null) return prop;
        else throw new RuntimeException("Property user_id isn't specified in properties file");
    }
}
