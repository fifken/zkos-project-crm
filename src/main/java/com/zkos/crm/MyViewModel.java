package com.zkos.crm;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MyViewModel {

    @WireVariable
    private NasabahService nasabahService;

    private ListModelList<Nasabah> nasabahListModel;

    // Form fields
    private String namaNasabah;
    private String noKontrak;
    private Double totalHutang;
    private Double sisaHutang;
    private String status;
    private String cabang;
    private String notelpon;
    private String email;
    private String alamat;
    private Nasabah selectedNasabah;

    // Dialog visibility
    private boolean addDialogVisible;
    private boolean detailDialogVisible;
    private boolean deleteDialogVisible;

    @Init
    public void init() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahListModel = new ListModelList<>(list);
    }

    public ListModelList<Nasabah> getNasabahListModel() {
        return nasabahListModel;
    }

    // --- Dialog Visibility Getters/Setters ---
    public boolean isAddDialogVisible() {
        return addDialogVisible;
    }
    public void setAddDialogVisible(boolean addDialogVisible) {
        this.addDialogVisible = addDialogVisible;
    }
    public boolean isDetailDialogVisible() {
        return detailDialogVisible;
    }
    public void setDetailDialogVisible(boolean detailDialogVisible) {
        this.detailDialogVisible = detailDialogVisible;
    }
    public boolean isDeleteDialogVisible() {
        return deleteDialogVisible;
    }
    public void setDeleteDialogVisible(boolean deleteDialogVisible) {
        this.deleteDialogVisible = deleteDialogVisible;
    }

    // --- Form Fields Getters/Setters ---
    public String getNamaNasabah() { return namaNasabah; }
    public void setNamaNasabah(String namaNasabah) { this.namaNasabah = namaNasabah; }
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

    // --- Selected Nasabah ---
    public Nasabah getSelectedNasabah() { return selectedNasabah; }
    public void setSelectedNasabah(Nasabah selectedNasabah) { this.selectedNasabah = selectedNasabah; }

    // --- Commands for Buttons and Dialogs ---

    @Command("showAddDialog")
    @NotifyChange({"addDialogVisible", "namaNasabah", "noKontrak", "totalHutang", "sisaHutang", "cabang", "notelpon", "email", "alamat", "status"})
    public void showAddDialog() {
        clearForm();
        setAddDialogVisible(true);
    }

    @Command("closeAddDialog")
    @NotifyChange("addDialogVisible")
    public void closeAddDialog() {
        setAddDialogVisible(false);
    }

    @Command("tambahNasabah")
    @NotifyChange({"nasabahListModel", "addDialogVisible"})
    public void tambahNasabah() {
        // Cek apakah noKontrak sudah ada
        if (nasabahService.findByNoKontrak(noKontrak) != null) {
            Messagebox.show("No Kontrak sudah terdaftar!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        Nasabah nasabah = new Nasabah();
        // Validasi sisa hutang tidak boleh kurang dari 0
        if (sisaHutang == null || sisaHutang < 0) {
            sisaHutang = 0d;
        }
        nasabah.setNama(namaNasabah);
        nasabah.setNoKontrak(noKontrak);
        nasabah.setTotalHutang(totalHutang);
        nasabah.setSisaHutang(sisaHutang);
        // Status otomatis
        if (sisaHutang == 0) {
            nasabah.setStatus("Lunas");
        } else {
            nasabah.setStatus("Belum Lunas");
        }
        nasabah.setCabang(cabang);
        nasabah.setNotelpon(notelpon);
        nasabah.setEmail(email);
        nasabah.setAlamat(alamat);
        // Validasi input wajib
        if (namaNasabah == null || namaNasabah.trim().isEmpty()) {
            Messagebox.show("Nama Nasabah wajib diisi!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (namaNasabah.trim().length() < 3) {
            Messagebox.show("Nama Nasabah minimal 3 karakter!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (!namaNasabah.matches("^[A-Za-z\\s.'-]{3,}$")) {
            Messagebox.show("Nama Nasabah hanya boleh huruf, spasi, titik, apostrof, dan strip!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (noKontrak == null || noKontrak.trim().isEmpty()) {
            Messagebox.show("No Kontrak wajib diisi!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (totalHutang == null || totalHutang < 0) {
            Messagebox.show("Total Hutang wajib diisi dan tidak boleh kurang dari 0!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (sisaHutang == null || sisaHutang < 0) {
            Messagebox.show("Sisa Hutang wajib diisi dan tidak boleh kurang dari 0!", "Error", Messagebox.OK, Messagebox.ERROR);
            sisaHutang = 0d;
            return;
        }
        if (cabang == null || cabang.trim().isEmpty()) {
            Messagebox.show("Cabang wajib diisi!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        // Validasi email menggunakan regex umum
        if (email != null && !email.trim().isEmpty() && !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            Messagebox.show("Format email tidak valid!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        // Validasi nomor telepon Indonesia (08xx... atau +628xx...)
        if (notelpon != null && !notelpon.trim().isEmpty() && !notelpon.matches("^(\\+62|62|0)8[1-9][0-9]{6,10}$")) {
            Messagebox.show("Format nomor telepon Indonesia tidak valid!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        nasabahService.saveNasabah(nasabah);
        nasabahListModel.add(nasabah);
        setAddDialogVisible(false);
        clearForm();
    }

    @Command("showDetail")
    @NotifyChange({"detailDialogVisible", "selectedNasabah"})
    public void showDetail(@BindingParam("nasabah") Nasabah nasabah) {
        this.selectedNasabah = nasabah;
        setDetailDialogVisible(true);
    }

    @Command("closeDetailDialog")
    @NotifyChange("detailDialogVisible")
    public void closeDetailDialog() {
        setDetailDialogVisible(false);
    }

    @Command("editSisaHutang")
    @NotifyChange({"selectedNasabah", "detailDialogVisible"})
    public void editSisaHutang() {
        if (selectedNasabah != null) {
            // Validasi sisa hutang tidak boleh kurang dari 0
            if (selectedNasabah.getSisaHutang() < 0) {
                selectedNasabah.setSisaHutang(0d);
            }
            // Update status otomatis
            if (selectedNasabah.getSisaHutang() == 0) {
                selectedNasabah.setStatus("Lunas");
            } else {
                selectedNasabah.setStatus("Belum Lunas");
            }
            nasabahService.updateNasabah(selectedNasabah);
            nasabahListModel.clear();
            nasabahListModel.addAll(nasabahService.getAllNasabah());
            setDetailDialogVisible(false);
        }
    }

    @Command("showDeleteConfirm")
    @NotifyChange({"deleteDialogVisible", "selectedNasabah"})
    public void showDeleteConfirm(@BindingParam("nasabah") Nasabah nasabah) {
        this.selectedNasabah = nasabah;
        setDeleteDialogVisible(true);
    }

    @Command("closeDeleteDialog")
    @NotifyChange("deleteDialogVisible")
    public void closeDeleteDialog() {
        setDeleteDialogVisible(false);
    }

    @Command("deleteNasabah")
    @NotifyChange({"nasabahListModel", "deleteDialogVisible", "selectedNasabah"})
    public void deleteNasabah() {
        if (selectedNasabah != null) {
            nasabahService.deleteNasabah(selectedNasabah.getNoKontrak());
            nasabahListModel.remove(selectedNasabah);
            setDeleteDialogVisible(false);
            selectedNasabah = null;
        }
    }

    // --- Utility ---
    public void clearForm() {
        namaNasabah = null;
        noKontrak = null;
        totalHutang = null;
        sisaHutang = null;
        status = null;
        cabang = null;
        notelpon = null;
        email = null;
        alamat = null;
    }
}