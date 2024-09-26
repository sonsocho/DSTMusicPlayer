package com.example.dstmusicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import entity.Song;

public class DSPAdapter extends RecyclerView.Adapter<DSPAdapter.SongViewHolder> {
    private final Context context;
    private final List<Song> songs;

    public DSPAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dspadapter, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.nameTextView.setText(song.getTenBaiHat());
        holder.artistTextView.setText(song.getTenNgheSi());

        String songPath = song.getId_BaiHat();
        Bitmap img = null;
        try {
            img = SongImage.getImg(songPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (img != null) {
            holder.imageView.setImageBitmap(img);
        } else {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_image));
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, artistTextView;
        ImageView imageView;

        SongViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.song_name);
            artistTextView = itemView.findViewById(R.id.song_artist);
            imageView = itemView.findViewById(R.id.song_image);
        }
    }
}
