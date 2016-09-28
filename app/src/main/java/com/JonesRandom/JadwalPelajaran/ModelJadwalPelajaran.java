package com.JonesRandom.JadwalPelajaran;

/**
 * Created by JhonDev on 28/09/2016.
 */

public class ModelJadwalPelajaran {

    int id;
    String MataPelajaran;
    String GuruPengajar;
    String WaktuMulai;
    String WaktuAkhir;
    String PembagianWaktu;

    public ModelJadwalPelajaran() {
    }

    public ModelJadwalPelajaran(int id, String mataPelajaran, String guruPengajar, String waktuMulai, String waktuAkhir, String pembagianWaktu) {
        this.id = id;
        this.MataPelajaran = mataPelajaran;
        this.GuruPengajar = guruPengajar;
        this.WaktuMulai = waktuMulai;
        this.WaktuAkhir = waktuAkhir;
        this.PembagianWaktu = pembagianWaktu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMataPelajaran() {
        return MataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.MataPelajaran = mataPelajaran;
    }

    public String getGuruPengajar() {
        return GuruPengajar;
    }

    public void setGuruPengajar(String guruPengajar) {
        this.GuruPengajar = guruPengajar;
    }

    public String getWaktuMulai() {
        return WaktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.WaktuMulai = waktuMulai;
    }

    public String getWaktuAkhir() {
        return WaktuAkhir;
    }

    public void setWaktuAkhir(String waktuAkhir) {
        this.WaktuAkhir = waktuAkhir;
    }

    public String getPembagianWaktu() {
        return PembagianWaktu;
    }

    public void setPembagianWaktu(String pembagianWaktu) {
        this.PembagianWaktu = pembagianWaktu;
    }
}
