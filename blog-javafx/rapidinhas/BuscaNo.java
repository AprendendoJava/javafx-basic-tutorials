import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.geometry.Pos;

public class BuscaNo extends Application {

	final int TOTAL_LABELS = 15;
	final int T_NORMAL = 15;
	final int T_GRANDE = 20;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Random random = new Random();
		HBox hbBusca = new HBox(10);
		VBox vbNodes = new VBox(10);
		VBox vbRaiz = new VBox(20);
		Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
		// Campos do topo da App para busca de elementos
		TextField txtSeletor = new TextField();
		Button btnBuscar = new Button("Buscar");
		hbBusca.getChildren().addAll(new Label("Seletor CSS: "), txtSeletor, btnBuscar);
		// Gera Labels aleatórios e adiciona à lista
		IntStream.range(0, TOTAL_LABELS).mapToObj(i -> {
			Label l = new Label();
			String id = "ID-" + i;
			// O nome da classe pode e deve repetir as vezes
			String cl = "CL-" + Math.abs(random.nextInt()%(TOTAL_LABELS/2));
			l.setId(id);
			l.setFont(Font.font(T_NORMAL));
			l.getStyleClass().add(cl);
			l.setText(id + " | " + cl);
			return l; 
		}).forEach(n -> vbNodes.getChildren().add(n));
		dialogoErro.setTitle("Nenhum nó encontrado!");
		vbRaiz.getChildren().addAll(hbBusca, new Separator(), vbNodes);	
		vbNodes.setAlignment(Pos.CENTER);
		// Stage
		stage.setTitle("Buscando elementos");
		stage.setScene(new Scene(vbRaiz, 400, 450, Color.WHITE));
		stage.show();
		// Listeners
		btnBuscar.setOnAction(e -> {
			String busca = txtSeletor.getText();
			Set<Node> nodesEncontrados = vbNodes.lookupAll(busca);
			// não encontrou nada
			if(nodesEncontrados.size() == 0) {
				dialogoErro.setContentText("Nenhum nó encontrado para busca: " + busca);
				dialogoErro.showAndWait();
			} else {
				// encontrou! ajusta os não encontrados e destaca os encontrados!
				vbNodes.getChildren().stream().map(n -> (Label) n).forEach(l -> {
					l.setFont(Font.font(T_NORMAL));
					l.setTextFill(Color.BLACK);
				});
				nodesEncontrados.stream().map(n -> (Label) n).forEach(l -> {
					l.setFont(Font.font(T_GRANDE));
					l.setTextFill(Color.RED);
				});	
			}
		});
	}

}
