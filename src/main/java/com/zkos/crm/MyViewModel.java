// package com.zkos.crm;

// import com.zkos.crm.model.Nasabah;
// import com.zkos.crm.services.NasabahService;
// import com.zkos.crm.services.impl.NasabahServiceImpl;
// import java.util.List;
// import org.zkoss.bind.annotation.BindingParam;
// import org.zkoss.bind.annotation.Command;
// import org.zkoss.bind.annotation.Init;
// import org.zkoss.lang.Strings;
// import org.zkoss.zul.ListModel;
// import org.zkoss.zul.ListModelList;

// public class MyViewModel {

//     private NasabahService nasabahService = new NasabahServiceImpl();

//     private ListModelList<Nasabah> nasabahListModel;
//     private String namaNasabah;
//     private String noKontrak;
//     private Double totalHutang;
//     private Double sisaHutang;
//     private String status;
//     private String cabang;

//     @Init
//     public void init() {
//         List<Nasabah> list = nasabahService.getAllNasabah();
//         nasabahListModel = new ListModelList<>(list);
//     }

//     public ListModel<Nasabah> getNasabahListModel() {
//         return nasabahListModel;
//     }

//     public String getNamaNasabah() {
//         return namaNasabah;
//     }

//     public void setNamaNasabah(String namaNasabah) {
//         this.namaNasabah = namaNasabah;
//     }

//     // Getter & Setter untuk noKontrak
//     public String getNoKontrak() {
//         return noKontrak;
//     }

//     public void setNoKontrak(String noKontrak) {
//         this.noKontrak = noKontrak;
//     }

//     // Getter & Setter untuk totalHutang
//     public Double getTotalHutang() {
//         return totalHutang;
//     }

//     public void setTotalHutang(Double totalHutang) {
//         this.totalHutang = totalHutang;
//     }

//     // Getter & Setter untuk sisaHutang
//     public Double getSisaHutang() {
//         return sisaHutang;
//     }

//     public void setSisaHutang(Double sisaHutang) {
//         this.sisaHutang = sisaHutang;
//     }

//     // Getter & Setter untuk status
//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }

//     // Getter & Setter untuk cabang
//     public String getCabang() {
//         return cabang;
//     }

//     public void setCabang(String cabang) {
//         this.cabang = cabang;
//     }

//     @Command
//     public void tambahNasabah() {
//         if (Strings.isBlank(namaNasabah) || Strings.isBlank(noKontrak) || totalHutang == null || sisaHutang == null || Strings.isBlank(status) || Strings.isBlank(cabang)) {
//             return;
//         }

//         Nasabah nasabahBaru = new Nasabah();
//         nasabahBaru.setNama(namaNasabah);
//         nasabahBaru.setNoKontrak(noKontrak);
//         nasabahBaru.setTotalHutang(totalHutang);
//         nasabahBaru.setSisaHutang(sisaHutang);
//         nasabahBaru.setStatus(status);
//         nasabahBaru.setCabang(cabang);

//         Nasabah savedNasabah = nasabahService.saveNasabah(nasabahBaru);
//         nasabahListModel.add(savedNasabah);

//         namaNasabah = ""; // reset input
//         noKontrak = "";
//         totalHutang = null;
//         sisaHutang = null;
//         status = "";
//         cabang = "";
//     }

//     @Command
//     public void hapusNasabah(@BindingParam("nasabah") Nasabah nasabah) {
//         nasabahService.deleteNasabah(nasabah.getNoKontrak());
//         nasabahListModel.remove(nasabah);
//     }
// }