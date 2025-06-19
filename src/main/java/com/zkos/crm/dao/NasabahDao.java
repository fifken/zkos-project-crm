package com.zkos.crm.dao;

import java.util.List;

import com.zkos.crm.model.Nasabah;

public interface NasabahDao {
    List<Nasabah> findAll();
    Nasabah save(Nasabah nasabah);
    void delete(String noKontrak);
    Nasabah findByNoKontrak(String noKontrak);
}