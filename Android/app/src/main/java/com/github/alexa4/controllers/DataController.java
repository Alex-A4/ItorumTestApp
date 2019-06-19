package com.github.alexa4.controllers;

import com.github.alexa4.models.People;
import com.github.alexa4.models.Planet;
import com.github.alexa4.providers.Provider;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Controller that contains both providers to planets and people.
 */
public class DataController {
    // Singleton instance of class
    private static DataController instance = new DataController();

    // Get instance of class
    public static DataController getInstance() {
        return instance;
    }

    // Providers of data
    // Put Class variable to constructor to create instances of that class in work time
    private final Provider<People> mPeople = new Provider<>(People.class);
    private final Provider<Planet> mPlanets = new Provider<>(Planet.class);


    /**
     * Start downloading information about people and return observable
     * object that notify when result would be appear
     *
     * @return observable of list of people
     */
    public Observable<ArrayList<People>> downloadPeople() {
        return new Downloader<People>().getDownloader("people", mPeople);
    }


    /**
     * Start downloading information about planets and return observable
     * object that notify when result would be appear
     *
     * @return observable of list of planets
     */
    public Observable<ArrayList<Planet>> downloadPlanets() {
        return new Downloader<Planet>().getDownloader("planets", mPlanets);
    }

    /**
     * Return planet by input index
     * If index incorrect, then return null
     */
    public Planet getPlanetByIndex(int index) {
        return mPlanets.getItemByIndex(index);
    }

    /**
     * Return people by input index
     * If index incorrect, then return null
     */
    public People getPeopleByIndex(int index) {
        return mPeople.getItemByIndex(index);
    }
}
