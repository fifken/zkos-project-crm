package com.zkos.crm.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;

public class DeleteConfirmController extends SelectorComposer<Component> {
    @Wire
    private Window deleteWin;
    
    private final NasabahService nasabahService = new NasabahServiceImpl();
    
    @Listen("onClick=#yaBtn")
    public void onConfirm() {
        Nasabah nasabah = (Nasabah) deleteWin.getAttribute("nasabah");
        if (nasabah != null) {
            try {
                nasabahService.deleteNasabah(nasabah.getNoKontrak());
                
                // Show success status
                Window statusWin = (Window) Executions.createComponents(
                    "/popup/statusDlt.zul", null, null);
                statusWin.doModal();
                
                // Close confirm window
                deleteWin.detach();
                
                // Refresh parent grid
                NasabahController parentController = (NasabahController) 
                    deleteWin.getAttribute("parentController");
                if (parentController != null) {
                    parentController.refreshList();
                }
            } catch (Exception e) {
                Messagebox.show("Error menghapus data: " + e.getMessage(), 
                    "Error", Messagebox.OK, Messagebox.ERROR);
            }
        }
    }
    
    @Listen("onClick=#tidakBtn")
    public void onCancel() {
        deleteWin.detach();
    }
}
