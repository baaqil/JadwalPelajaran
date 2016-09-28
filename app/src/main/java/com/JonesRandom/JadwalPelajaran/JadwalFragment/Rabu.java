package com.JonesRandom.JadwalPelajaran.JadwalFragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.JonesRandom.JadwalPelajaran.AdapterDaftarJadwal;
import com.JonesRandom.JadwalPelajaran.DBHandler;
import com.JonesRandom.JadwalPelajaran.EditJadwal;
import com.JonesRandom.JadwalPelajaran.ModelJadwalPelajaran;
import com.JonesRandom.JadwalPelajaran.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rabu extends Fragment implements AdapterView.OnItemClickListener {

    DBHandler handler;
    ListView listView;
    RelativeLayout relativeLayout;

    public Rabu() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new DBHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.jadwal, container, false);
        listView = (ListView) v.findViewById(R.id.listJadwal);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.emptyView);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListview();
    }

    public void setupListview() {
        Cursor cursor = handler.SemuaDaftarJadwal(DBHandler.NAMA_TABEL_RABU);
        if (cursor.getCount() < 1) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        AdapterDaftarJadwal adapterDaftarJadwal = new AdapterDaftarJadwal(getActivity(), cursor, 1);
        listView.setAdapter(adapterDaftarJadwal);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView listID = (TextView) view.findViewById(R.id.listID);
        final int ID = Integer.parseInt(listID.getText().toString());

        ModelJadwalPelajaran modelJadwalPelajaran = handler.cekSatuDaftarJadwal(DBHandler.NAMA_TABEL_RABU, ID);

        String dialogMataPelajaran = modelJadwalPelajaran.getMataPelajaran();
        String dialogGuruPengajar = modelJadwalPelajaran.getGuruPengajar();
        String dialogWaktuMulai = modelJadwalPelajaran.getWaktuMulai();
        String dialogWaktuAkhir = modelJadwalPelajaran.getWaktuAkhir();
        final String dialogPembagianWaktu = modelJadwalPelajaran.getPembagianWaktu();

        String pesanDialog = "Mata Pelajaran : " + dialogMataPelajaran
                + "\nGuru Pengajar : " + dialogGuruPengajar
                + "\nWaktu Mulai : " + dialogWaktuMulai
                + "\nWaktu Akhir : " + dialogWaktuAkhir;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogPembagianWaktu);
        builder.setMessage(pesanDialog);
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent editJadwal = new Intent(getActivity(), EditJadwal.class);
                editJadwal.putExtra(DBHandler.ROW_ID,ID);
                editJadwal.putExtra("Nama_Tabel",DBHandler.NAMA_TABEL_RABU);
                startActivity(editJadwal);
            }
        });
        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogHapusItem(dialogPembagianWaktu, ID);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void dialogHapusItem(String pembagianWaktu, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(pembagianWaktu);
        builder.setMessage("Apakah kamu yakin ingin menghapus jadwal di " + pembagianWaktu + " ?");
        builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handler.hapusJadwal(DBHandler.NAMA_TABEL_RABU, id);
                setupListview();
            }
        });
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
