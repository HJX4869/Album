package com.hjx.album.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hjx.album.PhotoModel;
import com.hjx.album.R;
import com.hjx.album.PhotoLoad;
import com.hjx.album.adapter.RecyclerViewAdapter;

import java.util.List;

public class PhotoSelectActivity extends AppCompatActivity {

    private static final String TAG = "PhotoSelectActivity";

    private static int SPAN_COUNT = 4;

    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;

    private Context mContext;

    private ComponentCallbacks2 mCallback = new ComponentCallbacks2() {
        @Override
        public void onTrimMemory(int level) {
            PhotoSelectActivity.super.onTrimMemory(level);
            if (level == TRIM_MEMORY_RUNNING_MODERATE || level == TRIM_MEMORY_RUNNING_LOW
                || level == TRIM_MEMORY_RUNNING_CRITICAL) {
                Glide.get(mContext).trimMemory(level);
            }
            if (level == TRIM_MEMORY_UI_HIDDEN) {
                Glide.get(mContext).clearMemory();
            }
        }

        @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
        }

        @Override
        public void onLowMemory() {
            PhotoSelectActivity.super.onLowMemory();
            Glide.get(mContext).clearMemory();
        }
    };

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

    private RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            int column = position % SPAN_COUNT;
            int space = (int)getApplication().getResources().getDisplayMetrics().density * 2;
            outRect.left = column * space / SPAN_COUNT;
            outRect.right = space - (column + 1) * space / SPAN_COUNT;
            outRect.bottom = space;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
        mContext = this;
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(mItemDecoration);
        LoaderManager.getInstance(this).initLoader(1, null, mPhotoLoadCallback);
        registerComponentCallbacks(mCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterComponentCallbacks(mCallback);
    }
}
