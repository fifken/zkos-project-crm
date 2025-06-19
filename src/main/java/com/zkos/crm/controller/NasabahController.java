package com.zkos.crm.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;

public class NasabahController extends SelectorComposer<Component> {
    @Wire
    private Rows nasabahRows;

    private NasabahService nasabahService = new NasabahServiceImpl();
    private static final DecimalFormat df = new DecimalFormat("#,##0");

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        refreshList();
    }

    @Listen("onClick=#addNasabahBtn")
    public void addNasabahPopup() {
        Window win = (Window) org.zkoss.zk.ui.Executions.createComponents("/popup/addNasabah.zul", null, null);
        win.setAttribute("parentController", this);
        win.doModal();
    }

    public void refreshList() {
        nasabahRows.getChildren().clear();
        List<Nasabah> list = nasabahService.getAllNasabah();
        System.out.println("DEBUG: Jumlah nasabah = " + (list != null ? list.size() : 0));
        for (Nasabah n : list) {
            System.out.println("DEBUG: Data nasabah = " + n);
            Row row = new Row();
            row.appendChild(new Label(n.getNama()));
            row.appendChild(new Label(n.getNoKontrak()));
            row.appendChild(new Label(df.format(n.getTotalHutang())));
            row.appendChild(new Label(df.format(n.getSisaHutang())));
            row.appendChild(new Label(n.getStatus()));
            
            // Tombol aksi
            Button btnDelete = new Button();
            btnDelete.setIconSclass("z-icon-trash");
            btnDelete.setSclass("elegant-button");
            btnDelete.setTooltiptext("Delete");
            btnDelete.addEventListener(Events.ON_CLICK, e -> showDeleteConfirm(n));

            Button btnDetail = new Button();
            btnDetail.setIconSclass("z-icon-search");
            btnDetail.setSclass("elegant-button");
            btnDetail.setTooltiptext("View Details");
            btnDetail.addEventListener(Events.ON_CLICK, e -> {
                System.out.println("DEBUG: btnDetail klik, nasabah = " + n);
                showDetailPopup(n);
            });

            Button btnEditSisa = new Button();
            btnEditSisa.setIconSclass("z-icon-edit");
            btnEditSisa.setSclass("elegant-button");
            btnEditSisa.setTooltiptext("Edit Sisa Hutang");
            btnEditSisa.addEventListener(Events.ON_CLICK, e -> showEditSisaPopup(n));

            Hlayout aksi = new Hlayout();
            aksi.appendChild(btnDetail);
            aksi.appendChild(btnEditSisa);
            aksi.appendChild(btnDelete);
            row.appendChild(aksi);
            nasabahRows.appendChild(row);
        }
    }

    private void showDeleteConfirm(Nasabah nasabah) {
        Window win = (Window) org.zkoss.zk.ui.Executions.createComponents("/popup/deleteConfirm.zul", null, null);
        win.setAttribute("nasabah", nasabah);
        win.setAttribute("parentController", this);
        win.doModal();
    }

    private void showDetailPopup(Nasabah nasabah) {
        Map<String, Object> params = new HashMap<>();
        params.put("nasabah", nasabah);
        Window win = (Window) org.zkoss.zk.ui.Executions.createComponents("/popup/detailNasabah.zul", null, params);
        win.setAttribute("parentController", this);
        win.doModal();
    }

    private void showEditSisaPopup(Nasabah nasabah) {
        Map<String, Object> params = new HashMap<>();
        params.put("nasabah", nasabah);
        Window win = (Window) org.zkoss.zk.ui.Executions.createComponents("/popup/editSisaHutang.zul", null, params);
        win.setAttribute("parentController", this);
        win.doModal();
    }
}
