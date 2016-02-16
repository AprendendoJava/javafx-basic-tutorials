package org.aprendendojavafx.crud.model;

import java.util.Date;

/**
 * 
 * Nossa classe de modelo do objeto que "sofrerá" as operações de CRUD
 * @author wsiqueir
 *
 */
public class Conta {

	private int id;
	private String concessionaria;
	private String descricao;
	private Date dataVencimento;

	// gets e sets
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConcessionaria() {
		return concessionaria;
	}

	public void setConcessionaria(String concessionaria) {
		this.concessionaria = concessionaria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}