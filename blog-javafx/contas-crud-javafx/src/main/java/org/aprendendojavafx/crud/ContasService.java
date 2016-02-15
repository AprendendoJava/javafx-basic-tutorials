package org.aprendendojavafx.crud;

import java.util.List;

/**
 * 
 * Uma interface para definir as operações com o objeto conta
 * 
 * @author wsiqueir
 *
 */
public interface ContasService {

	public void salvar(Conta conta);
	
	public void atualizar(Conta conta);
	
	public List<Conta> buscarTodas();
	
	public void apagar(int id);
	
	public Conta buscaPorId(int id);
	
	public static ContasService getInstance() {
		return new ContasCSVService();
	}

}