package com.zkos.crm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.zkos.crm.dao.NasabahDao;
import com.zkos.crm.model.Nasabah;

@Repository
public class NasabahDaoImpl implements NasabahDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Nasabah> findAll() {
        return entityManager.createQuery("SELECT n FROM Nasabah n", Nasabah.class).getResultList();
    }

    @Override
    public Nasabah save(Nasabah nasabah) {
        if (nasabah.getId() == null) {
            entityManager.persist(nasabah);
            return nasabah;
        }
        return entityManager.merge(nasabah);
    }

    @Override
    public void delete(String noKontrak) {
        Nasabah n = findByNoKontrak(noKontrak);
        if (n != null) entityManager.remove(n);
    }

    @Override
    public Nasabah findByNoKontrak(String noKontrak) {
        List<Nasabah> list = entityManager.createQuery(
            "SELECT n FROM Nasabah n WHERE n.noKontrak = :noKontrak", Nasabah.class)
            .setParameter("noKontrak", noKontrak)
            .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}