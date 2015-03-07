package br.com.controlepontosimples.model;

import java.util.Date;

public class LembreteItem {
	private int id;
	private int codigoPonto;
	private long dataLembrete;

	public LembreteItem() {
		this.setId(-1);
		this.setCodigoPonto(-1);
		this.setDataLembrete(new Date().getTime());
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public long getDataLembrete() {
		return dataLembrete;
	}
	public void setDataLembrete(long dataLembrete) {
		this.dataLembrete = dataLembrete;
	}

	public int getCodigoPonto() {
		return codigoPonto;
	}

	public void setCodigoPonto(int codigoPonto) {
		this.codigoPonto = codigoPonto;
	}
}
