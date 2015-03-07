package br.com.controlepontosimples.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JornadaItem {
	
	private int id;
	private long dataJornada;
	private List<PontoItem> pontos;
	
	public JornadaItem() {
		this.setId(-1);
		this.setDataJornada(new Date().getTime());
		this.setPontos(new ArrayList<PontoItem>());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<PontoItem> getPontos() {
		return pontos;
	}
	public void setPontos(List<PontoItem> pontos) {
		this.pontos = pontos;
	}
	public long getDataJornada() {
	    return dataJornada;
	}
	public void setDataJornada(long dataJornada) {
	    this.dataJornada = dataJornada;
	}
	

}
