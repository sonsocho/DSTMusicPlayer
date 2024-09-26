package dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import entity.DSP;

@Dao
public interface DSPDao {
    @Insert
    Void insertDSP(DSP DSP);

    @Query("SELECT * FROM DanhSachPhat")
    List<DSP> getAllDSP();

   @Query("SELECT id_BaiHat FROM DanhSachPhat")
    List<String> getAllId();
}