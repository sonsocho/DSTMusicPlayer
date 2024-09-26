package entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "Luu")

public class LuuPlaylist {
    private String ID_Playlist;
    private String ID_BaiHat;

    public String getID_Playlist() {
        return ID_Playlist;
    }

    public void setID_Playlist(String ID_Playlist) {
        this.ID_Playlist = ID_Playlist;
    }

    public String getID_BaiHat() {
        return ID_BaiHat;
    }

    public void setID_BaiHat(String ID_BaiHat) {
        this.ID_BaiHat = ID_BaiHat;
    }
}
