package org.aprendendojavafx.crud.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.aprendendojavafx.crud.model.Conta;
import org.aprendendojavafx.crud.service.ContasService;

/**
 * 
 * Uma implementação do ContasService para lidar com arquivo CSV
 * @author wsiqueir
 *
 */
public class ContasCSVService implements ContasService {

	// divisor de colunas no arquivo
	private static final String SEPARADOR = ";";

	// o caminho para o arquivo deve ser selecionado aqui
	private static final Path ARQUIVO_SAIDA = Paths.get("./dados.csv");

	// os dados do arquivo
	private List<Conta> contas;

	// formato de data usado no arquivo
	final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

	public ContasCSVService() {
		carregaDados();
	}

	@Override
	public void salvar(Conta conta) {
		conta.setId(ultimoId() + 1);
		contas.add(conta);
		salvaDados();
	}


	@Override
	public void atualizar(Conta conta) {
		Conta contaAntiga = buscaPorId(conta.getId());
		contaAntiga.setConcessionaria(conta.getConcessionaria());
		contaAntiga.setDataVencimento(conta.getDataVencimento());
		contaAntiga.setDescricao(conta.getDescricao());
		salvaDados();
	}

	@Override
	public List<Conta> buscarTodas() {
		return contas;
	}

	@Override
	public void apagar(int id) {
		Conta conta = buscaPorId(id);
		contas.remove(conta);
		salvaDados();
	}

	public Conta buscaPorId(int id) {
		return contas.stream().filter(c -> c.getId() == id).findFirst()
				.orElseThrow(() -> new Error("Conta não encontrada"));
	}

	// salva a lista de dados no arquivo, gerando um novo CSV e escrevendo o arquivo completamente
	private void salvaDados() {
		StringBuffer sb = new StringBuffer();
		for (Conta c : contas) {
			String linha = criaLinha(c);
			sb.append(linha);
			sb.append(System.getProperty("line.separator"));
		}
		try {
			Files.delete(ARQUIVO_SAIDA);
			Files.write(ARQUIVO_SAIDA, sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// o ID mais alto é retornado aqui para continuarmos contando os IDs
	private int ultimoId() {
		return contas.stream().mapToInt(Conta::getId).max().orElse(0);
	}

	// carrega os dados do arquivo para a lista contas
	private void carregaDados() {
		try {
			if(!Files.exists(ARQUIVO_SAIDA)) {
				Files.createFile(ARQUIVO_SAIDA);
			}
			contas = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// transforma uma linha do CSV para o tipo Conta
	private Conta leLinha(String linha) {
		String colunas[] = linha.split(SEPARADOR);
		int id = Integer.parseInt(colunas[0]);
		Date dataVencimento = null;
		try {
			dataVencimento = formatoData.parse(colunas[3]);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Conta conta = new Conta();
		conta.setId(id);
		conta.setConcessionaria(colunas[1]);
		conta.setDescricao(colunas[2]);
		conta.setDataVencimento(dataVencimento);
		return conta;
	}
	
	// transforma um objeto conta em um arquivo CSV
	private String criaLinha(Conta c) {
		String dataStr = formatoData.format(c.getDataVencimento());
		String idStr = String.valueOf(c.getId());
		String linha = String.join(SEPARADOR, idStr, c.getConcessionaria(), c.getDescricao(),
				dataStr);
		return linha;
	}

}