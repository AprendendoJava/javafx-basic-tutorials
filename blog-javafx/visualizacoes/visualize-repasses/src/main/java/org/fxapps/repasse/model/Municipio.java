package org.fxapps.repasse.model;

public class Municipio {

	private long id;

	private String codigoSIAFI;

	private String nome;

	private String regiao;


	public Municipio() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getCodigoSIAFI() {
		return codigoSIAFI;
	}

	public void setCodigoSIAFI(String codigoSIAFI) {
		this.codigoSIAFI = codigoSIAFI;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}

}
