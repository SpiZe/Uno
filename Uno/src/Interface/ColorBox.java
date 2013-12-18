package Interface;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Cards.Card.CardColor;

public class ColorBox
{
	private Scene scene;
	private BorderPane pane;
	private Label text;
	private CardColor color;
	private GridPane colorPane;
	private GridPane topPane;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * @Since Version 1.0
	 * Permet de choisir contre combien d'IA le joueur joue. 
	 */
	public ColorBox()
	{
		final Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Aide");
		stage.setResizable(false);
		pane = new BorderPane();
		scene = new Scene(pane, Color.RED);
		text = new Label("Veuillez choisir votre couleur");
		colorPane = new GridPane();
		topPane = new GridPane();
		topPane.add(text, 0, 0);
		btn1 = new Button("Bleu");
		btn1.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			/**
			 * @author Keven Langlois
			 * @author Samuel Gagnon
			 * @Since Version 1.0
			 * @Param event : Représente l'événement
			 * Permet de gérer l'événement du bouton "Bleu". 
			 */
			public void handle(MouseEvent event)
			{
				color = CardColor.BLUE;
				stage.close();
			}
		});
		/**
		 * @author Keven Langlois
		 * @author Samuel Gagnon
		 * @Since Version 1.0
		 * @Param event : Représente l'événement
		 * Permet de gérer l'événement du bouton "Rouge". 
		 */
		btn2 = new Button("Rouge");
		btn2.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				color = CardColor.RED;
				stage.close();
			}
		});
		/**
		 * @author Keven Langlois
		 * @author Samuel Gagnon
		 * @Since Version 1.0
		 * @Param event : Représente l'événement
		 * Permet de gérer l'événement du bouton "Jaune". 
		 */
		btn3 = new Button("Jaune");
		btn3.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				color = CardColor.YELLOW;
				stage.close();
			}
		});
		/**
		 * @author Keven Langlois
		 * @author Samuel Gagnon
		 * @Since Version 1.0
		 * @Param event : Représente l'événement
		 * Permet de gérer l'événement du bouton "Vert". 
		 */
		btn4 = new Button("Vert");
		btn4.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				color = CardColor.GREEN;
				stage.close();
			}
		});

		colorPane.add(btn1, 1, 0);
		colorPane.add(btn2, 2, 0);
		colorPane.add(btn3, 3, 0);
		colorPane.add(btn4, 4, 0);

		pane.setCenter(colorPane);
		pane.setTop(topPane);

		stage.setScene(scene);
		stage.showAndWait();
	}
	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * @Since Version 1.0
	 * Permet de savoir quel couleur à été sélectionné. 
	 */
	public CardColor getColor()
	{
		return this.color;
	}
}
