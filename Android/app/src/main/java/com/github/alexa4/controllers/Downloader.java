package com.github.alexa4.controllers;

import android.util.Log;

import com.github.alexa4.models.Model;
import com.github.alexa4.providers.Provider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Support class that uses to get Observable object that downloads
 * data about specified type T
 *
 * @param <T> is implementation of {@link Model}
 */
public class Downloader<T extends Model> {
    private static final String TAG = "Downloader";
    // Network client
    private static final OkHttpClient client = new OkHttpClient();


    /**
     * Get Observable that is trying to download data with specified urlPart
     * and put it to provider
     * <p>
     * Make network calls until all data would be downloaded.
     * Each separate network call downloads some data and bring information about
     * is there more data or not.
     * If there is more data then next network call would be made with increased
     * number of page
     * <p>
     * If exception appeared in time of downloading then it sends to UI
     *
     * @param urlPart  the part of url to refer to API. It could be "people" or "planets"
     * @param provider the provider that contains list of data of specified type T
     */
    public Observable<ArrayList<T>> getDownloader(String urlPart, Provider<T> provider) {
        return Observable.<ArrayList<T>>create(emitter -> {
                    if (!provider.isLoaded()) {
                        boolean isNewData = false;
                        int page = 1;

                        Log.d(TAG, "Start downloading of " + urlPart);

                        do {
                            try {
                                isNewData = makeCall("https://swapi.co/api/" + urlPart
                                        + "/?page=" + page, provider);
                            } catch (Exception e) {
                                e.printStackTrace();
                                emitter.onError(new Exception("Unable to download " + urlPart));
                                break;
                            }

                            // Send data to UI
                            emitter.onNext(provider.list());

                            page++;
                        } while (isNewData);

                        Log.d(TAG, "Finish downloading of " + urlPart);

                    } else {
                        emitter.onNext(provider.list());
                    }

                    emitter.onComplete();
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Make network call by specified url to get data and put it to provider to parse
     * <p>
     * Return true if there is more data to download or false if there is no data
     *
     * @param url      the url for which need to make network call
     * @param provider the provider which should get data
     * @return is new data available to download
     */
    private boolean makeCall(String url, Provider<T> provider)
            throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            JSONObject json = new JSONObject(response.body().string());

            provider.parseData(json);
            return json.get("next") != null;
        }

        return false;
    }
}
