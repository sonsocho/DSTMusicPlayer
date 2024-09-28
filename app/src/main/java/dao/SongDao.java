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

    @Query("UPDATE BaiHat SET thoiGianNghe = :thoiGian WHERE id_BaiHat = :id")
    void updateThoiGianNghe(String id, String thoiGian);

    @Query("UPDATE BaiHat SET soLanNghe = soLanNghe + 1 WHERE id_BaiHat = :id")
    void incrementSoLanNghe(String id);

    @Query("SELECT * FROM BaiHat WHERE tenBaiHat LIKE :query OR tenNgheSi LIKE :query OR album LIKE :query")
    List<Song> searchSongs(String query);


}
