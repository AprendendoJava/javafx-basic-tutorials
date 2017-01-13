package org.fxapps.repasse.model;

import java.util.Map;

public class Agregacao {

	private int ano;

	private int mes;

	private Municipio municipio;

	private TipoAgregacao tipoAgregacao;
	private Map<Object, Double> dadosAgregados;

	public Agregacao() {

	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public Map<Object, Double> getDadosAgregados() {
		return dadosAgregados;
	}

	public void setDadosAgregados(Map<Object, Double> dadosAgregados) {
		this.dadosAgregados = dadosAgregados;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public TipoAgregacao getTipoAgregacao() {
		return tipoAgregacao;
	}

	public void setTipoAgregacao(TipoAgregacao tipoAgregacao) {
		this.tipoAgregacao = tipoAgregacao;
	}

}