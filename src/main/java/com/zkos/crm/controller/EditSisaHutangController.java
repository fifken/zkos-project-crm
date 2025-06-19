package com.zkos.crm.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;

public class EditSisaHutangController extends SelectorComposer<Component> {
    @Wire
    private Doublebox sisaHutangBox;
    
    @Wire
    private Window editSisaWin;
    
    private NasabahService nasabahService = new NasabahServiceImpl();
    private Nasabah nasabah;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        nasabah = (Nasabah) editSisaWin.getAttribute("nasabah");
        if (nasabah == null) {
            nasabah = (Nasabah) Executions.getCurrent().getArg().get("nasabah");
            if (nasabah != null) {
                editSisaWin.setAttribute("nasabah", nasabah);
            }
        }
        if (nasabah != null) {
            sisaHutangBox.setValue(nasabah.getSisaHutang());
        }
    }
    
    @Listen("onClick=#simpanBtn")
    public void onSave() {
        try {
            nasabah.setSisaHutang(sisaHutangBox.getValue());
            nasabahService.saveNasabah(nasabah);
            
            // Show success status
            Window statusWin = (Window) Executions.createComponents(
                "/popup/statusUbahSisa.zul", null, null);
            statusWin.doModal();
            
            // Close edit window
            editSisaWin.detach();
            
            // Refresh parent grid
            NasabahController parentController = (NasabahController) 
                editSisaWin.getAttribute("parentController");
            if (parentController != null) {
                parentController.refreshList();
            }
        } catch (Exception e) {
            Messagebox.show("Error: " + e.getMessage(), "Error", 
                Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Listen("onClick=#batalBtn")
    public void onCancel() {
        editSisaWin.detach();
    }
}
