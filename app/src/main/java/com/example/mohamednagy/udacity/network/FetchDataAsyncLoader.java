package com.example.mohamednagy.udacity.network;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.mohamednagy.udacity.async.AsyncLoaderNetwork;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public class FetchDataAsyncLoader implements LoaderManager.LoaderCallbacks {

    private AsyncLoaderNetwork asyncLoaderNetwork;

    public FetchDataAsyncLoader(AsyncLoaderNetwork asyncLoaderNetwork){
        this.asyncLoaderNetwork = asyncLoaderNetwork;
    }

    public void start(){
        asyncLoaderNetwork.initializeNetworkLoader(this);
    }
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return asyncLoaderNetwork.createNetworkLoader();
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        asyncLoaderNetwork.networkLoaderIsFinished();
    }

    @Override
    public void onLoaderReset(Loader loader) {
        asyncLoaderNetwork = null;
    }
}
