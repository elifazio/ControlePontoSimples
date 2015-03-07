package br.com.controlepontosimples.model;


public class PontoItem {
	private int id;
	private int  codigoJornada;
	private Long dataPontoInicio;
	private Long dataPontoFim;
	private Long dataPontoTotal;
	
	public PontoItem() {
		this.setId(-1);
		this.setCodigoJornada(-1);
		this.setDataPontoInicio(null);
		this.setDataPontoFim(null);
		//this.setDataPontoFimTotal(null);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodigoJornada() {
		return codigoJornada;
	}
	public void setCodigoJornada(int codigoJornada) {
		this.codigoJornada = codigoJornada;
	}
	public Long getDataPontoInicio() {
	    return dataPontoInicio;
	}
	public void setDataPontoInicio(Long dataPontoInicio) {
	    this.dataPontoInicio = dataPontoInicio;
	}
	public Long getDataPontoFim() {
	    return dataPontoFim;
	}
	public void setDataPontoFim(Long dataPontoFim) {
	    this.dataPontoFim = dataPontoFim;
	}
	public void setDataPontoTotal(Long dataPontoTotal) {
		this.dataPontoTotal = dataPontoTotal;
	}
	public Long getDataPontoTotal() {
		return dataPontoTotal;
	}
}
