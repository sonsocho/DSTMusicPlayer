package entity;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.TimerTask;

@Entity(tableName = "BaiHat"
//        foreignKeys = @ForeignKey(
//                entity = Playlist.class,
//                parentColumns = "id_Playlist",
//                childColumns = "id_Playlist",
//                onDelete = ForeignKey.CASCADE
//        )

)
public class Song {
    @PrimaryKey
    @NonNull
    private String id_BaiHat;

    private String tenBaiHat;
    private String tenNgheSi;
    private String namPhatHanh;
    private String album;
    private int soLanNghe;
    private String img;

    public String getThoiGianNghe() {
        return thoiGianNghe;
    }

    public void setThoiGianNghe(String thoiGianNghe) {
        this.thoiGianNghe = thoiGianNghe;
    }

    private String thoiGianNghe;
//    private String id_Playlist; //xóa



    public Song(@NonNull String id_BaiHat, String tenBaiHat, String tenNgheSi, String namPhatHanh, String album, int soLanNghe, String img, String thoiGianNghe) {
        this.id_BaiHat = id_BaiHat;
        this.tenBaiHat = tenBaiHat;
        this.tenNgheSi = tenNgheSi;
        this.namPhatHanh = namPhatHanh;
        this.album = album;
        this.soLanNghe = soLanNghe;
        this.img = img;
        this.thoiGianNghe = thoiGianNghe;
//        this.id_Playlist = id_Playlist;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

//    public String getId_Playlist() {
//        return id_Playlist;
//    }
//
//    public void setId_Playlist(String id_Playlist) {
//        this.id_Playlist = id_Playlist;
//    }

    public String getId_BaiHat() {
        return id_BaiHat;
    }

    public void setId_BaiHat(String id_BaiHat) {
        this.id_BaiHat = id_BaiHat;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getTenNgheSi() {
        return tenNgheSi;
    }

    public void setTenNgheSi(String tenNgheSi) {
        this.tenNgheSi = tenNgheSi;
    }

    public String getNamPhatHanh() {
        return namPhatHanh;
    }

    public void setNamPhatHanh(String namPhatHanh) {
        this.namPhatHanh = namPhatHanh;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getSoLanNghe() {
        return soLanNghe;
    }

    public void setSoLanNghe(int soLanNghe) {
        this.soLanNghe = soLanNghe;
    }



}
