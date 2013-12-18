/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 */

package Interface;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CloseBox
{
	private boolean toReturn;
	/** 
	 * @author Samuel Gagnon
	 * @author Keven Langlois
	 * @param parent
	 * @since Version 1.0 Fenêtre de confirmation de fermeture du jeu.
	 */
	public CloseBox(Stage parent)
	{
		final Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.initOwner(parent);

		GridPane pane = new GridPane();
		Label txtClose = new Label("Voulez-vous vraiment fermer l'application ?");
		pane.add(txtClose, 0, 0);

		Button btnYes = new Button("Oui");
		pane.add(btnYes, 0, 1);
		btnYes.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				toReturn = true;
				stage.close();
			}
		});

		Button btnNo = new Button("Non");
		pane.add(btnNo, 0, 2);
		btnNo.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				toReturn = false;
				stage.close();
			}
		});
		Scene scene = new Scene(pane, Color.RED);

		stage.setScene(scene);
		stage.showAndWait();
	}

	/**
	 * @author Samuel Gagnon
	 * @author Keven Langlois
	 * @since Version 1.0 permet de savoir s'il faut fermer l'application ou pas.
	 * @return Retourne s'il faut fermer l'application ou pas.
	 */
	public boolean getReturn()
	{
		return this.toReturn;
	}
}
