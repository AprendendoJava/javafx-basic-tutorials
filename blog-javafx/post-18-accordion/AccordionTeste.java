import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AccordionTeste extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		// criamos o Accordion
		Accordion accordion = new Accordion();
		// criando os paineis do Accordion. Notem que o construtor recebe o
		// texto que vai aparecer e o nó
		TitledPane painel1 = new TitledPane("Painel 1", new Label(
				"O Primeiro Painel"));
		TitledPane painel2 = new TitledPane("Painel 2", new Button(
				"Sou um botão do painel 2"));
		TitledPane painel3 = new TitledPane("Painel 3", new Rectangle(150, 50));
		// Adicionando um ícone ao Painel
		painel1.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("coracao.png"))));
		painel2.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("fogo.png"))));
		painel3.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("agua.png"))));
		// agora adicionamos todas as abas de vez
		accordion.getPanes().addAll(painel1, painel2, painel3);
		// aqui deixamos o painel que vai expandido por padrao
		accordion.setExpandedPane(painel1);
		// definimos um padrão mínimo para o painel ou ele vai se dimensionar de
		// acordo com o tamanho dos nós
		accordion.setMinSize(300, 300);

		// coisas do javafx
		s.setScene(new Scene(accordion));
		s.setTitle("Testando Painel de Abas");
		s.show();
		s.setWidth(400);
		s.setWidth(300);
	}
}
