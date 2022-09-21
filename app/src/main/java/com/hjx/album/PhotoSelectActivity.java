package com.hjx.album;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.hjx.album.adapter.PhotoLoad;
import com.hjx.album.adapter.RecyclerViewAdapter;

import java.util.List;

public class PhotoSelectActivity extends AppCompatActivity {

    private static final String TAG = "PhotoSelectActivity";

    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;

    private Context mContext;

    private LoaderManager.LoaderCallbacks<List<PhotoModel>> mPhotoLoadCallback = new LoaderManager.LoaderCallbacks<List<PhotoModel>>() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @NonNull
        @Override
        public Loader<List<PhotoModel>> onCreateLoader(int id, @Nullable Bundle args) {
            return new PhotoLoad(mContext);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<PhotoModel>> loader, List<PhotoModel> data) {
            mAdapter.bindData(data);
            Log.i(TAG, "photo number is:" + data.size());
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<PhotoModel>> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
        mContext = this;
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        LoaderManager.getInstance(this).initLoader(1, null, mPhotoLoadCallback);
    }
}
