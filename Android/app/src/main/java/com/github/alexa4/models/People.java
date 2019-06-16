package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * The POJO class that describes instance of people
 */
public class People extends Model {
    private String mName;
    private String mHeight;
    private String mMass;
    private String mHairColor;
    private String mSkinColor;
    private String mEyeColor;
    private String mBirth;
    private String mGender;

    /**
     * Default empty constructor
     */
    public People() {
    }

    /**
     * Implementation on method that creates people from input json object
     *
     * @param json object with information about people
     */
    @Override
    public void extractData(JSONObject json) throws JSONException {
        mName = json.getString("name");
        mHeight = addAdditive(json.getString("height"), "cm");
        mMass = addAdditive(json.getString("mass"), "kg");
        mHairColor = json.getString("hair_color");
        mSkinColor = json.getString("skin_color");
        mEyeColor = json.getString("eye_color");
        mBirth = json.getString("birth_year");
        mGender = json.getString("gender");
    }

    public String name() {
        return mName;
    }

    public String height() {
        return mHeight;
    }

    public String mass() {
        return mMass;
    }

    public String hairColor() {
        return mHairColor;
    }

    public String skinColor() {
        return mSkinColor;
    }

    public String eyeColor() {
        return mEyeColor;
    }

    public String birth() {
        return mBirth;
    }

    public String gender() {
        return mGender;
    }
}
