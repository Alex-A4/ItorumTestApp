package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  The POJO class that describes instance of planet
 */
public class Planet {
    final String mName;
    final String mRotation;
    final String mOrbital;
    final String mDiameter;
    final String mClimate;
    final String mGravity;
    final String mTerrain;
    final String mWater;
    final String mPopulation;

    // ??
    final String mUrl;

    /**
     * Default constructor that create planet from incoming json object
     *
     * @param json object with planet
     */
    public Planet(JSONObject json) throws JSONException {
        mName = json.getString("name");
        mRotation = json.getString("rotation_period");
        mOrbital = json.getString("orbital_period");
        mDiameter = json.getString("diameter");
        mClimate = json.getString("climate");
        mGravity = json.getString("gravity");
        mTerrain = json.getString("terrain");
        mWater = json.getString("surface_water");
        mPopulation = json.getString("population");
        mUrl = json.getString("url");
    }
}
