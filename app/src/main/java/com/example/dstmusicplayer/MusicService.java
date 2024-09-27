package com.example.dstmusicplayer;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new MusicBinder();
    private int currentPosition = 0;
    private String currentFilePath;
    private String currentSongTitle;
    private String currentSongArtist;
    private Bitmap currentAlbumArt;
    private String filePath;

    private float[] playbackSpeeds = {0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f};
    private int currentSpeedIndex = 3;

    public void setPlaybackSpeed(int index) {
        if (mediaPlayer != null && index >= 0 && index < playbackSpeeds.length) {
            currentSpeedIndex = index;
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(playbackSpeeds[currentSpeedIndex]));
        }
    }

    private boolean isLooping = false; // Biến để kiểm tra chế độ phát lại

    // Phương thức để thiết lập chế độ phát lại
    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }


    public void setFilePath(String path) {
        this.filePath = path;
        this.currentFilePath = path;

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateSongInfo(filePath);
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startMusic(String filePath) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            this.filePath = filePath;
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
                currentFilePath = filePath;
                updateSongInfo(filePath);
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(currentPosition);
                mediaPlayer.start();
            }
        }
    }

    private void updateSongInfo(String filePath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);

            currentSongTitle = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if (currentSongTitle == null) {
                currentSongTitle = "Unknown Title";
            }

            currentSongArtist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (currentSongArtist == null) {
                currentSongArtist = "Unknown Artist";
            }

            byte[] art = retriever.getEmbeddedPicture();
            if (art != null) {
                currentAlbumArt = BitmapFactory.decodeByteArray(art, 0, art.length);
            } else {
                currentAlbumArt = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentSongFilePath() {
        return currentFilePath;
    }

    public String getCurrentSongTitle() {
        return currentSongTitle;
    }

    public String getCurrentSongArtist() {
        return currentSongArtist;
    }

    public Bitmap getCurrentAlbumArt() {
        return currentAlbumArt;
    }


    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    public void playNext(){

    }

    public void playPrevious(){

    }


    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            currentPosition = 0;
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }


    public void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
