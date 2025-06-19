package com.zkos.crm;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MyViewModel {

    @WireVariable
    private NasabahService nasabahService;

    private ListModelList<Nasabah> nasabahListModel;

    // Example fields for form binding
    private String namaNasabah;
    private String noKontrak;
    private Double totalHutang;
    private Double sisaHutang;
    private String status;
    private String cabang;
    private Nasabah selectedNasabah;

    @Init
    public void init() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahListModel = new ListModelList<>(list);
    }

    public ListModelList<Nasabah> getNasabahListModel() {
        return nasabahListModel;
    }

    public void setNasabahListModel(ListModelList<Nasabah> nasabahListModel) {
        this.nasabahListModel = nasabahListModel;
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
    private boolean addDialogVisible;

    public boolean isAddDialogVisible() {
        return addDialogVisible;
    }

    public void setAddDialogVisible(boolean addDialogVisible) {
        this.addDialogVisible = addDialogVisible;
    }

    private boolean detailDialogVisible;

    public boolean isDetailDialogVisible() {
        return detailDialogVisible;
    }

    public void setDetailDialogVisible(boolean detailDialogVisible) {
        this.detailDialogVisible = detailDialogVisible;
    }

    private boolean deleteDialogVisible;
    
    public boolean isDeleteDialogVisible() {
        return deleteDialogVisible;
    }

    public void setDeleteDialogVisible(boolean deleteDialogVisible) {
        this.deleteDialogVisible = deleteDialogVisible;
    }

}
