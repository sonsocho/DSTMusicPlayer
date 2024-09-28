package com.example.dstmusicplayer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import connectDB.SongData;
import entity.DSP;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_ADD = 29;
    private static final int RESULT_LIST = 7;

    private static final Random random = new Random();
    private addSong permissionManager;
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    private boolean select;

    private static final int REQUEST_PERMISSION = 123;
    private boolean isServiceBound = false;
    private MusicService musicService;
    private MiniPlayerFragment miniPlayerFragment;
    private utf8 utf8;
    private SongData db;
<<<<<<< HEAD
    public static ArrayList<String> DSPList= new ArrayList<>();;
    public static String idPhatNhac;

=======
    public static ArrayList<String> DSPList;
>>>>>>> ec8d59f179719284ac32782f1f43a26a6abd2660


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.hasExtra("fileNhac")) {
            String songID = intent.getStringExtra("fileNhac");
            String fileGoc = utf8.decodeString(songID);
            miniPlayerFragment.updateUI(fileGoc);
            if (isServiceBound) {
                musicService.stopMusic();
                musicService.startMusic(fileGoc);
                if (miniPlayerFragment != null) {
                    miniPlayerFragment.updateUI(fileGoc);
                }
            }
            openPhatNhacActivity(fileGoc);
        }
        if (intent != null && intent.hasExtra("songList")) {
            ArrayList<String> songList = intent.getStringArrayListExtra("songList");
            if (songList != null && !songList.isEmpty()) {
                // Gọi phương thức để thiết lập danh sách bài hát trong MusicService
                musicService.setPlaylist(songList,0);
                musicService.startMusic(songList.get(0)); // Bắt đầu phát bài đầu tiên
            }
            openDanhSachPhatNhacActivity(songList);
        }
    }

    private void openDanhSachPhatNhacActivity(ArrayList<String> songIdList){
        Intent intent = new Intent(this, PhatNhacActivity.class);
        intent.putStringArrayListExtra("songList", songIdList);
        intent.putExtra("currentSongIndex", 0); // Vị trí bài hát hiện tại
        startActivity(intent);
    }




    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        //check permission
        select = false;
        permissionManager = new addSong(this, getApplicationContext(), select);
        permissionManager.requestPermission();
        db = SongData.getInstance(this);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            }else if (itemId == R.id.library) {
                replaceFragment(new ThuvienFragment());
            } else if (itemId == R.id.playlist) {
                replaceFragment(new playlistFragment());
            }
            return true;
        });
        createDSPList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select = true;
                permissionManager = new addSong(MainActivity.this, getApplicationContext(), select);
                permissionManager.requestPermission();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu từ tệp XML
        getMenuInflater().inflate(R.menu.search, menu);


        return true; // Trả về true để menu hiển thị
    }
    private void createDSPList() {
        new Thread(() -> {
            try {
                List<String> idBaiHat = db.dspdao().getAllId();
                DSPList = new ArrayList<>(idBaiHat);


            }catch (Exception e) {
                Log.d("azcv", "lỗi Mainactivity");
            }
        }).start();
    }

    private void openPhatNhacActivity(String fileGoc) {
        Intent intentPhatNhac = new Intent(MainActivity.this, PhatNhacActivity.class);
        intentPhatNhac.putExtra("fileNhac", fileGoc);
        startActivity(intentPhatNhac);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addSong.REQUEST_CODE_SONG_LIST && resultCode == RESULT_OK && data != null) {
            ArrayList<String> selectedSongs = data.getStringArrayListExtra("selectedSongs");
            Toast.makeText(this, "REQUEST_CODE_SONG_LIST", Toast.LENGTH_SHORT).show();

        } else if (resultCode == RESULT_ADD) {
            bottomNavigationView.setSelectedItemId(R.id.library);
            Toast.makeText(this, "RESULT_ADD", Toast.LENGTH_SHORT).show();

        }else if (resultCode == RESULT_LIST) {
            Intent intent = getIntent();
            ArrayList<String> DSPList = intent.getStringArrayListExtra("DSPList");
            for(String a : DSPList){
                Log.d("azcvmain", a);
            }
            Toast.makeText(this, "RESULT_LIST", Toast.LENGTH_SHORT).show();
        }else {
            Log.e("MusicFragment", "Request code or result code not matched");
            Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            isServiceBound = true;

            if (miniPlayerFragment != null) {
                miniPlayerFragment.updateUI(musicService.getCurrentSongFilePath());
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (miniPlayerFragment != null && isServiceBound) {
            String currentSongFilePath = musicService.getCurrentSongFilePath();
            miniPlayerFragment.updateUI(currentSongFilePath);
        }
    }


    public static ArrayList<String> getDSPList() {
        return DSPList;
    }

    public static void setDSPList(ArrayList<String> DSPList) {
        MainActivity.DSPList = DSPList;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        miniPlayerFragment = new MiniPlayerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.miniPlayerContainer, miniPlayerFragment)
                .commit();
        if (DSPList != null) {
            for (String idBaiHat : DSPList) {
                Log.d("aqer", idBaiHat);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        if (isFinishing() && isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(() -> {
            db.dspdao().deleteDSP();
            for (String idBaiHat : DSPList) {
                db.dspdao().insertDSP(idBaiHat);
            }
        }).start();

    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.phatketiep);
        LinearLayout shortsLayout = dialog.findViewById(R.id.themvaoplaylist);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Upload a Video is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        shortsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Create a short is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Go live is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    public static ArrayList<String> shuffle(ArrayList<String> listShuffle) {
        if (listShuffle.isEmpty()) {
            return null;
        }

        ArrayList<String> originalList = new ArrayList<>(listShuffle);

        do {
            Collections.shuffle(listShuffle, random);
        } while (!isDifferentFromOriginal(listShuffle, originalList));

        return listShuffle;
    }

    private static boolean isDifferentFromOriginal(ArrayList<String> shuffledList, ArrayList<String> originalList) {
        for (int i = 0; i < shuffledList.size(); i++) {
            if (shuffledList.get(i).equals(originalList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void setIdPhatNhac(String idPhatNhac) {
        MainActivity.idPhatNhac = idPhatNhac;
    }
}
