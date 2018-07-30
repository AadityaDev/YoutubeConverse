package com.technawabs.app.youtubeconverse.services;

import android.util.Log;

import com.technawabs.app.youtubeconverse.concurrency.ExecutorUtils;
import com.technawabs.app.youtubeconverse.constants.AppAPI;
import com.technawabs.app.youtubeconverse.constants.AppConstant;
import com.technawabs.app.youtubeconverse.network.RequestGenerator;
import com.technawabs.app.youtubeconverse.network.RequestHandler;
import com.technawabs.app.youtubeconverse.utils.ExceptionUtils;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import okhttp3.Request;

public class MoviesService {

    private final String TAG=this.getClass().getSimpleName();

    public ListenableFuture<JSONArray> getMovies() {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.MOVIES_URL);
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONObject response=new JSONObject(result);
                JSONArray movies = new JSONArray();
                movies=response.getJSONArray(AppConstant.RESULTS);
                try {
                    Log.d(TAG, movies.toString());
                } catch (Exception exception) {
                    ExceptionUtils.exceptionMessage(exception,TAG);
                } finally {
                    return movies;
                }
            }
        });
    }

    public ListenableFuture<JSONObject> getVideoUrl(final String key){
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.GET_VIDEO_PREFIX+key+AppAPI.GET_VIDEO_SUFFIX);
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONObject response=new JSONObject(result);
                return response;
            }
        });
    }

    public ListenableFuture<JSONObject> getMovieDetail(final String key){
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.MOVIE_DETAIL_PREFIX+key+AppAPI.MOVIE_DETAIL_SUFFIX);
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONObject response=new JSONObject(result);
                return response;
            }
        });
    }

}
