package com.JonesRandom.JadwalPelajaran;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.JonesRandom.JadwalPelajaran.JadwalFragment.Jumat;
import com.JonesRandom.JadwalPelajaran.JadwalFragment.Kamis;
import com.JonesRandom.JadwalPelajaran.JadwalFragment.Rabu;
import com.JonesRandom.JadwalPelajaran.JadwalFragment.Sabtu;
import com.JonesRandom.JadwalPelajaran.JadwalFragment.Selasa;
import com.JonesRandom.JadwalPelajaran.JadwalFragment.Senin;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DBHandler handler;
    SharedPreferences preferences;
    View headerItem;

    ///NavigationItem

    ImageButton headerSetting;
    TextView txtNamaSiswa, txtKelas;
    ImageView pic;

    Bitmap prosesBitmap;
    Bitmap hasilBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        handler = new DBHandler(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_jadwal, new Senin()).commit();
        }

        toolbar = (Toolbar) findViewById(R.id.Jadwal_Toolbar);
        toolbar.setTitle("Jadwal Hari Senin");
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("temp", MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerJadwal);
        navigationView = (NavigationView) findViewById(R.id.Navigation_Jadwal);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.d_open, R.string.d_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigasiDiKlik(item);
                return true;
            }
        });

        /// Navigation Item

        headerItem = navigationView.inflateHeaderView(R.layout.header_drawer_layout);

        txtNamaSiswa = (TextView) headerItem.findViewById(R.id.txtNamaSiswa);
        txtKelas = (TextView) headerItem.findViewById(R.id.txtKelas);

        pic = (ImageView)headerItem.findViewById(R.id.pic);

        headerSetting = (ImageButton) headerItem.findViewById(R.id.headerSetting);
        headerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeaderSetting.class));
            }
        });

        loadHeaderItem();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void navigasiDiKlik(MenuItem item) {
        int ID = item.getItemId();
        Fragment fragment = null;
        Class fragmentJadwal = null;
        SharedPreferences.Editor editor = preferences.edit();
        String NOMOR = "nomor_halaman";
        switch (ID) {
            case R.id.menuJadwalSenin:
                fragmentJadwal = Senin.class;
                editor.putInt(NOMOR, 1);
                break;
            case R.id.menuJadwalSelasa:
                fragmentJadwal = Selasa.class;
                editor.putInt(NOMOR, 2);
                break;
            case R.id.menuJadwalRabu:
                fragmentJadwal = Rabu.class;
                editor.putInt(NOMOR, 3);
                break;
            case R.id.menuJadwalKamis:
                fragmentJadwal = Kamis.class;
                editor.putInt(NOMOR, 4);
                break;
            case R.id.menuJadwalJumat:
                fragmentJadwal = Jumat.class;
                editor.putInt(NOMOR, 5);
                break;
            case R.id.menuJadwalSabtu:
                fragmentJadwal = Sabtu.class;
                editor.putInt(NOMOR, 6);
                break;
        }

        try {
            if (fragmentJadwal != null) {
                fragment = (Fragment) fragmentJadwal.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_jadwal, fragment).commit();
        toolbar.setTitle(item.getTitle());
        drawerLayout.closeDrawers();
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tambah_jadwal:
                startActivity(new Intent(MainActivity.this, TambahJadwal.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadHeaderItem() {
        SharedPreferences preferences = getSharedPreferences("profil", MODE_PRIVATE);
        txtNamaSiswa.setText(preferences.getString("NAMA_SISWA", "Nama Siswa"));
        txtKelas.setText(preferences.getString("KELAS", "Kelas"));
        try {
            String Lokasi = Environment.getExternalStorageDirectory() + "/Android/data/com.JonesRandom.JadwalPelajaran/file/pic/ProfilPic.png";
            prosesBitmap = BitmapFactory.decodeFile(Lokasi);
            if (prosesBitmap.getWidth() >= prosesBitmap.getHeight()) {
                hasilBitmap = Bitmap.createBitmap(prosesBitmap, prosesBitmap.getWidth() / 2 - prosesBitmap.getHeight() / 2, 0, prosesBitmap.getHeight(), prosesBitmap.getHeight());
            } else {
                hasilBitmap = Bitmap.createBitmap(prosesBitmap, 0, prosesBitmap.getHeight() / 2 - prosesBitmap.getWidth() / 2, prosesBitmap.getWidth(), prosesBitmap.getWidth());
            }

            PicBulat picBulat = new PicBulat(hasilBitmap);
            pic.setImageDrawable(picBulat);
        } catch (Exception e) {
            hasilBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            PicBulat picBulat = new PicBulat(hasilBitmap);
            pic.setImageDrawable(picBulat);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        int nomor_Halaman = preferences.getInt("nomor_halaman", 1);

        Fragment fragment = null;
        Class fragmentJadwal = null;

        switch (nomor_Halaman) {
            case 1:
                fragmentJadwal = Senin.class;
                toolbar.setTitle("Jadwal Hari Senin");
                break;
            case 2:
                fragmentJadwal = Selasa.class;
                toolbar.setTitle("Jadwal Hari Selasa");
                break;
            case 3:
                fragmentJadwal = Rabu.class;
                toolbar.setTitle("Jadwal Hari Rabu");
                break;
            case 4:
                fragmentJadwal = Kamis.class;
                toolbar.setTitle("Jadwal Hari Kamis");
                break;
            case 5:
                fragmentJadwal = Jumat.class;
                toolbar.setTitle("Jadwal Hari Jumat");
                break;
            case 6:
                fragmentJadwal = Sabtu.class;
                toolbar.setTitle("Jadwal Hari Sabtu");
                break;
        }

        try {
            if (fragmentJadwal != null) {
                fragment = (Fragment) fragmentJadwal.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_jadwal, fragment).commit();
        loadHeaderItem();
    }
}
