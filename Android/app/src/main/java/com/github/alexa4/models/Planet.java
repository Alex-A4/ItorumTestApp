package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The POJO class that describes instance of planet
 */
public class Planet extends Model {
    private String mName;
    private String mDiameter;
    private String mClimate;
    private String mGravity;
    private String mTerrain;
    private String mWater;
    private String mPopulation;

    /**
     * Default empty constructor
     */
    public Planet() {}

    @Override
    public void extractData(JSONObject json) throws JSONException {
        mName = json.getString("name");
        mDiameter = addAdditive(json.getString("diameter"), "km");
        mClimate = json.getString("climate");
        mGravity = json.getString("gravity");
        mTerrain = json.getString("terrain");
        mWater = addAdditive(json.getString("surface_water"), "%");
        mPopulation = addAdditive(json.getString("population"), "people");
    }

    public String name() {
        return mName;
    }

    public String diameter() {
        return mDiameter;
    }

    public String climate() {
        return mClimate;
    }

    public String gravity() {
        return mGravity;
    }

    public String terrain() {
        return mTerrain;
    }

    public String water() {
        return mWater;
    }

    public String population() {
        return mPopulation;
    }
}
