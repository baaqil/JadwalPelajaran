package com.JonesRandom.JadwalPelajaran;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class HeaderSetting extends AppCompatActivity {

    Toolbar toolbar;
    EditText settNamaSiswa, setKelas;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_setting);

        preferences = getSharedPreferences("profil", MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.Jadwal_Toolbar);
        toolbar.setTitle("Edit Profil");

        setSupportActionBar(toolbar);

        settNamaSiswa = (EditText) findViewById(R.id.setNamaSiswa);
        setKelas = (EditText) findViewById(R.id.setKelas);

        loadSett();

    }

    public void loadSett() {
        settNamaSiswa.setText(preferences.getString("NAMA_SISWA", "Nama Siswa"));
        setKelas.setText(preferences.getString("KELAS", "Kelas"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.simpanProfil) {

            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("NAMA_SISWA", settNamaSiswa.getText().toString());
            editor.putString("KELAS", setKelas.getText().toString());

            editor.apply();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
