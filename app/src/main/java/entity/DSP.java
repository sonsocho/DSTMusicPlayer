package entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "DanhSachPhat",
        foreignKeys = @ForeignKey(
                entity = Song.class,
                parentColumns = "id_BaiHat",
                childColumns = "id_BaiHat",
                onDelete = ForeignKey.CASCADE
        )
)
public class DSP {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int STT;
    String id_BaiHat;

    Boolean phatNhac;

    public DSP(int STT, String id_BaiHat) {
        this.STT = STT;
        this.id_BaiHat = id_BaiHat;
        this.phatNhac = false;
    }

    public DSP(int STT, String id_BaiHat, Boolean phatNhac) {
        this.STT = STT;
        this.id_BaiHat = id_BaiHat;
        this.phatNhac = phatNhac;
    }

    public DSP() {
        this.phatNhac = false;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getId_BaiHat() {
        return id_BaiHat;
    }

    public void setId_BaiHat(String id_BaiHat) {
        this.id_BaiHat = id_BaiHat;
    }

    public Boolean getPhatNhac() {
        return phatNhac;
    }

    public void setPhatNhac(Boolean phatNhac) {
        this.phatNhac = phatNhac;
    }
}
