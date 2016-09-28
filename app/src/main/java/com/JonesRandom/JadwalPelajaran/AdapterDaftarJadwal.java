package com.JonesRandom.JadwalPelajaran;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by JonesRandom 26/09/2016.
 */

public class AdapterDaftarJadwal extends CursorAdapter {

    LayoutInflater ly;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterDaftarJadwal(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View v = ly.inflate(R.layout.row_jadwal_pelajaran,viewGroup,false);

        JadwalHolder holder = new JadwalHolder();
        holder.listID = (TextView)v.findViewById(R.id.listID);
        holder.listMataPelajaran = (TextView)v.findViewById(R.id.listMataPelajaran);
        holder.listPengajar = (TextView)v.findViewById(R.id.listGuruPengajar);
        holder.listWaktu = (TextView)v.findViewById(R.id.listWaktu);

        v.setTag(holder);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String ID = cursor.getString(cursor.getColumnIndex(DBHandler.ROW_ID));
        String MataPelajaran = cursor.getString(cursor.getColumnIndex(DBHandler.ROW_MATA_PELAJARAN));
        String Pengajar = cursor.getString(cursor.getColumnIndex(DBHandler.ROW_PENGAJAR));
        String WaktuMulai = cursor.getString(cursor.getColumnIndex(DBHandler.ROW_WAKTU_MULAI));
        String WaktuAkhir = cursor.getString(cursor.getColumnIndex(DBHandler.ROW_WAKTU_AKHIR));

        JadwalHolder holder = (JadwalHolder)view.getTag();

        holder.listID.setText(ID);
        holder.listMataPelajaran.setText(MataPelajaran);
        holder.listPengajar.setText(Pengajar);
        holder.listWaktu.setText(WaktuMulai + " s/d " + WaktuAkhir);

    }

    private class JadwalHolder{
        TextView listID;
        TextView listMataPelajaran;
        TextView listPengajar;
        TextView listWaktu;

    }
}
