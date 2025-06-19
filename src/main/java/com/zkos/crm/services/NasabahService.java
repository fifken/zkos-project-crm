package com.zkos.crm.services;

import java.util.List;

import com.zkos.crm.model.Nasabah;

public interface NasabahService {
    List<Nasabah> getAllNasabah();
    Nasabah saveNasabah(Nasabah nasabah);
    void deleteNasabah(String noKontrak);
    Nasabah updateNasabah(Nasabah nasabah);
    Nasabah findByNoKontrak(String noKontrak);
}