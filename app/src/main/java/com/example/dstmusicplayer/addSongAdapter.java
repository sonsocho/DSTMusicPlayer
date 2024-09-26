package com.example.dstmusicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import entity.Song;

public class addSongAdapter extends ArrayAdapter<Song> {
    private final ArrayList<Song> songList;
    private final ArrayList<Song> selectedSongs = new ArrayList<>();
    private ImageView imgIcon;

    public addSongAdapter(@NonNull Context context, @NonNull ArrayList<Song> objects) {
        super(context, 0, objects);
        songList = objects;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_songs, parent, false);
        }

        Song song = getItem(position);
        if (song != null) {
            TextView titleTextView = convertView.findViewById(R.id.BaiHat);
            TextView artistTextView = convertView.findViewById(R.id.CaSi);
            ImageView albumArtImageView = convertView.findViewById(R.id.img);
            CheckBox selectCheckBox = convertView.findViewById(R.id.selectCheckBox);

            titleTextView.setText(song.getTenBaiHat());
            artistTextView.setText(song.getTenNgheSi());
            if (song.getImg() != "") {
                Bitmap img = SongImage.stringToBitmap(song.getImg());
                albumArtImageView.setImageBitmap(img);
            } else {
                albumArtImageView.setImageResource(R.drawable.default_image);
            }

            selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedSongs.add(song);
                } else {
                    selectedSongs.remove(song);
                }
            });
        }
//        CheckBox selectCheckBox = convertView.findViewById(R.id.selectCheckBox);
//        ImageView imgIcon = convertView.findViewById(R.id.img_properties);
//        imgIcon.setOnClickListener(view -> {
//            // Hiển thị Toast với thông tin của bài hát hiện tại
//            Toast.makeText(getContext(), "Bài hát: " + song.getTenBaiHat(), Toast.LENGTH_SHORT).show();
//        });

        // Checkbox logic
//        selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                selectedSongs.add(song);
//            } else {
//                selectedSongs.remove(song);
//            }
//        });
        return convertView;
    }

    public ArrayList<Song> getSelectedSongs() {
        return selectedSongs;
    }
}