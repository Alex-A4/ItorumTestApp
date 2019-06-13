package com.github.alexa4.providers;

import android.util.Log;


import com.github.alexa4.models.Planet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Provider of planets that stores list of Planet instances.
 * Provider can parse json data to extract helpful information
 */
public class PlanetsProvider {
    private static final String TAG = "PlanetsProvider";
    public final ArrayList<Planet> mPlanets = new ArrayList<>();

    /**
     * Check is people list already downloaded
     */
    public boolean isLoaded() {
        return mPlanets.size() != 0;
    }


    /**
     * Parse information from incoming json object.
     * This method could be called multiple times
     *
     * @param json object with data
     */
    public void parsePlanets(JSONObject json) {
        Log.d(TAG, "Start parsing");

        try {
            JSONArray array = json.getJSONArray("results");
            for (int i = 0; i < array.length(); i++)
                mPlanets.add(new Planet(array.getJSONObject(i)));

        } catch (Exception e) {
            e.printStackTrace();
            mPlanets.clear();
        }


        Log.d(TAG, "End parsing");
    }

    /**
     * Dispose provider and clear data
     */
    public void dispose() {
        mPlanets.clear();
    }
}
