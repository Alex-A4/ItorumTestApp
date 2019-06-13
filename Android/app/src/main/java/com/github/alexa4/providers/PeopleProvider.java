package com.github.alexa4.providers;

import android.util.Log;

import com.github.alexa4.models.People;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Provider of people that stores list of People instances.
 * Provider can parse json data to extract helpful information
 */
public class PeopleProvider {
    private static final String TAG = "PeopleProvider";
    public final ArrayList<People> mPeople = new ArrayList<>();

    /**
     * Check is people list already downloaded
     */
    public boolean isLoaded() {
        return mPeople.size() != 0;
    }


    /**
     * Parse information from incoming json object.
     * This method could be called multiple times
     *
     * @param json object with data
     */
    public void parsePeople(JSONObject json) {
        Log.d(TAG, "Start parsing");

        try {
            JSONArray array = json.getJSONArray("results");
            for (int i = 0; i < array.length(); i++)
                mPeople.add(new People(array.getJSONObject(i)));

        } catch (Exception e) {
            e.printStackTrace();
            mPeople.clear();
        }

        Log.d(TAG, "End parsing");
    }


    /**
     * Dispose provider and clear data
     */
    public void dispose() {
        mPeople.clear();
    }
}
