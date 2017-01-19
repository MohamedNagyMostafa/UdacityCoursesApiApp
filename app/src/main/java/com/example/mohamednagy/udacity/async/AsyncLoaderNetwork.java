package com.example.mohamednagy.udacity.async;

import android.support.v4.app.LoaderManager;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public interface AsyncLoaderNetwork {
    void initializeNetworkLoader(LoaderManager.LoaderCallbacks<Void> loaderCallbacks);
    AsyncNetworkLoader createNetworkLoader();
    void networkLoaderIsFinished();
}
