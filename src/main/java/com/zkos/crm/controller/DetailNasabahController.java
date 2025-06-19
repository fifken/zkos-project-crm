package com.zkos.crm.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.zkos.crm.model.Nasabah;

public class DetailNasabahController extends SelectorComposer<Component> {
    @Wire
    private Label namaDetail, noKontrakDetail, totalHutangDetail, 
                sisaHutangDetail, statusDetail, cabangDetail;
    
    @Wire
    private Window detailWin;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Nasabah nasabah = (Nasabah) detailWin.getAttribute("nasabah");
        if (nasabah == null) {
            nasabah = (Nasabah) Executions.getCurrent().getArg().get("nasabah");
            if (nasabah != null) {
                detailWin.setAttribute("nasabah", nasabah);
            }
        }
        java.util.Set<String> attrNames = detailWin.getAttributes().keySet();
        System.out.println("DEBUG: Attribute di detailWin: " + attrNames);
        for (String attr : attrNames) {
            System.out.println("DEBUG: Attribute " + attr + " = " + detailWin.getAttribute(attr));
        }
        if (nasabah != null) {
            populateDetails(nasabah);
        } else {
            namaDetail.setValue("Data tidak ditemukan");
            noKontrakDetail.setValue("");
            totalHutangDetail.setValue("");
            sisaHutangDetail.setValue("");
            statusDetail.setValue("");
            cabangDetail.setValue("");
        }
    }
    
    private void populateDetails(Nasabah nasabah) {
        System.out.println("DEBUG: populateDetails dipanggil, nasabah = " + nasabah);
        namaDetail.setValue(nasabah.getNama());
        noKontrakDetail.setValue(nasabah.getNoKontrak());
        totalHutangDetail.setValue(String.valueOf(nasabah.getTotalHutang()));
        sisaHutangDetail.setValue(String.valueOf(nasabah.getSisaHutang()));
        statusDetail.setValue(nasabah.getStatus());
        cabangDetail.setValue(nasabah.getCabang());
    }
    
    @Listen("onClick=#tutupBtn")
    public void onClose() {
        detailWin.detach();
    }
}
