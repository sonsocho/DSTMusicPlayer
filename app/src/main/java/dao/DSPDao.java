package dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entity.DSP;

@Dao
public interface DSPDao {
    @Insert
    Void insert(DSP DSP);

    @Query("SELECT * FROM DanhSachPhat")
    List<DSP> getAllDSP();

   @Query("SELECT id_BaiHat FROM DanhSachPhat")
    List<String> getAllId();

   @Query("INSERT INTO DanhSachPhat(id_BaiHat) VALUES(:id)")
    void insertDSP(String id);

   @Query("DELETE FROM DanhSachPhat")
    void deleteDSP();

   @Query("SELECT id_BaiHat FROM DanhSachPhat WHERE PhatNhac = True")
    String getPhatNhac();
}