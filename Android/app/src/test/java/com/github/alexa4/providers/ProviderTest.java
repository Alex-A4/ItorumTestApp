package com.github.alexa4.providers;

import com.github.alexa4.controllers.Downloader;
import com.github.alexa4.models.People;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProviderTest {
    Provider<People> mPeople = new Provider<>(People.class);

    @Before
    public void setUp() throws Exception {
        new Downloader<People>()
                .downloadPage("https://swapi.co/api/people/?page=1", mPeople);
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

    @Test
    public void testParsingTrue() throws Exception {
        Provider<People> people = new Provider<>(People.class);
        String data = new Downloader<People>()
                .makeCall("https://swapi.co/api/people/?page=1");

        JSONObject json = new JSONObject(data);
        people.parseData(json);
        assertTrue(people.isLoaded());
    }

    @Test
    public void testParsingFalse() throws Exception {
        Provider<People> people = new Provider<>(People.class);
        String data = new Downloader<People>()
                .makeCall("https://swapi.co/api/planets/?page=1");

        JSONObject json = new JSONObject(data);
        people.parseData(json);
        assertFalse(people.isLoaded());
    }
}