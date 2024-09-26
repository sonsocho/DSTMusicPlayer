package entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "DanhSachPhat",
        foreignKeys = @ForeignKey(
        entity = Song.class,
        parentColumns = "id_BaiHat",
        childColumns = "ID_BaiHat",
        onDelete = ForeignKey.CASCADE
        )
)
public class DSP {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int STT;

    String ID_BaiHat;

    public DSP(int STT, String ID_BaiHat) {
        this.STT = STT;
        this.ID_BaiHat = ID_BaiHat;
    }

    public DSP() {

    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getID_BaiHat() {
        return ID_BaiHat;
    }

    public void setID_BaiHat(String ID_BaiHat) {
        this.ID_BaiHat = ID_BaiHat;
    }
}

