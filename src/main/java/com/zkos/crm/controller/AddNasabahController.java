package com.zkos.crm.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;

public class AddNasabahController extends SelectorComposer<Component> {
    @Wire
    private Textbox namaBox, noKontrakBox, cabangBox;
    
    @Wire
    private Doublebox totalHutangBox, sisaHutangBox;
    
    @Wire
    private Combobox statusBox;
    
    @Wire
    private Window addWin;
    
    private NasabahService nasabahService = new NasabahServiceImpl();
    
    @Listen("onClick=#tambahBtn")
    public void onAdd() {
        System.out.println("Adding new Nasabah");
        try {
            Nasabah nasabah = new Nasabah(
                namaBox.getValue(),
                noKontrakBox.getValue(),
                totalHutangBox.getValue(),
                sisaHutangBox.getValue(),
                statusBox.getSelectedItem().getValue(),
                cabangBox.getValue()
            );
            
            nasabahService.saveNasabah(nasabah);
            
            // Show success popup
            Window statusWin = (Window) Executions.createComponents(
                "/popup/statusUbahSisa.zul", null, null);
            statusWin.setTitle("Sukses");
            Label msg = (Label) statusWin.getFellow("statusLabel");
            msg.setValue("Data nasabah berhasil ditambahkan");
            statusWin.doModal();
            
            // Close this window
            addWin.detach();
            
            // Refresh parent grid
            NasabahController parentController = (NasabahController) 
                addWin.getAttribute("parentController");
            if (parentController != null) {
                parentController.refreshList();
            }
        } catch (Exception e) {
            Messagebox.show("Error: " + e.getMessage(), "Error", 
                Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Listen("onClick=#cancelBtn")
    public void onCancel() {
        addWin.detach();
    }
}
