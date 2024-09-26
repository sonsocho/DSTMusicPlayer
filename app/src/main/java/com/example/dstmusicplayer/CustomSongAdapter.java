package com.example.dstmusicplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import conect.SongData;
import dao.DSPDao;
import entity.DSP;
import entity.Song;

public class CustomSongAdapter extends ArrayAdapter<Song> {
    private final Context context;
    private final List<Song> songs;
    private final SongData dspData;
    private ArrayList<String> listDSP;
    private List<String> idBaiHatList;

    public CustomSongAdapter(@NonNull Context context, List<Song> songs) {
        super(context, R.layout.list_songs, songs);
        this.context = context;
        this.songs = songs;
        this.dspData = SongData.getInstance(context);
        this.listDSP = new ArrayList<>();
        this.idBaiHatList = new ArrayList<>();
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

            if (!song.getImg().isEmpty()) {
                Bitmap img = SongImage.stringToBitmap(song.getImg());
                albumArtImageView.setImageBitmap(img);
            } else {
                albumArtImageView.setImageResource(R.drawable.default_image);
            }


            properties.setOnClickListener(view -> {
                String songPath = song.getId_BaiHat();
                Toast.makeText(getContext(), songPath, Toast.LENGTH_SHORT ).show();
                listDSP.add(songPath);
                DSP dsp = new DSP();
                dsp.setID_BaiHat(songPath);
                new Thread(() -> dspData.dspdao().insertDSP(dsp) );
            });
        }

        return convertView;
    }

    public ArrayList<String> getListDSP() {
        Toast.makeText(getContext(), "Oke la", Toast.LENGTH_SHORT ).show();
        return listDSP;
    }



}
