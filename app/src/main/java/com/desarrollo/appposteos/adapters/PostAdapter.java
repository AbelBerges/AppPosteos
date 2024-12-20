package com.desarrollo.appposteos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideExperiments;
import com.desarrollo.appposteos.R;
import com.desarrollo.appposteos.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private List<Post> posteos;
    public PostAdapter(List<Post> posteos){
        this.posteos = posteos;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posteos.get(position);
        holder.tvTitulo.setText(post.getTitulo());
        holder.tvDescripcion.setText(post.getDescripcion());

        if (post.getImagenes().size() > 0){
            Glide.with(holder.ivImage1.getContext())
                    .load(post.getImagenes().get(0))
                    .into(holder.ivImage1);
            holder.ivImage1.setVisibility(View.VISIBLE);
        }
        if (post.getImagenes().size() > 1){
            Glide.with(holder.ivImage2.getContext())
                    .load(post.getImagenes().get(1))
                    .into(holder.ivImage2);
            holder.ivImage2.setVisibility(View.VISIBLE);
        }
        if (post.getImagenes().size() > 2){
            Glide.with(holder.ivImage3.getContext())
                    .load(post.getImagenes().get(2))
                    .into(holder.ivImage3);
            holder.ivImage3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return posteos.size();
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion;
        ImageView ivImage1, ivImage2, ivImage3;

        public PostViewHolder(View itemView){
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ivImage1 = itemView.findViewById(R.id.ivImage1);
            ivImage2 = itemView.findViewById(R.id.ivImage2);
            ivImage3 = itemView.findViewById(R.id.ivImage3);
        }

    }
}
