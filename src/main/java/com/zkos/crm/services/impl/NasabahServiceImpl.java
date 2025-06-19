package com.zkos.crm.services.impl;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NasabahServiceImpl implements NasabahService {

    private static List<Nasabah> nasabahList = new ArrayList<>();

    static {
        nasabahList.add(new Nasabah("Wati", "001", 12000000, 6000000, "Belum Lunas", "Jakarta"));
        nasabahList.add(new Nasabah("Dono", "002", 15000000, 0, "Lunas", "Bandung"));
    }

    @Override
    public List<Nasabah> getAllNasabah() {
        return new ArrayList<>(nasabahList);
    }

    @Override
    public Nasabah saveNasabah(Nasabah nasabah) {
        nasabahList.add(nasabah);
        return nasabah;
    }

    @Override
    public void deleteNasabah(String noKontrak) {
        Iterator<Nasabah> iterator = nasabahList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getNoKontrak().equals(noKontrak)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public Nasabah findByNoKontrak(String noKontrak) {
        for (Nasabah n : nasabahList) {
            if (n.getNoKontrak().equals(noKontrak)) {
                return n;
            }
        }
        return null;
    }
}