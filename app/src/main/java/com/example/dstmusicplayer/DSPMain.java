package com.example.dstmusicplayer;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import conect.SongData;
import entity.DSP;
import entity.Song;


public class DSPMain extends AppCompatActivity {
    private SongData db;
    private RecyclerView recyclerView;
    private DSPAdapter adapter;
    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsp);

        db = Room.databaseBuilder(getApplicationContext(), SongData.class, "music.db")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songList = getSongListFromDatabase();

        adapter = new DSPAdapter(this, songList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(songList, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setElevation(30);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setElevation(0);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && isCurrentlyActive) {
                    viewHolder.itemView.setAlpha(0.8f);
                } else {
                    viewHolder.itemView.setAlpha(1.0f);
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        List<String> idBaiHat = getAllId();

        for(String id : idBaiHat){
            Log.d("idBaiHat", id);
        }



    }

    private List<Song> getSongListFromDatabase() {
        ArrayList<String> listDSP = getIntent().getStringArrayListExtra("listDSP");
        List<Song> songList = new ArrayList<>();

        for (String dsp : listDSP) {
            List<Song> songs = db.songDao().getSongId(dsp);

            if (songs != null && !songs.isEmpty()) {
                songList.addAll(songs);
            }
        }
        return songList;
    }

    private ArrayList<String> getAllId(){
        List<String> idList = db.dspdao().getAllId();
        ArrayList<String> idDsp = new ArrayList<>(idList);
        return idDsp;
    }
}
