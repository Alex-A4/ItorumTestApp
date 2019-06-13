package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  The POJO class that describes instance of planet
 */
public class Planet {
    public final String mName;
    public final String mRotation;
    public final String mOrbital;
    public final String mDiameter;
    public final String mClimate;
    public final String mGravity;
    public final String mTerrain;
    public final String mWater;
    public final String mPopulation;

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
