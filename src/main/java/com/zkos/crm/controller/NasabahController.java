package com.rkcrm.controller;

import com.rkcrm.model.Nasabah;
import com.rkcrm.service.NasabahService;
import com.rkcrm.service.impl.NasabahServiceImpl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Rows;

import java.util.List;

public class NasabahController extends SelectorComposer<Component> {

    @Wire
    private Grid nasabahGrid;

    private NasabahService nasabahService = new NasabahServiceImpl();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        loadNasabah();
    }

    private void loadNasabah() {
        List<Nasabah> list = nasabahService.getAllNasabah();
        nasabahGrid.setModel(new ListModelList<>(list));
        ((Rows) nasabahGrid.getRows()).invalidate(); // Refresh UI
    }
}
