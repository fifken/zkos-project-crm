package com.zkos.crm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.zkos.crm.dao.NasabahDao;
import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;

@Service("nasabahService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NasabahServiceImpl implements NasabahService {

    @Autowired
    private NasabahDao nasabahDao;

    @Override
    public List<Nasabah> getAllNasabah() {
        return nasabahDao.findAll();
    }

    @Override
    public Nasabah saveNasabah(Nasabah nasabah) {
        return nasabahDao.save(nasabah);
    }

    @Override
    public void deleteNasabah(String noKontrak) {
        nasabahDao.delete(noKontrak);
    }

    @Override
    public Nasabah updateNasabah(Nasabah nasabah) {
        return nasabahDao.save(nasabah);
    }

    @Override
    public Nasabah findByNoKontrak(String noKontrak) {
        return nasabahDao.findByNoKontrak(noKontrak);
    }
}
