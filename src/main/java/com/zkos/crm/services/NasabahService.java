package com.rkcrm.service;

import com.rkcrm.model.Nasabah;

import java.util.List;

public interface NasabahService {
    List<Nasabah> getAllNasabah();
    void addNasabah(Nasabah nasabah);
    void deleteNasabah(String noKontrak);
    Nasabah findByNoKontrak(String noKontrak);
}
