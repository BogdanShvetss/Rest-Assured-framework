package com.spotify.oauth2.tests;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    //maven command to run and pass system properties
    //mvn test -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("Starting Test: " + method.getName());
        System.out.println("Thread id: " + Thread.currentThread().getId());
    }
}
