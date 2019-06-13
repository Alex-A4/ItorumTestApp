package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * The POJO class that describes instance of people
 */
public class People {
    final String mName;
    final String mHeight;
    final String mMass;
    final String mHairColor;
    final String mSkinColor;
    final String mEyeColor;
    final String mBirth;
    final String mGender;
    final String mUrl;

    // ??
    final String mHome;

    /**
     * Default constructor that create people from incoming json object
     *
     * @param json object with people
     */
    public People(JSONObject json) throws JSONException {
        mName = json.getString("name");
        mHeight = json.getString("height");
        mMass = json.getString("mass");
        mHairColor = json.getString("hair_color");
        mSkinColor = json.getString("skin_color");
        mEyeColor = json.getString("eye_color");
        mBirth = json.getString("birth_year");
        mGender = json.getString("gender");
        mUrl = json.getString("url");
        mHome = json.getString("homeworld");
    }
}
