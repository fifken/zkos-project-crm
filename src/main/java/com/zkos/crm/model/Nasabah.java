package com.zkos.crm.model;

public class Nasabah {
    private String nama;
    private String noKontrak;
    private double totalHutang;
    private double sisaHutang;
    private String status;
    private String cabang;

    public Nasabah() {}

    public Nasabah(String nama, String noKontrak, double totalHutang, double sisaHutang, String status, String cabang) {
        this.nama = nama;
        this.noKontrak = noKontrak;
        this.totalHutang = totalHutang;
        this.sisaHutang = sisaHutang;
        this.status = status;
        this.cabang = cabang;
    }

    // Getters & Setters

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoKontrak() { return noKontrak; }
    public void setNoKontrak(String noKontrak) { this.noKontrak = noKontrak; }

    public double getTotalHutang() { return totalHutang; }
    public void setTotalHutang(double totalHutang) { this.totalHutang = totalHutang; }

    public double getSisaHutang() { return sisaHutang; }
    public void setSisaHutang(double sisaHutang) { this.sisaHutang = sisaHutang; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCabang() { return cabang; }
    public void setCabang(String cabang) { this.cabang = cabang; }
}
