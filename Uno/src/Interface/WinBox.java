package Interface;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WinBox
{
	private Scene scene;
	private BorderPane pane;
	private Label text;
	private GridPane topPane;
	
	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * @Since Version 1.0
	 * Permet de choisir contre combien d'IA le joueur joue. 
	 */
	public WinBox(int nbPlayer)
	{
		final Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Aide");
		stage.setResizable(false);
		pane = new BorderPane();
		scene = new Scene(pane, Color.RED);
		text = new Label("Le joueur " + Integer.toString(nbPlayer + 1) + " à gagné la partie!");
		topPane = new GridPane();
		topPane.add(text, 0, 0);

		pane.setTop(topPane);

		stage.setScene(scene);
		stage.showAndWait();
	}
}
