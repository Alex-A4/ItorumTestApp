package com.github.alexa4.controllers;

import com.github.alexa4.models.People;
import com.github.alexa4.models.Planet;
import com.github.alexa4.providers.Provider;

import org.junit.Assert;
import org.junit.Test;

public class DownloaderTest {
    private static Provider<People> mPeople = new Provider<>(People.class);
    private static Provider<Planet> mPlanets = new Provider<>(Planet.class);

    @Test
    public void downloadPage_3_People() throws Exception {
        int result = new Downloader<People>()
                .downloadPage("https://swapi.co/api/people/?page=3", mPeople);
        Assert.assertEquals("Assert result of downloading 3-rd page of people",
                1, result);
    }

    @Test
    public void downloadPage_10_People() throws Exception {
        int result = new Downloader<People>()
                .downloadPage("https://swapi.co/api/people/?page=10", mPeople);
        Assert.assertEquals("Assert result of downloading 10-th page of people",
                4, result);
    }

    @Test
    public void downloadPage_9_People() throws Exception {
        int result = new Downloader<People>()
                .downloadPage("https://swapi.co/api/people/?page=9", mPeople);
        Assert.assertEquals("Assert result of downloading 9-th page of people",
                2, result);
    }

    @Test
    public void downloadPage_1_Planet() throws Exception {
        int result = new Downloader<Planet>()
                .downloadPage("https://swapi.co/api/planets/?page=1", mPlanets);
        Assert.assertEquals(1, result);
    }

    @Test
    public void downloadPage_7_Planet() throws Exception {
        int result = new Downloader<Planet>()
                .downloadPage("https://swapi.co/api/planets/?page=7", mPlanets);
        Assert.assertEquals(2, result);
    }

    @Test
    public void downloadPage_10_Planet() throws Exception {
        int result = new Downloader<Planet>()
                .downloadPage("https://swapi.co/api/planets/?page=10", mPlanets);
        Assert.assertEquals(4, result);
    }
}