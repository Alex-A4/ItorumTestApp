package com.github.alexa4.controllers;

import android.util.Log;

import com.github.alexa4.models.Model;
import com.github.alexa4.providers.Provider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
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
     * and put it to provider. All updates sends to UI with emitter
     * <p>
     * Download data page by page until all data would be downloaded.
     * Each separate network call downloads some data and bring information about
     * is there more data or not.
     * If there is more data then next network call would be made with increased
     * number of page
     * <p>
     * If exception appeared in time of downloading then it sends to UI
     * <p>
     * Code handle:
     * 1 - continue work
     * 2 - finish work
     * 3 - skip iteration
     * 4 - send error to UI and stop working
     *
     * @param urlPart  the part of url to refer to API. It could be "people" or "planets"
     * @param provider the provider that contains list of data of specified type T
     */
    public Observable<ArrayList<T>> getDownloader(String urlPart, Provider<T> provider) {
        return Observable.<ArrayList<T>>create(emitter -> {
                    if (!provider.isLoaded()) {
                        int page = 1;

                        while (true) {
                            int result = downloadPage("https://swapi.co/api/"
                                    + urlPart + "/?page=" + page, provider);

                            if (result == 1) {
                                emitter.onNext(provider.list());
                                page++;
                            } else if (result == 2)
                                break;
                            else if (result == 3) page++;
                            else {
                                emitter.onError(new Throwable("Unable to download data"));
                                return;
                            }
                        }
                    }

                    // if loaded or last update
                    emitter.onNext(provider.list());
                    emitter.onComplete();
                }
        ).subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Download unique page with data and put it to provider.
     * After downloading return one of codes:
     * 1 - got new data
     * 2 - data ended
     * 3 - json parse exception (incorrect data)
     * 4 - downloading error (appears when user have no internet or page incorrect)
     *
     * @param url      the url from which need download data
     * @param provider the provider where need to put data
     * @return result code in range [1..4]
     */
    public int downloadPage(String url, Provider<T> provider) {
        try {
            String body = makeCall(url);
            if (body == null)
                return 4;

            JSONObject json = new JSONObject(body);
            provider.parseData(json);

            return json.getString("next").compareTo("null") != 0 ? 1 : 2;
        } catch (JSONException e) {
            e.printStackTrace();
            return 3;
        } catch (IOException e) {
            e.printStackTrace();
            return 4;
        }
    }

    /**
     * Make network call by specified url to get data and return result string or null
     * <p>
     * Return string result if there is data or null if there is error
     *
     * @param url the url for which need to make network call
     * @return string result or null
     */
    public String makeCall(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful())
            return response.body().string();

        return null;
    }
}
