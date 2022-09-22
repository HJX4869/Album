package com.hjx.album;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class PhotoLoad extends AsyncTaskLoader<List<PhotoModel>> {

    private static final String[] PHOTO_PROJECTION =
        new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.WIDTH, MediaStore.Images.ImageColumns.HEIGHT};


    private static final String DESC = " DESC";

    public PhotoLoad(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onReset() {

    }

    @Override
    protected void onAbandon() {

    }

    @Override
    public void deliverResult(List<PhotoModel> data) {
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public List<PhotoModel> loadInBackground() {
        List<PhotoModel> data = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PHOTO_PROJECTION, null, null, MediaStore.Images.Media._ID + DESC);
            if (cursor == null) {
                return data;
            }
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int width  = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
                int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                PhotoModel photoModel = new PhotoModel.Builder().
                    setId(id).
                    setPath(path).
                    setWidth(width).
                    setHeight(height).
                    build();
                data.add(photoModel);
            } while (cursor.moveToNext());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }
}
