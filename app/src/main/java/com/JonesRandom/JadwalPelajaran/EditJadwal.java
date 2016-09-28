package com.JonesRandom.JadwalPelajaran;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditJadwal extends AppCompatActivity implements View.OnTouchListener {

    Spinner spinnerHari, spinnerPembagianWaktu;
    ArrayAdapter<String> adapterHari;
    ArrayAdapter<String> adapterPembagianWaktu;

    EditText editMataPelajaran, editGuruPengajar, editWaktuMulai, editWaktuAkhir;
    Button simpanJadwal;

    Toolbar toolbar;

    String Edittextt;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_jadwal);

        handler = new DBHandler(this);

        final int ID = getIntent().getIntExtra(DBHandler.ROW_ID, 1);

        toolbar = (Toolbar) findViewById(R.id.Jadwal_Toolbar);
        toolbar.setTitle("Edit Jadwal Pelajaran");

        spinnerHari = (Spinner) findViewById(R.id.spinnerHari);
        spinnerPembagianWaktu = (Spinner) findViewById(R.id.spinnerPembagianWaktu);

        adapterHari = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerHari());
        adapterPembagianWaktu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerPembagianWaktu());

        spinnerHari.setAdapter(adapterHari);
        spinnerHari.setEnabled(false);
        spinnerPembagianWaktu.setAdapter(adapterPembagianWaktu);

        editMataPelajaran = (EditText) findViewById(R.id.tambahMataPelajaran);
        editGuruPengajar = (EditText) findViewById(R.id.tambahGuruPengajar);
        editWaktuMulai = (EditText) findViewById(R.id.tambahWaktuMulai);
        editWaktuAkhir = (EditText) findViewById(R.id.tambahWaktuAkhir);

        editWaktuMulai.setOnTouchListener(this);
        editWaktuAkhir.setOnTouchListener(this);

        simpanJadwal = (Button) findViewById(R.id.simpanJadwal);
        simpanJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDaftarJadwal(ID);
            }
        });

        loadDataJadwal(ID);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.tambahWaktuMulai:
                    Edittextt = "Waktu_Mulai";
                    myTimePicker();
                    break;
                case R.id.tambahWaktuAkhir:
                    Edittextt = "Waktu_Akhir";
                    myTimePicker();
                    break;
            }
        }
        return true;
    }

    public void myTimePicker() {
        Calendar c = Calendar.getInstance();
        int jam = c.get(Calendar.HOUR_OF_DAY);
        int menit = c.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this, listener, jam, menit, android.text.format.DateFormat.is24HourFormat(EditJadwal.this));
        dialog.show();
    }

    TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            String jam = String.valueOf(i);
            String menit = String.valueOf(i1);
            if (i < 10) {
                jam = "0" + i;
            }
            if (i1 < 10) {
                menit = "0" + i1;
            }
            String hasil = jam + ":" + menit;
            if (Edittextt.equals("Waktu_Mulai")) {
                editWaktuMulai.setText(hasil);
            } else if (Edittextt.equals("Waktu_Akhir")) {
                editWaktuAkhir.setText(hasil);
            }

        }
    };

    public List<String> spinnerHari() {
        List<String> list = new ArrayList<>();
        list.add("Daftar Jadwal Pelajaran");
        list.add("Jadwal Senin");
        list.add("Jadwal Selasa");
        list.add("Jadwal Rabu");
        list.add("Jadwal Kamis");
        list.add("Jadwal Jumat");
        list.add("Jadwal Sabtu");
        return list;
    }

    public List<String> spinnerPembagianWaktu() {
        List<String> list = new ArrayList<String>();
        list.add("Pembagian Waktu");
        list.add("Jam Pertama");
        list.add("Jam Kedua");
        list.add("Jam Ketiga");
        list.add("Jam Keempat");
        list.add("Jam Kelima");
        list.add("Jam Keeman");
        list.add("Jam Ketujuh");
        list.add("Jam Kedelapan");
        list.add("Jam Kesembilan");
        list.add("Jam Kesepuluh");
        return list;
    }

    public void loadDataJadwal(int id) {
        String Nama_tabel = getIntent().getStringExtra("Nama_Tabel");
        ModelJadwalPelajaran modelJadwalPelajaran = handler.cekSatuDaftarJadwal(Nama_tabel, id);

        String dataMataPelajaran = modelJadwalPelajaran.getMataPelajaran();
        String dataGuruPengajar = modelJadwalPelajaran.getGuruPengajar();
        String dataWaktuMulai = modelJadwalPelajaran.getWaktuMulai();
        String dataWaktuAkhir = modelJadwalPelajaran.getWaktuAkhir();
        String dataPembagianWaktu = modelJadwalPelajaran.getPembagianWaktu();

        String dataSpinnerHari = Nama_tabel.replace("_", " ");

        editMataPelajaran.setText(dataMataPelajaran);
        editGuruPengajar.setText(dataGuruPengajar);
        editWaktuMulai.setText(dataWaktuMulai);
        editWaktuAkhir.setText(dataWaktuAkhir);

        spinnerHari.setSelection(adapterHari.getPosition(dataSpinnerHari));
        spinnerPembagianWaktu.setSelection(adapterPembagianWaktu.getPosition(dataPembagianWaktu));
    }

    public void editDaftarJadwal(int id) {

        ModelJadwalPelajaran modelJadwalPelajaran = new ModelJadwalPelajaran();

        String insertMataPelajaran = editMataPelajaran.getText().toString().trim();
        String insertGuruPengajar = editGuruPengajar.getText().toString().trim();
        String insertWaktuMulai = editWaktuMulai.getText().toString().trim();
        String insertWaktuAkhir = editWaktuAkhir.getText().toString().trim();
        String insertPembagianWaktu = spinnerPembagianWaktu.getSelectedItem().toString();

        String namaTabel = spinnerHari.getSelectedItem().toString();
        String NAMA_TABEL = namaTabel.replace(" ", "_");

        int posisiHari = spinnerHari.getSelectedItemPosition();
        int posisiPembagian = spinnerPembagianWaktu.getSelectedItemPosition();

        if (insertMataPelajaran.equals("") || insertGuruPengajar.equals("") || insertWaktuMulai.equals("") || insertWaktuAkhir.equals("") || posisiHari == 0 || posisiPembagian == 0) {
            if (posisiHari == 0) {
                Toast.makeText(this, "Tentukan Pilihan Hari Untuk Melanjutkan ", Toast.LENGTH_SHORT).show();
            } else if (insertMataPelajaran.equals("")) {
                Toast.makeText(this, "Masukan Mata Pelajaran Untuk Melanjutkan", Toast.LENGTH_SHORT).show();
            } else if (insertGuruPengajar.equals("")) {
                Toast.makeText(this, "Masukan Nama Guru Pengajar Untuk Melanjutkan", Toast.LENGTH_SHORT).show();
            } else if (insertWaktuMulai.equals("")) {
                Toast.makeText(this, "Tentukan Waktu Mulai Pembelajaran Untuk Melanjutkan", Toast.LENGTH_SHORT).show();
            } else if (insertWaktuAkhir.equals("")) {
                Toast.makeText(this, "Tentukan Waktu Akhir Pembelajaran Untuk Melanjutkan", Toast.LENGTH_SHORT).show();
            } else if (posisiPembagian == 0) {
                Toast.makeText(this, "Tentukan Pembagian Waktu Pembelajaran Untuk Melanjutkan", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                modelJadwalPelajaran.setId(posisiPembagian);
                modelJadwalPelajaran.setMataPelajaran(insertMataPelajaran);
                modelJadwalPelajaran.setGuruPengajar(insertGuruPengajar);
                modelJadwalPelajaran.setWaktuMulai(insertWaktuMulai);
                modelJadwalPelajaran.setWaktuAkhir(insertWaktuAkhir);
                modelJadwalPelajaran.setPembagianWaktu(insertPembagianWaktu);

                handler.editJadwal(NAMA_TABEL, modelJadwalPelajaran, id);
                handler.close();
                finish();

            } catch (SQLiteConstraintException e) {
                ModelJadwalPelajaran modelJadwalPelajaran1 = handler.cekSatuDaftarJadwal(NAMA_TABEL, posisiPembagian);

                String errorMataPelajaran = modelJadwalPelajaran1.getMataPelajaran();
                String errorGuruPengajar = modelJadwalPelajaran1.getGuruPengajar();
                String errorWaktuMulai = modelJadwalPelajaran1.getWaktuMulai();
                String errorWaktuAkhir = modelJadwalPelajaran1.getWaktuAkhir();
                String Hari = namaTabel.replace("Jadwal ", "");

                String pesanError = insertPembagianWaktu + " Di Hari " + Hari + " Sudah Ada\n"
                        + "\nMata Pelajaran : " + errorMataPelajaran
                        + "\nGuru Pengajar : " + errorGuruPengajar
                        + "\nWaktu Mulai : " + errorWaktuMulai
                        + "\nWaktu Akhir : " + errorWaktuAkhir;

                final AlertDialog.Builder builder = new AlertDialog.Builder(EditJadwal.this);
                builder.setCancelable(false);
                builder.setTitle(namaTabel);
                builder.setMessage(pesanError);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.setCancelable(true);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }

}
