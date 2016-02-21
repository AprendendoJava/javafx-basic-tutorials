package org.aprendendojavafx.crud.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aprendendojavafx.crud.model.Conta;
import org.aprendendojavafx.crud.service.ContasService;

/**
 * Faz as operações de CRUD usando banco de dados MySQL. Para rodar essa classe
 * vocë precisa <br />
 * - Certificar-se que o driver do MySQL está no classpath da aplicação -  Criar
 * o banco de dados e atualizar o código abaixo de acordo com o seu banco - 
 * Criar a tabela contas.
 * 
 * Se houver qualquer erro, o programa irá sair, assim, cheque os logs para ver o erro.
 * 
 * @author wsiqueir
 *
 */
public class ContasDBService implements ContasService {

	// dados para acesso ao banco, atualize de acordo com o seu banco de dados
	final String USUARIO = "contas_app";
	final String SENHA = "aprendajavafx";
	final String URL_BANCO = "jdbc:mysql://localhost:3306/crud-contas";

	// constantes de acesso
	final String CLASSE_DRIVER = "com.mysql.jdbc.Driver";

	// comandos
	final String INSERIR = "INSERT INTO contas(concessionaria, descricao, data_vencimento) VALUES(?, ?, STR_TO_DATE(?, '%d/%m/%Y'))";
	final String ATUALIZAR = "UPDATE contas SET concessionaria=?, descricao=?, data_vencimento = STR_TO_DATE(?, '%d/%m/%Y') WHERE id = ?";
	final String BUSCAR = "SELECT id, concessionaria, descricao, DATE_FORMAT(data_vencimento, %d/%m/%Y') FROM contas WHERE ID = ?";
	final String BUSCAR_TODOS = "SELECT id, concessionaria, descricao, DATE_FORMAT(data_vencimento, '%d/%m/%Y') FROM contas";
	final String APAGAR = "DELETE FROM contas WHERE id = ?";

	// tratamento de data

	final String FORMATO_DATA = "dd/MM/yyyy";
	final SimpleDateFormat FORMATADOR = new SimpleDateFormat(FORMATO_DATA);

	@Override
	public void salvar(Conta conta) {
		try {
			Connection con = conexao();
			PreparedStatement salvar = con.prepareStatement(INSERIR);
			String dateStr = FORMATADOR.format(conta.getDataVencimento());
			salvar.setString(1, conta.getConcessionaria());
			salvar.setString(2, conta.getDescricao());
			salvar.setString(3, dateStr);
			salvar.executeUpdate();
			salvar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR SALVANDO CONTA");
			System.exit(0);
		} 
	}

	@Override
	public List<Conta> buscarTodas() {
		List<Conta> contas = new ArrayList<>();
		try {
			Connection con = conexao();
			PreparedStatement buscarTodos = con.prepareStatement(BUSCAR_TODOS);
			ResultSet resultadoBusca = buscarTodos.executeQuery();
			while (resultadoBusca.next()) {
				Conta conta = extraiConta(resultadoBusca);
				contas.add(conta);
			}
			buscarTodos.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR BUSCANDO TODAS AS CONTAS.");
			System.exit(0);
		} 
		return contas;
	}

	@Override
	public Conta buscaPorId(int id) {
		Conta conta = null;
		try {
			Connection con = conexao();
			PreparedStatement buscar = con.prepareStatement(BUSCAR);
			buscar.setInt(1, id);
			ResultSet resultadoBusca = buscar.executeQuery();
			resultadoBusca.next();
			conta = extraiConta(resultadoBusca);
			buscar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR BUSCANDO CONTA COM ID " + id);
			System.exit(0);
		} 
		return conta;
	}

	@Override
	public void apagar(int id) {
		try {
			Connection con = conexao();
			PreparedStatement apagar = con.prepareStatement(APAGAR);
			apagar.setInt(1, id);
			apagar.executeUpdate();
			apagar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR APAGANDO CONTA COM ID " + id);
			System.exit(0);
		} 
	}

	@Override
	public void atualizar(Conta conta) {
		try {
			Connection con = conexao();
			PreparedStatement atualizar = con.prepareStatement(ATUALIZAR);
			String dateStr = FORMATADOR.format(conta.getDataVencimento());
			atualizar.setString(1, conta.getConcessionaria());
			atualizar.setString(2, conta.getDescricao());
			atualizar.setString(3, dateStr);
			atualizar.setInt(4, conta.getId());
			atualizar.executeUpdate();
			atualizar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR ATUALIZANDO CONTA COM ID " + conta.getId());
			System.exit(0);
		} 

	}

	// abre uma nova conexão com o banco de dados. Se algum erro for lançado
	// aqui, verifique o erro com atenção e se o banco está rodando
	private Connection conexao() {
		try {
			Class.forName(CLASSE_DRIVER);
			return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof ClassNotFoundException) {
				System.err.println("VERIFIQUE SE O DRIVER DO BANCO DE DADOS ESTÁ NO CLASSPATH");
			} else {
				System.err.println("VERIFIQUE SE O BANCO ESTÁ RODANDO E SE OS DADOS DE CONEXÃO ESTÃO CORRETOS");
			}
			System.exit(0);
			// o sistema deverá sair antes de chegar aqui...
			return null;
		}
	}

	// extrain o objeto Conta do result set
	private Conta extraiConta(ResultSet resultadoBusca) throws SQLException, ParseException {
		Conta conta = new Conta();
		conta.setId(resultadoBusca.getInt(1));
		conta.setConcessionaria(resultadoBusca.getString(2));
		conta.setDescricao(resultadoBusca.getString(3));
		Date dataVencimento = FORMATADOR.parse(resultadoBusca.getString(4));
		conta.setDataVencimento(dataVencimento);
		return conta;
	}

}