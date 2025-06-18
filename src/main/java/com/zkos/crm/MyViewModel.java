package com.zkos.crm;

import com.zkos.crm.entity.Nasabah;
import com.zkos.crm.services.NasabahService;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MyViewModel {

	@WireVariable
	private NasabahService myService;
	private ListModelList<Nasabah> logListModel;
	private String message;

	@Init
	public void init() {
		List<Nasabah> logList = myService.getLogs();
		logListModel = new ListModelList<Nasabah>(logList);
	}

	public ListModel<Nasabah> getLogListModel() {
		return logListModel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Command
	public void addLog() {
		if(Strings.isBlank(message)) {
			return;
		}
		Nasabah log = new Nasabah(message);
		log = myService.addLog(log);
		logListModel.add(log);
	}

	@Command
	public void deleteLog(@BindingParam("log") Nasabah log) {
		myService.deleteLog(log);
		logListModel.remove(log);
	}

}
