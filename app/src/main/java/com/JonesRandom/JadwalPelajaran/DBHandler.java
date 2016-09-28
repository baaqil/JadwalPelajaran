package com.JonesRandom.JadwalPelajaran;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JonesRandom 26/09/2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final String NAMA_DATABASE = "DatabaseJadwal";
    public static final String NAMA_TABEL_SENIN = "Jadwal_Senin";
    public static final String NAMA_TABEL_SELASA = "Jadwal_Selasa";
    public static final String NAMA_TABEL_RABU = "Jadwal_Rabu";
    public static final String NAMA_TABEL_KAMIS = "Jadwal_Kamis";
    public static final String NAMA_TABEL_JUMAT = "Jadwal_Jumat";
    public static final String NAMA_TABEL_SABTU = "Jadwal_Sabtu";

    public static final String ROW_ID = "_id";
    public static final String ROW_MATA_PELAJARAN = "Nama_Pelajaran";
    public static final String ROW_WAKTU_MULAI = "Waktu_Mulai";
    public static final String ROW_WAKTU_AKHIR = "Waktu_Akhir";
    public static final String ROW_PENGAJAR = "Pengajar";
    public static final String ROW_PEMBAGIAN_WAKTU = "Pembagian_waktu";

    SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, NAMA_DATABASE, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query(NAMA_TABEL_SENIN));
        db.execSQL(query(NAMA_TABEL_SELASA));
        db.execSQL(query(NAMA_TABEL_RABU));
        db.execSQL(query(NAMA_TABEL_KAMIS));
        db.execSQL(query(NAMA_TABEL_JUMAT));
        db.execSQL(query(NAMA_TABEL_SABTU));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public String query(String Tabel) {
        return "CREATE TABLE " + Tabel + "(" + ROW_ID + " INTEGER PRIMARY KEY," + ROW_MATA_PELAJARAN + " TEXT," + ROW_WAKTU_MULAI + " TEXT," + ROW_WAKTU_AKHIR + " TEXT," + ROW_PENGAJAR + " TEXT," + ROW_PEMBAGIAN_WAKTU + " TEXT)";
    }

    public Cursor SemuaDaftarJadwal(String Tabel) {
        return db.rawQuery("SELECT * FROM " + Tabel, null);
    }

    public ModelJadwalPelajaran tambahJadwal(String Tabel, int id, String Matapelajaran, String waktuMulai, String waktuAkhir, String Pengajar, String PembagianWaktu) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, id);
        values.put(ROW_MATA_PELAJARAN, Matapelajaran);
        values.put(ROW_WAKTU_MULAI, waktuMulai);
        values.put(ROW_WAKTU_AKHIR, waktuAkhir);
        values.put(ROW_PENGAJAR, Pengajar);
        values.put(ROW_PEMBAGIAN_WAKTU, PembagianWaktu);

        long getID = db.insert(Tabel, null, values);

        Cursor cursor = db.query(Tabel,null, ROW_ID + "=" + getID,null,null,null,null);
        cursor.moveToFirst();
        ModelJadwalPelajaran modelJadwalPelajaran = new ModelJadwalPelajaran();

        modelJadwalPelajaran.setId(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
        modelJadwalPelajaran.setMataPelajaran(cursor.getString(cursor.getColumnIndex(ROW_MATA_PELAJARAN)));
        modelJadwalPelajaran.setGuruPengajar(cursor.getString(cursor.getColumnIndex(ROW_PENGAJAR)));
        modelJadwalPelajaran.setWaktuMulai(cursor.getString(cursor.getColumnIndex(ROW_WAKTU_MULAI)));
        modelJadwalPelajaran.setWaktuAkhir(cursor.getString(cursor.getColumnIndex(ROW_WAKTU_AKHIR)));
        modelJadwalPelajaran.setPembagianWaktu(cursor.getString(cursor.getColumnIndex(ROW_PEMBAGIAN_WAKTU)));

        return modelJadwalPelajaran;

    }

    public void editJadwal(String Tabel, ModelJadwalPelajaran modelJadwalPelajaran, int id) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, modelJadwalPelajaran.getId());
        values.put(ROW_MATA_PELAJARAN, modelJadwalPelajaran.getMataPelajaran());
        values.put(ROW_WAKTU_MULAI, modelJadwalPelajaran.getWaktuMulai());
        values.put(ROW_WAKTU_AKHIR, modelJadwalPelajaran.getWaktuAkhir());
        values.put(ROW_PENGAJAR, modelJadwalPelajaran.getGuruPengajar());
        values.put(ROW_PEMBAGIAN_WAKTU, modelJadwalPelajaran.getPembagianWaktu());
        db.update(Tabel, values, ROW_ID + "=" + id, null);
    }

    public ModelJadwalPelajaran cekSatuDaftarJadwal(String tabel, int id) {
        ModelJadwalPelajaran modelJadwalPelajaran = new ModelJadwalPelajaran();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tabel + " WHERE " + ROW_ID + "=" + id, null);
        cursor.moveToFirst();
        modelJadwalPelajaran.setId(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
        modelJadwalPelajaran.setMataPelajaran(cursor.getString(cursor.getColumnIndex(ROW_MATA_PELAJARAN)));
        modelJadwalPelajaran.setGuruPengajar(cursor.getString(cursor.getColumnIndex(ROW_PENGAJAR)));
        modelJadwalPelajaran.setWaktuMulai(cursor.getString(cursor.getColumnIndex(ROW_WAKTU_MULAI)));
        modelJadwalPelajaran.setWaktuAkhir(cursor.getString(cursor.getColumnIndex(ROW_WAKTU_AKHIR)));
        modelJadwalPelajaran.setPembagianWaktu(cursor.getString(cursor.getColumnIndex(ROW_PEMBAGIAN_WAKTU)));
        cursor.close();
        return modelJadwalPelajaran;
    }

    public void hapusJadwal(String Tabel, int id) {
        db.delete(Tabel, ROW_ID + "=" + id, null);
    }

}
