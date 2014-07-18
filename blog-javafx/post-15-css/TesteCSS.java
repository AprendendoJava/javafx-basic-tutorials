import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.BorderPane;

public class TesteCSS extends Application {

	String txt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc porta erat id lectus interdum, a pharetra est luctus. Nulla interdum convallis molestie. In hac habitasse platea dictumst. Ut ullamcorper ultricies viverra. Quisque blandit libero in ante sagittis sagittis. Ut gravida nibh quis justo sodales rutrum. Fusce euismod diam diam, vitae vulputate urna placerat vel. ";
	
	public void start(Stage s) {
		// declarações
		BorderPane raiz = new BorderPane();
		HBox pnlCentro = new HBox(50);
		VBox vbEsquerda = new VBox(10);
		VBox vbDireita = new VBox(10);
		Button btnAplicar = new Button("Aplicar CSS");
		TextArea txtCSS = new TextArea();
		Text txtAlvo = new Text(txt);
		Label lblTitulo = new Label("Testando CSS");
		Scene cena = new Scene(raiz, 800, 250);

		// configurando Layout e adicionando componentes
		vbEsquerda.getChildren().addAll(new Label("Entre o CSS aqui"), txtCSS);
		vbDireita.getChildren().addAll(new Label("O texto Alvo"), txtAlvo);

		pnlCentro.getChildren().addAll(vbEsquerda, btnAplicar, vbDireita);
		pnlCentro.setAlignment(Pos.CENTER);

		raiz.setCenter(pnlCentro);
		raiz.setTop(lblTitulo);
		BorderPane.setAlignment(lblTitulo, Pos.CENTER);	

		txtCSS.setMinWidth(350);
		txtAlvo.setWrappingWidth(220);
		btnAplicar.setMinWidth(120);
		btnAplicar.setOnAction( event -> {
			txtAlvo.setStyle(txtCSS.getText());
		});
		// configuramos classes para os nossos labels especiais	
		lblTitulo.getStyleClass().add("titulo");
		// informamos o arquivo principal de CSS	
		cena.getStylesheets().add("app.css");
		s.setScene(cena);
		s.setTitle("Teste de CSS do JavaFX");
		s.show();
	}
	
}
