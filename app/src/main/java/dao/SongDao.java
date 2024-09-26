package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entity.Song;

@Dao
public interface SongDao {
    @Insert
    void insertSong(Song song);

    @Update
    void updateSong(Song song);

    @Delete
    void deleteSong(Song song);

    @Query("SELECT * FROM BaiHat")
    List<Song> getAllSongs();

    @Query("SELECT * FROM BaiHat Where id_BaiHat = :id")
    List<Song> getSongId(String id);

//    @Query("UPDATE BaiHat SET id_Playlist = :id_playlist Where id_BaiHat = :id_BaiHat ")
//    void updatePlaylist(String id_playlist, String id_BaiHat);
}
