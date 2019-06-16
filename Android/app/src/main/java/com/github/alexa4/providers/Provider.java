package com.github.alexa4.providers;

import android.util.Log;

import com.github.alexa4.models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Parametrised provider of specified class that extend Model
 * <p>
 * This provider contains list of data of type T.
 * This data came from parseData method
 *
 * @param <T> is class that extend {@link Model} and implement own way
 *            to extract data from JSONObject
 */
public class Provider<T extends Model> {
    // Tag to use logger
    private static final String TAG = "Provider";

    /**
     * List of data of type T
     * The list fills when method parseData completes
     */
    private final ArrayList<T> mList = new ArrayList<>();

    // Getter of mList
    public ArrayList<T> list() {
        return mList;
    }


    /**
     * Check is list contains some data.
     * <p>
     * If size not 0, then there is some objects that could be used,
     * else there is no data
     */
    public boolean isLoaded() {
        return mList.size() != 0;
    }


    /**
     * Parse data from input JSON object and add new instance
     * of class T to list
     *
     * @param json object should contains data that needs to
     *             create instance of class T
     */
    public void parseData(JSONObject json) throws JSONException {
        Log.d(TAG, "Start parsing");

        try {
            JSONArray array = json.getJSONArray("results");

            Class<T> maker = new Class<>();

            for (int i = 0; i < array.length(); i++) {
                // Create instance of class T
                T model = maker.newInstance();

                model.extractData(array.getJSONObject(i));
                mList.add(model);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "End parsing");
    }

    /**
     * Return item from list by specified index
     * If index incorrect, then return null
     */
    public T getItemByIndex(int index) {
        if (index < mList.size() && index >= 0)
            return mList.get(index);

        return null;
    }
}
