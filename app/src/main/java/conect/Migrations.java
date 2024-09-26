package conect;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class Migrations {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thay đổi cấu trúc cơ sở dữ liệu từ version 1 lên version 2
            database.execSQL("CREATE TABLE IF NOT EXISTS `DanhSachPhat` (`STT` INTEGER NOT NULL, `ID_BaiHat` TEXT, PRIMARY KEY(`STT`))");
        }
    };
}

