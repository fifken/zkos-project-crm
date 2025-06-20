package com.zkos.crm;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.util.SecurityUtil;

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

    private String userRole;

    @Init
    public void init() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahListModel = new ListModelList<>(list);
        Object roleObj = Sessions.getCurrent().getAttribute("userRole");
        userRole = (roleObj != null) ? roleObj.toString() : "ROLE_USER";
    }

    public ListModelList<Nasabah> getNasabahListModel() {
        return nasabahListModel;
    }

    public boolean isAdmin() {
        return SecurityUtil.isAdmin();
    }

    public boolean isUser() {
        return SecurityUtil.isUser();
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

    public String getNotelpon() {
        return notelpon;
    }

    public void setNotelpon(String notelpon) {
        this.notelpon = notelpon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // --- Selected Nasabah ---
    public Nasabah getSelectedNasabah() {
        return selectedNasabah;
    }

    public void setSelectedNasabah(Nasabah selectedNasabah) {
        this.selectedNasabah = selectedNasabah;
    }

    // --- Commands for Buttons and Dialogs ---
    @Command("showAddDialog")
    @NotifyChange("addDialogVisible")
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
        if (!SecurityUtil.isAdmin()) {
            org.zkoss.zul.Messagebox.show("Access denied.");
            return;
        }
        Nasabah nasabah = new Nasabah();
        nasabah.setNama(namaNasabah);
        nasabah.setNoKontrak(noKontrak);
        nasabah.setTotalHutang(totalHutang);
        nasabah.setSisaHutang(sisaHutang);
        nasabah.setStatus(status);
        nasabah.setCabang(cabang);
        nasabah.setNotelpon(notelpon);
        nasabah.setEmail(email);
        nasabah.setAlamat(alamat);
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
    @NotifyChange({"nasabahListModel", "detailDialogVisible"})
    public void editSisaHutang() {
        if (!SecurityUtil.isAdmin()) {
            org.zkoss.zul.Messagebox.show("Access denied.");
            return;
        }
        if (selectedNasabah != null) {
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
    @NotifyChange({"nasabahListModel", "deleteDialogVisible"})
    public void deleteNasabah() {
        if (!SecurityUtil.isAdmin()) {
            org.zkoss.zul.Messagebox.show("Access denied.");
            return;
        }
        if (selectedNasabah != null) {
            nasabahService.deleteNasabah(selectedNasabah.getNoKontrak());
            nasabahListModel.remove(selectedNasabah);
            setDeleteDialogVisible(false);
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
