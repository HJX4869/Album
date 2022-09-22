package com.hjx.album.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hjx.album.PhotoModel;
import com.hjx.album.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PhotoModel> mData;

    private Context mContext;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_gride_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < 0) {
            return;
        }
        if (!(holder instanceof ViewHolder)) {
            return;
        }
        Log.i("hjx",  mData.get(position).getPath());
        ImageView imageView = ((ViewHolder) holder).mImageView;
        Glide.with(mContext).load(mData.get(position).getPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {

    }

    public void bindData(List<PhotoModel> data) {
        mData = data;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;

        ViewHolder(View view) {
            super(view);
            this.mImageView = view.findViewById(R.id.photo_view);
        }
    }
}
