package com.example.dstmusicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connectDB.SongData;
import entity.DSP;
import entity.Song;

public class CustomSongAdapter extends ArrayAdapter<Song> {
    private final Context context;
    private final List<Song> songs;
    private final SongData dspData;
    private ArrayList<String> listDSP;
    private List<String> idBaiHatList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    public CustomSongAdapter(@NonNull Context context, List<Song> songs) {
        super(context, R.layout.list_songs, songs);
        this.context = context;
        this.songs = songs;
        this.dspData = SongData.getInstance(context);
        this.listDSP = new ArrayList<>();
        this.idBaiHatList = new ArrayList<>();
        this.listener = listener;
        fetchIdBaiHat();
    }
    private void fetchIdBaiHat() {
        new Thread(() -> {
            List<String> idBaiHat = SongData.getInstance(getContext()).dspdao().getAllId();
            idBaiHatList.addAll(idBaiHat);

            ((Activity) context).runOnUiThread(() -> {
                for (String id : idBaiHatList) {
                    listDSP.add(id);
                }
            });
        }).start();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_songs, parent, false);
        }

        Song song = getItem(position);
        if (song != null) {
            TextView titleTextView = convertView.findViewById(R.id.song_name);
            TextView artistTextView = convertView.findViewById(R.id.song_artist);
            ImageView albumArtImageView = convertView.findViewById(R.id.song_image);
            ImageView properties = convertView.findViewById(R.id.img_properties);

            titleTextView.setText(song.getTenBaiHat());
            artistTextView.setText(song.getTenNgheSi());
            String songPath2 = song.getId_BaiHat();

//            convertView.setOnClickListener(v->{
//                Intent sendID = new Intent(getContext(), MainActivity.class);
//                sendID.putExtra("fileNhac", songPath2);
//                getContext().startActivity(sendID);
//            });
            convertView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(song);
                    Intent sendID = new Intent(getContext(), MainActivity.class);
                    sendID.putExtra("fileNhac", songPath2);
                    getContext().startActivity(sendID);
                }
            });

            Bitmap img = null;
            try {
                img = SongImage.getImg(songPath2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (img != null) {
                albumArtImageView.setImageBitmap(img);
            } else {
                albumArtImageView.setImageResource(R.drawable.mainlogo);
            }

            bottomDialog dialog = new bottomDialog();
            properties.setOnClickListener(view -> {
                dialog.showBottomDialog(getContext());
            });
        }

        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(song);
            }
        });


        //propertis click
        ImageView img_properties = convertView.findViewById(R.id.img_properties);
        img_properties.setOnClickListener(view -> {
            
        });
        return convertView;
    }

    public ArrayList<String> getListDSP() {
        Toast.makeText(getContext(), "Oke la", Toast.LENGTH_SHORT ).show();
        return listDSP;
    }



}
