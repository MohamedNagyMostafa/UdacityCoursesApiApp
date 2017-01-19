package com.example.mohamednagy.udacity.async;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.mohamednagy.udacity.network.FetchDataFromNetwork;

import java.io.IOException;

/**
 * Created by mohamednagy on 12/3/2016.
 */
public class AsyncNetworkLoader extends AsyncTaskLoader<Void>{


    public AsyncNetworkLoader(Context context) {
        super(context);
    }

    @Override
    public Void loadInBackground() {
        try {
            FetchDataFromNetwork.FetchDataAndInsertToDatabase(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
