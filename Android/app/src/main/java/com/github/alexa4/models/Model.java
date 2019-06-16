package com.github.alexa4.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract model that needs to use with Provider
 * <p>
 * This class contains method to extract data from input JSON object
 */
public abstract class Model {
    private final String unkwn = "unknown";

    /**
     * Abstract method to extract data from input JSON object
     */
    public abstract void extractData(JSONObject json) throws JSONException;

    /**
     * Add additive to the end of word.
     * <p>
     * If word is unknown then return word without additive,
     * if not, then add additive to the end of word
     *
     * @param word     for which need add additive
     * @param additive additive that need add to word
     */
    protected String addAdditive(String word, String additive) {
        return word + (word.compareTo(unkwn) == 0 ? "" : "  " + additive);
    }
}
