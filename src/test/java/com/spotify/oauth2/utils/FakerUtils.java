package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    public static String generateName() {
        return Faker.instance().music().genre();
    }

    public static String generateDescription() {
        return Faker.instance().lebowski().quote();
    }
}
