package br.com.controlepontosimples.bll;

import java.util.List;

import android.content.Context;
import br.com.controlepontosimples.dal.LembreteDAL;
import br.com.controlepontosimples.lembrete.ReminderManager;
import br.com.controlepontosimples.model.LembreteItem;
import br.com.controlepontosimples.model.PontoItem;

public class LembreteBLL {

	private Context ctx;
	private LembreteDAL dal;

	public LembreteBLL(Context context) {
		this.ctx = context;
		this.dal = new LembreteDAL(context);
	}

	public boolean saveLembrete(LembreteItem item) {
		if (this._saveLembrete(item)) {
			ReminderManager rm = new ReminderManager(this.ctx);
			rm.setReminder(item.getId(), item.getDataLembrete());
			return true;
		}
		return false;
	}

	private boolean _saveLembrete(LembreteItem item) {
		if (item.getId() > 0) {
			return this.dal.updateLembrete(item) > 0;
		} else {
			item.setId((int) this.dal.insertLembrete(item));
			return item.getId() > 0;
		}
	}

	public LembreteItem getLembrete(int id) {
		return this.dal.getLembrete(id);
	}

	public List<LembreteItem> getLembretes() {
		return this.dal.getLembretes();
	}

	public boolean deleteLembrete(PontoItem item) {
		if (item.getId() > 0) {
			return this.dal.deleteLembrete(item.getId()) > 0;
		}
		return false;
	}

	public boolean deleteLembrete(int id) {
		if (id > 0) {
			return this.dal.deleteLembrete(id) > 0;
		}
		return false;
	}

}
