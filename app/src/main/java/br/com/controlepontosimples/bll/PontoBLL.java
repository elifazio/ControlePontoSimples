package br.com.controlepontosimples.bll;

import java.util.List;

import android.content.Context;
import br.com.controlepontosimples.dal.PontoDAL;
import br.com.controlepontosimples.model.PontoItem;

public class PontoBLL {

	private PontoDAL dal;

	public PontoBLL(Context context) {
		this.dal = new PontoDAL(context);
	}

	public int savePonto(PontoItem item) {
		if (item.getId() > 0) {
			return this.dal.updatePonto(item);
		} else {
			return (int) this.dal.insertPonto(item);
		}
	}
	
	public int deletePonto(PontoItem item) {
		if (item.getId() > 0) {
			return this.dal.deletePonto(item.getId());
		}
		return 0;
	}

	public List<PontoItem> getPontosByCodigoJornada(int codigoJornada) {
		return this.dal.getPontosByCodigoJornada(codigoJornada);
	}

	public boolean setEmptyNewPonto(List<PontoItem> lst) {
		if (lst.size() == 0 || lst.get(lst.size() - 1).getDataPontoFim() != null) {
			lst.add(new PontoItem());
			return true;
		}
		return false;
	}
}
