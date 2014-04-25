/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post13.tabelas;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author william
 */
public class Post13Tabelas extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("William", 32, "william@email.com"),
                new Pessoa("Luana", 17, "luana@email.com"),
                new Pessoa("Maria", 12, "maria@email.com"),
                new Pessoa("João", 15, "joao@email.com"),
                new Pessoa("Antônio", 28, "antonio@email.com"),
                new Pessoa("Teles", 17, "teles@email.com"),
                new Pessoa("Eduan", 30, "eduan@email.com"),
                new Pessoa("Gabu", 22, "gabu@email.com")
        );

        TableView<Pessoa> tabela = new TableView<>();
        TableColumn<Pessoa, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Pessoa, String> colunaIdade = new TableColumn<>("Idade");
        TableColumn<Pessoa, String> colunaEmail = new TableColumn<>("E-mail");

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabela.setItems(FXCollections.observableArrayList(pessoas));
        tabela.getColumns().addAll(colunaNome, colunaIdade, colunaEmail);
        
        primaryStage.setScene(new Scene(tabela));
        primaryStage.setWidth(250);
        primaryStage.setHeight(300);
        primaryStage.setTitle("Tabelas no JavaFX");
        primaryStage.show();
    }

    public static class Pessoa {

        private String nome;
        private int idade;
        private String email;

        public Pessoa(String nome, int idade, String email) {
            this.nome = nome;
            this.idade = idade;
            this.email = email;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
}
