import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TreeViewTeste extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		// a nossa árvore
		TreeView<String> arvore = new TreeView<>();
		// Esse é o item raiz da nossa árvore. Embaixo dele temos que colocar
		// mais itens
		TreeItem<String> raiz = new TreeItem<String>("Raiz");
		// Os itens podem ser aninhados, abaixo criamos dois items filhos no
		// nível 1 e para cada um desses, três filhotes no nível 2
		for (int i = 0; i < 2; i++) {
			TreeItem<String> lvl1 = new TreeItem<String>("LVL1 " + i);
			for (int j = 0; j < 3; j++) {
				lvl1.getChildren().add(new TreeItem<String>("LVL2 " + j));
			}
			raiz.getChildren().add(lvl1);
		}
		// você pode expandir para por padrão mostrar os filhos de um item.
		// Vamos fazer isso com a nossa raiz
		raiz.setExpanded(true);
		// agora setamos a raiz da nossa árvore
		arvore.setRoot(raiz);

		// como a árvore é um nó, podemos brincar com ela...
		arvore.setRotate(30);
		arvore.setEffect(new BoxBlur());
		
		// cada Item pode ter um ícone. Aqui a gente adiciona um simples ícone
		// de uma árvore para demonstrar o uso desse método
		raiz.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(
				"./arvore.png"))));
		// coisas do JavaFX...
		s.setScene(new Scene(arvore));
		s.setTitle("Testando o TreeView");
		s.show();
	}

}
