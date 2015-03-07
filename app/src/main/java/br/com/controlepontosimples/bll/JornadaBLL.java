package br.com.controlepontosimples.bll;

import java.util.Date;

import android.content.Context;
import br.com.controlepontosimples.dal.JornadaDAL;
import br.com.controlepontosimples.model.JornadaItem;

public class JornadaBLL {

	private Context ctx;
	private JornadaDAL dal;

	public JornadaBLL(Context context) {
		this.ctx = context;
		this.dal = new JornadaDAL(context);
	}

	public JornadaItem getJornada() {
		JornadaItem item = this.dal.getJornadaByTimestamp(new Date().getTime());
		if (item != null) {
			PontoBLL bll = new PontoBLL(this.ctx);
			item.setPontos(bll.getPontosByCodigoJornada(item.getId()));
		} else {
			item = new JornadaItem();
		item.setId(this.saveJornada(item));	
		}
		return item;
	}
	
	public void setEmptyNewPontoForJornada(JornadaItem item) {
		PontoBLL bll = new PontoBLL(this.ctx);
		if (bll.setEmptyNewPonto(item.getPontos())) {
			item.getPontos().get(item.getPontos().size()-1).setCodigoJornada(item.getId());	
		}		
	}
	
	public int saveJornada(JornadaItem item) {
		if (item.getId() > 0) {
			return this.dal.updateJornada(item);
		} else {
			return (int) this.dal.insertJornada(item);
		}
	}
	
	
}
