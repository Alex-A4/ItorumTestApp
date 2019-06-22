package com.github.alexa4.providers;

import com.github.alexa4.controllers.Downloader;
import com.github.alexa4.models.People;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProviderTest {
    Provider<People> mPeople = new Provider<>(People.class);
    @Before
    public void setUp() throws Exception {
        new Downloader<People>().downloadPage("https://swapi.co/api/people/?page=1", mPeople);
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