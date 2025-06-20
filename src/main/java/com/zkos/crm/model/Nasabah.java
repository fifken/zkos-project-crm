package com.zkos.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nasabah")
public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(name = "no_kontrak", unique = true, nullable = false)
    private String noKontrak;

    @Column(nullable = false)
    private Double totalHutang;

    @Column(nullable = false)
    private Double sisaHutang;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String cabang;

    @Column(length = 20)
    private String notelpon;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String alamat;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNoKontrak() { return noKontrak; }
    public void setNoKontrak(String noKontrak) { this.noKontrak = noKontrak; }
    public Double getTotalHutang() { return totalHutang; }
    public void setTotalHutang(Double totalHutang) { this.totalHutang = totalHutang; }
    public Double getSisaHutang() { return sisaHutang; }
    public void setSisaHutang(Double sisaHutang) { this.sisaHutang = sisaHutang; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCabang() { return cabang; }
    public void setCabang(String cabang) { this.cabang = cabang; }
    public String getNotelpon() { return notelpon; }
    public void setNotelpon(String notelpon) { this.notelpon = notelpon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}