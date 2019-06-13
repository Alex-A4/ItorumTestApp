package com.github.alexa4.controllers;


import android.util.Log;

import com.github.alexa4.models.People;
import com.github.alexa4.models.Planet;
import com.github.alexa4.providers.PeopleProvider;
import com.github.alexa4.providers.PlanetsProvider;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Controller that provide access to data
 */
public class DataController {
    private static final String TAG = "DataController";

    // Singleton instance of class
    private static DataController instance = new DataController();

    // Get instance of class
    public static DataController getInstance() {
        return instance;
    }

    // Network client
    private final OkHttpClient client = new OkHttpClient();

    private final PeopleProvider mPeople = new PeopleProvider();
    private final PlanetsProvider mPlanets = new PlanetsProvider();


    /**
     * Start to downloading information about people and return observable
     * object that notify when result would be appear
     *
     * @return observable of list of people
     */
    public Observable<ArrayList<People>> downloadPeople() {
        return Observable.<ArrayList<People>>create(emitter -> {
            // Download from network if there is no data
            if (!mPeople.isLoaded()) {

                boolean isNewData = false;
                int page = 1;

                // Make network calls until all people would be downloaded
                do {
                    // Network request
                    Request request = new Request.Builder()
                            .url("https://swapi.co/api/people/?page=" + page)
                            .build();
                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful()) {
                            JSONObject json = new JSONObject(response.body().string());

                            // Check is there is more data
                            Log.d(TAG, json.get("next") == null
                                    ? null : json.get("next").toString());
                            isNewData = json.get("next") != null;

                            mPeople.parsePeople(json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }

                    // Send part of data to UI
                    emitter.onNext(mPeople.mPeople);

                    page++;
                } while (isNewData);

            } else {
                emitter.onNext(mPeople.mPeople);
            }

            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                );
    }


    /**
     * Start to downloading information about planets and return observable
     * object that notify when result would be appear
     *
     * @return observable of list of planets
     */
    public Observable<ArrayList<Planet>> downloadPlanets() {
        return Observable.<ArrayList<Planet>>create(emitter -> {
            // Download from network if there is no data
            if (!mPlanets.isLoaded()) {

                boolean isNewData = false;
                int page = 1;

                // Make network calls until all people would be downloaded
                do {
                    // Network request
                    Request request = new Request.Builder()
                            .url("https://swapi.co/api/planets/?page=" + page)
                            .build();
                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful()) {
                            JSONObject json = new JSONObject(response.body().string());

                            // Check is there is more data
                            Log.d(TAG, json.get("next") == null
                                    ? null : json.get("next").toString());
                            isNewData = json.get("next") != null;

                            mPlanets.parsePlanets(json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }

                    // Send data to UI
                    emitter.onNext(mPlanets.mPlanets);

                    page++;
                } while (isNewData);

            } else {
                emitter.onNext(mPlanets.mPlanets);
            }

            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                );
    }

    /**
     * Dispose controller to free memory
     */
    public void dispose() {
        mPlanets.dispose();
        mPlanets.dispose();
    }
}
