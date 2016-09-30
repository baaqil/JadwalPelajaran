package com.JonesRandom.JadwalPelajaran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HeaderSetting extends AppCompatActivity {

    Toolbar toolbar;
    EditText settNamaSiswa, setKelas;
    ImageView pic;
    SharedPreferences preferences;

    Bitmap prosesBitmap;
    Bitmap hasilBitmap = null;

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

        pic = (ImageView) findViewById(R.id.pic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(i, "selected picture"), 1);
            }
        });

        loadSett();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:

                Uri uri = data.getData();
                String Lokasi = getPath(uri);

                if (Lokasi == null) {
                    Lokasi = uri.getPath();
                } else {
                    try {
                        Bitmap bitmap = bitmapScaling(Lokasi, 200, 200);
                        File path = Environment.getExternalStorageDirectory();
                        File Folder = new File(path.getAbsolutePath() + "/Android/data/com.JonesRandom.JadwalPelajaran/file/pic");
                        Folder.mkdirs();
                        File file = new File(Folder, "ProfilPic.png");
                        FileOutputStream stream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                        stream.flush();
                        stream.close();
                        if (bitmap.getWidth() >= bitmap.getHeight()) {
                            hasilBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0, bitmap.getHeight(), bitmap.getHeight());
                        } else {
                            hasilBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2, bitmap.getWidth(), bitmap.getWidth());
                        }

                        PicBulat picBulat = new PicBulat(hasilBitmap);
                        pic.setImageDrawable(picBulat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void loadSett() {
        settNamaSiswa.setText(preferences.getString("NAMA_SISWA", "Nama Siswa"));
        setKelas.setText(preferences.getString("KELAS", "Kelas"));
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
            hasilBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            PicBulat picBulat = new PicBulat(hasilBitmap);
            pic.setImageDrawable(picBulat);
        }
    }

    public Bitmap bitmapScaling(String path, int lebar, int tinggi) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        Bitmap bmpSize = BitmapFactory.decodeFile(path);
        Bitmap bmp = BitmapFactory.decodeFile(path, opt);
        int rasioTinggi = (int) Math.ceil(opt.outHeight / (float) tinggi);
        int rasioLebar = (int) Math.ceil(opt.outWidth / (float) lebar);

        if (rasioTinggi > 1 || rasioLebar > 1) {
            if (rasioTinggi > rasioLebar) {
                opt.inSampleSize = rasioTinggi;
            } else {
                opt.inSampleSize = rasioLebar;
            }
        }
        opt.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, opt);
        Log.d("Ukuran Asli", "doInBackground: " + bmpSize.getHeight() + " & " + bmpSize.getWidth());
        Log.d("Ukuran Jadi", "doInBackground: " + bmp.getHeight() + " & " + bmp.getWidth());
        return bmp;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public String getPath(Uri u) {
        String path = null;
        String[] imageData = {MediaStore.Images.Media.DATA};
        Cursor cek = getContentResolver().query(u, imageData, null, null, null, null);

        if (cek.moveToFirst()) {
            int nomor = cek.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cek.getString(nomor);
        }
        cek.close();
        return path;
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
