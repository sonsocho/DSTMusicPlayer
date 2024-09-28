package com.example.dstmusicplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    private utf8 utf8;
    private ImageView albumArtImageView, properties;
    private TextView titleTextView, artistTextView;


    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    public CustomSongAdapter(@NonNull Context context, List<Song> songs, OnItemClickListener listener) {
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

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_songs, parent, false);
        }

        Song song = getItem(position);
        if (song != null) {
            titleTextView = convertView.findViewById(R.id.song_name);
            artistTextView = convertView.findViewById(R.id.song_artist);
            albumArtImageView = convertView.findViewById(R.id.song_image);
            properties = convertView.findViewById(R.id.img_properties);

            titleTextView.setText(song.getTenBaiHat());
            titleTextView.setSingleLine(true);
            titleTextView.setEllipsize(TextUtils.TruncateAt.END);
            artistTextView.setText(song.getTenNgheSi());
            artistTextView.setSingleLine(true);
            artistTextView.setEllipsize(TextUtils.TruncateAt.END);
            String songPath2 = song.getId_BaiHat();

            convertView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(song);
                    Intent sendID = new Intent(getContext(), MainActivity.class);
                    sendID.putExtra("fileNhac", songPath2);
                    getContext().startActivity(sendID);
                }
            });


            Bitmap currentAlbumArt;
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(utf8.decodeString(songPath2));
            byte[] art = retriever.getEmbeddedPicture();
            if (art != null) {
                currentAlbumArt = BitmapFactory.decodeByteArray(art, 0, art.length);
                albumArtImageView.setImageBitmap(currentAlbumArt);
            } else {
                currentAlbumArt = null;
                albumArtImageView.setImageResource(R.drawable.default_item);
            }



            properties.setOnClickListener(view -> {
//                dialog.showBottomDialog(getContext());

                if (songPath2 != null) {
//                    new Thread(() -> {
//                        try {
//                            dspData.dspdao().insertDSP(songPath2);
//                            Log.d("InsertDSP", songPath2);
//                        } catch (Exception e) {
//                            Log.e("InsertDSP", "Error inserting DSP: ", e);
//                        }
//                    }).start();
                   ArrayList<String> a = MainActivity.getDSPList();
                   a.add(songPath2);
                    MainActivity.setDSPList(a);

                } else {
                    Toast.makeText(context, "Song path is null!", Toast.LENGTH_SHORT).show();
                }

            });

        }


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
