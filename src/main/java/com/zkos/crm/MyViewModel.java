package com.zkos.crm;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;

public class MyViewModel {
    private NasabahService nasabahService = new NasabahServiceImpl();
    private ListModelList<Nasabah> nasabahListModel;
    
    // Form fields
    private String namaNasabah;
    private String noKontrak;
    private Double totalHutang;
    private Double sisaHutang;
    private String status;
    private String cabang;
    
    // Selected item
    private Nasabah selectedNasabah;
    
    // Dialog visibility flags
    private boolean addDialogVisible = false;
    private boolean detailDialogVisible = false;
    private boolean deleteDialogVisible = false;

    @Init
    public void init() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahListModel = new ListModelList<>(list);
    }

    @Command
    @NotifyChange({"addDialogVisible", "namaNasabah", "noKontrak", "totalHutang", "sisaHutang", "status", "cabang"})
    public void showAddDialog() {
        clearForm();
        addDialogVisible = true;
    }

    @Command
    @NotifyChange("addDialogVisible")
    public void closeAddDialog() {
        addDialogVisible = false;
    }

    @Command
    @NotifyChange({"nasabahListModel", "addDialogVisible"})
    public void tambahNasabah() {
        if (isFormValid()) {
            Nasabah nasabahBaru = new Nasabah(namaNasabah, noKontrak, totalHutang, 
                                             sisaHutang, status, cabang);
            // Set status berdasarkan sisa hutang
            nasabahBaru.setStatus(sisaHutang == 0 ? "Lunas" : "Belum Lunas");
            
            nasabahService.saveNasabah(nasabahBaru);
            refreshList();
            addDialogVisible = false;
            clearForm();
            Clients.showNotification("Data berhasil ditambahkan", "info", null, "middle_center", 2000);
        } else {
            Clients.showNotification("Semua field harus diisi", "warning", null, "middle_center", 2000);
        }
    }

    @Command
    @NotifyChange({"selectedNasabah", "detailDialogVisible"})
    public void showDetail(@BindingParam("nasabah") Nasabah nasabah) {
        this.selectedNasabah = nasabah;
        detailDialogVisible = true;
    }

    @Command
    @NotifyChange("detailDialogVisible")
    public void closeDetailDialog() {
        detailDialogVisible = false;
    }

    @Command
    @NotifyChange({"nasabahListModel", "detailDialogVisible", "selectedNasabah"})
    public void editSisaHutang() {
        if (selectedNasabah != null) {
            // Update status berdasarkan sisa hutang
            selectedNasabah.setStatus(selectedNasabah.getSisaHutang() == 0 ? "Lunas" : "Belum Lunas");
            
            nasabahService.deleteNasabah(selectedNasabah.getNoKontrak());
            nasabahService.saveNasabah(selectedNasabah);
            
            refreshList();
            detailDialogVisible = false;
            Clients.showNotification("Sisa hutang berhasil diubah", "info", null, "middle_center", 2000);
        }
    }

    @Command
    @NotifyChange({"selectedNasabah", "deleteDialogVisible"})
    public void showDeleteConfirm(@BindingParam("nasabah") Nasabah nasabah) {
        this.selectedNasabah = nasabah;
        deleteDialogVisible = true;
    }

    @Command
    @NotifyChange("deleteDialogVisible")
    public void closeDeleteDialog() {
        deleteDialogVisible = false;
    }

    @Command
    @NotifyChange({"nasabahListModel", "deleteDialogVisible"})
    public void deleteNasabah() {
        if (selectedNasabah != null) {
            nasabahService.deleteNasabah(selectedNasabah.getNoKontrak());
            refreshList();
            deleteDialogVisible = false;
            Clients.showNotification("Data berhasil dihapus", "info", null, "middle_center", 2000);
        }
    }

    private void clearForm() {
        namaNasabah = "";
        noKontrak = "";
        totalHutang = null;
        sisaHutang = null;
        status = "";
        cabang = "";
    }

    private boolean isFormValid() {
        return namaNasabah != null && !namaNasabah.isEmpty() &&
               noKontrak != null && !noKontrak.isEmpty() &&
               totalHutang != null && sisaHutang != null &&
               status != null && !status.isEmpty() &&
               cabang != null && !cabang.isEmpty();
    }

    private void refreshList() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahListModel.clear();
        nasabahListModel.addAll(list);
    }

    // Getters and Setters
    public ListModelList<Nasabah> getNasabahListModel() {
        return nasabahListModel;
    }

    public String getNamaNasabah() {
        return namaNasabah;
    }

    public void setNamaNasabah(String namaNasabah) {
        this.namaNasabah = namaNasabah;
    }

    public String getNoKontrak() {
        return noKontrak;
    }

    public void setNoKontrak(String noKontrak) {
        this.noKontrak = noKontrak;
    }

    public Double getTotalHutang() {
        return totalHutang;
    }

    public void setTotalHutang(Double totalHutang) {
        this.totalHutang = totalHutang;
    }

    public Double getSisaHutang() {
        return sisaHutang;
    }

    public void setSisaHutang(Double sisaHutang) {
        this.sisaHutang = sisaHutang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public Nasabah getSelectedNasabah() {
        return selectedNasabah;
    }

    public void setSelectedNasabah(Nasabah selectedNasabah) {
        this.selectedNasabah = selectedNasabah;
    }

    public boolean isAddDialogVisible() {
        return addDialogVisible;
    }

    public boolean isDetailDialogVisible() {
        return detailDialogVisible;
    }

    public boolean isDeleteDialogVisible() {
        return deleteDialogVisible;
    }
}