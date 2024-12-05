package com.desarrollo.appposteos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.desarrollo.appposteos.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<String> imageUrls;
    private Context context;

    public ImageAdapter(List<String> imageUrls, Context context){
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        String imgUrl = imageUrls.get(position);
        Glide.with(context).load(imgUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
    public void updateImages(List<String> newImgsUrl){
        this.imageUrls = newImgsUrl;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ImageViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

}
