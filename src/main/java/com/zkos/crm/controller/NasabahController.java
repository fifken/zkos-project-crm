package com.zkos.crm.controller;

import com.zkos.crm.model.Nasabah;
import com.zkos.crm.services.NasabahService;
import com.zkos.crm.services.impl.NasabahServiceImpl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.util.List;

public class NasabahController extends SelectorComposer<Component> {
    @Wire
    private Grid nasabahGrid;
    @Wire
    private Rows nasabahRows;
    @Wire
    private Window addWin, DltUpt, deleteConfirm, statusDlt;
    @Wire
    private Textbox namaBox, noKontrakBox, statusBox, cabangBox;
    @Wire
    private Doublebox totalHutangBox, sisaHutangBox;
    @Wire
    private Button yaHapusBtn;

    // Tambahkan property dan window detail
    @Wire
    private Window detailWin;
    @Wire
    private Label detailNama, detailNoKontrak, detailCabang, detailTotalHutang, detailSisaHutang, detailStatus;
    @Wire
    private Window editSisaWin;
    @Wire
    private Doublebox editSisaBox;

    private NasabahService nasabahService = new NasabahServiceImpl();
    private Nasabah selectedNasabah;
    private Nasabah detailNasabah;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        loadNasabah();
        // Wiring tombol tambah
        Button tambahBtn = (Button) addWin.getFellow("tambahBtn");
        tambahBtn.addEventListener(Events.ON_CLICK, e -> addNasabah());
        // Wiring tombol simpan sisa hutang langsung (hanya sekali)
        Button editBtn = (Button) detailWin.getFellow("editSisaBtn");
        editBtn.addEventListener(Events.ON_CLICK, e -> simpanEditSisaHutangLangsung());
    }

    private void loadNasabah() {
        nasabahRows.getChildren().clear();
        List<Nasabah> list = nasabahService.getAllNasabah();
        for (Nasabah n : list) {
            Row row = new Row();
            row.appendChild(new Label(n.getNama()));
            row.appendChild(new Label(n.getNoKontrak()));
            row.appendChild(new Label(String.valueOf(n.getTotalHutang())));
            row.appendChild(new Label(String.valueOf(n.getSisaHutang())));
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
            btnDetail.addEventListener(Events.ON_CLICK, e -> showDetailPopup(n));

            Hlayout aksi = new Hlayout();
            aksi.appendChild(btnDetail);
            aksi.appendChild(btnDelete);
            row.appendChild(aksi);
            nasabahRows.appendChild(row);
        }
    }

    public void addNasabah() {
        Textbox namaBox = (Textbox) addWin.getFellow("namaBox");
        Textbox noKontrakBox = (Textbox) addWin.getFellow("noKontrakBox");
        Doublebox totalHutangBox = (Doublebox) addWin.getFellow("totalHutangBox");
        Doublebox sisaHutangBox = (Doublebox) addWin.getFellow("sisaHutangBox");
        Textbox statusBox = (Textbox) addWin.getFellow("statusBox");
        Textbox cabangBox = (Textbox) addWin.getFellow("cabangBox");
        String nama = namaBox.getValue();
        String noKontrak = noKontrakBox.getValue();
        Double totalHutang = totalHutangBox.getValue();
        Double sisaHutang = sisaHutangBox.getValue();
        String status = statusBox.getValue();
        String cabang = cabangBox.getValue();
        if (nama.isEmpty() || noKontrak.isEmpty() || totalHutang == null || sisaHutang == null || status.isEmpty() || cabang.isEmpty()) {
            Messagebox.show("Semua field harus diisi.");
            return;
        }
        Nasabah nasabahBaru = new Nasabah(nama, noKontrak, totalHutang, sisaHutang, status, cabang);
        nasabahService.saveNasabah(nasabahBaru);
        addWin.setVisible(false);
        DltUpt.setVisible(true);
        clearFormFellow();
        loadNasabah();
    }

    private void clearFormFellow() {
        ((Textbox) addWin.getFellow("namaBox")).setValue("");
        ((Textbox) addWin.getFellow("noKontrakBox")).setValue("");
        ((Doublebox) addWin.getFellow("totalHutangBox")).setValue(null);
        ((Doublebox) addWin.getFellow("sisaHutangBox")).setValue(null);
        ((Textbox) addWin.getFellow("statusBox")).setValue("");
        ((Textbox) addWin.getFellow("cabangBox")).setValue("");
    }

    private void showDeleteConfirm(Nasabah nasabah) {
        selectedNasabah = nasabah;
        deleteConfirm.setVisible(true);
        Button yaBtn = (Button) deleteConfirm.getFellow("yaHapusBtn");
        // Remove previous listeners to avoid duplicate delete
        for (org.zkoss.zk.ui.event.EventListener<?> l : yaBtn.getEventListeners(Events.ON_CLICK)) {
            yaBtn.removeEventListener(Events.ON_CLICK, l);
        }
        yaBtn.addEventListener(Events.ON_CLICK, this::deleteNasabah);
    }

    private void deleteNasabah(Event event) {
        if (selectedNasabah != null) {
            nasabahService.deleteNasabah(selectedNasabah.getNoKontrak());
            deleteConfirm.setVisible(false);
            statusDlt.setVisible(true);
            loadNasabah();
            selectedNasabah = null;
        }
    }

    private void showDetailPopup(Nasabah n) {
        if (detailWin != null) {
            detailNasabah = n;
            ((Label) detailWin.getFellow("detailNama")).setValue(n.getNama());
            ((Label) detailWin.getFellow("detailNoKontrak")).setValue(n.getNoKontrak());
            ((Label) detailWin.getFellow("detailCabang")).setValue(n.getCabang());
            ((Label) detailWin.getFellow("detailTotalHutang")).setValue(String.valueOf(n.getTotalHutang()));
            ((Textbox) detailWin.getFellow("detailSisaHutang")).setValue(String.valueOf(n.getSisaHutang()));
            ((Label) detailWin.getFellow("detailStatus")).setValue(n.getStatus());
            detailWin.setVisible(true);
        }
    }

    private void simpanEditSisaHutangLangsung() {
        if (detailNasabah != null) {
            Textbox sisaBox = (Textbox) detailWin.getFellow("detailSisaHutang");
            try {
                double newSisa = Double.parseDouble(sisaBox.getValue());
                detailNasabah.setSisaHutang(newSisa);
                nasabahService.deleteNasabah(detailNasabah.getNoKontrak());
                nasabahService.saveNasabah(detailNasabah);
                loadNasabah();
            } catch (NumberFormatException ex) {
                Messagebox.show("Input sisa hutang tidak valid.");
            }
        }
    }
}
