package com.example.dstmusicplayer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dstmusicplayer.R;

public class bottomDialog {
    public void showBottomDialog(Context context) {
        final Dialog dialog = new Dialog(context); // Sử dụng context thay vì 'this'
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout phatketiep = dialog.findViewById(R.id.phatketiep);
        LinearLayout themvaoplaylist = dialog.findViewById(R.id.themvaoplaylist);
        LinearLayout chitiet = dialog.findViewById(R.id.chitiet);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        phatketiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        themvaoplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                // Tạo dialog mới để hiển thị chi tiết
                final Dialog detailDialog = new Dialog(context);
                detailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                detailDialog.setContentView(R.layout.info_dialog);

                // Ánh xạ các phần tử từ layout dialog_chitiet
                EditText edtSongName = detailDialog.findViewById(R.id.edtsongname);
                EditText edtArtistName = detailDialog.findViewById(R.id.edtartistname);
                EditText edtReleaseYear = detailDialog.findViewById(R.id.edtreleaseyear);
                EditText edtDuration = detailDialog.findViewById(R.id.edtduration);
                EditText edtPlayCount = detailDialog.findViewById(R.id.edtplaycount);
                Button btnEdit = detailDialog.findViewById(R.id.btnedit);
                Button btnSave = detailDialog.findViewById(R.id.btnsave);

                // Chỉnh sửa
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cho phép chỉnh sửa các trường
                        edtSongName.setEnabled(true);
                        edtArtistName.setEnabled(true);
                        edtReleaseYear.setEnabled(true);
                        edtDuration.setEnabled(true);
                        edtPlayCount.setEnabled(true);

                        // Hiển thị nút Lưu
                        btnSave.setVisibility(View.VISIBLE);
                    }
                });

                // Lưu thông tin khi nhấn nút Lưu
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Ở đây, bạn có thể thêm chức năng lưu thông tin
                        detailDialog.dismiss();
                    }
                });

                // Hiển thị dialog chi tiết
                detailDialog.show();
                detailDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Đảm bảo style này tồn tại
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
