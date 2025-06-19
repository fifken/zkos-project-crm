package com.zkos.crm.services;

import com.zkos.crm.model.Nasabah;
import java.util.List;

public interface NasabahService {
    List<Nasabah> getAllNasabah();
    Nasabah saveNasabah(Nasabah nasabah);
    void deleteNasabah(String noKontrak);
    Nasabah findByNoKontrak(String noKontrak);
}
