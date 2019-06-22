package com.github.alexa4.controllers;

import com.github.alexa4.models.People;
import com.github.alexa4.models.Planet;
import com.github.alexa4.providers.Provider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataControllerTest {
    final Provider<People> mPeople = new Provider<>(People.class);
    final Provider<Planet> mPlanets = new Provider<>(Planet.class);

    @Before
    public void setUp() throws Exception {
        new Downloader<People>().downloadPage("https://swapi.co/api/people/?page=1", mPeople);
        new Downloader<Planet>().downloadPage("https://swapi.co/api/planets/?page=1", mPlanets);
    }

    @Test
    public void getPlanetByIndexLessZero() {
        Planet planet = mPlanets.getItemByIndex(-1);
        assertNull(planet);
    }

    @Test
    public void getPlanetByIndex() {
        Planet planet = mPlanets.getItemByIndex(1);
        assertNotNull(planet);
    }

    @Test
    public void getPlanetByIndexUpperSize() {
        Planet planet = mPlanets.getItemByIndex(15);
        assertNull(planet);
    }

    @Test
    public void getPeopleByIndexLessZero() {
        People people = mPeople.getItemByIndex(-1);
        assertNull(people);
    }

    @Test
    public void getPeopleByIndex() {
        People people = mPeople.getItemByIndex(1);
        assertNotNull(people);
    }

    @Test
    public void getPeopleByIndexUpperSize() {
        People people = mPeople.getItemByIndex(15);
        assertNull(people);
    }
}