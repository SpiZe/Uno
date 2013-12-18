/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 */

package Interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppFrame extends Application
{

	private static final String LOGO = "./res/images.jpg";
	private Scene scene;
	private BorderPane pane;
	private GridPane centerPane;
	private Label text;
	private static int NBPLAYER = 2;

	@Override
	public void start(final Stage primaryStage)
	{
		Socket socket;
		BufferedReader in;

		try
		{
			socket = new Socket("localhost", 51004);

			primaryStage.setTitle("SamuelGagnon_KevenLanglois");
			primaryStage.setResizable(false);
			pane = new BorderPane();
			scene = new Scene(pane, Color.RED);
			text = new Label("En attente d'un adversaire.");

			ImageView logo = new ImageView(new Image(getClass().getResourceAsStream(LOGO)));
			pane.setTop(logo);

			centerPane = new GridPane();
			centerPane.add(text, 0, 0);
			centerPane.setHgap(10);
			centerPane.setVgap(10);
			pane.setCenter(centerPane);

			primaryStage.setScene(scene);
			primaryStage.show();

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String xml = in.readLine();

			SAXBuilder saxBuilder = new SAXBuilder();

			Document doc = saxBuilder.build(new StringReader(xml));
			String x = doc.getRootElement().getText();
			text.setText(x);
			Element element = doc.getRootElement();

			if (element.getName() == "Error")
			{
				primaryStage.close();
			}
			else
			{
				if (element.getName() == "Start")
				{
					text.setText("Start");
					GameInterface game = new GameInterface(primaryStage, NBPLAYER, socket);
				}
			}

			// socket.close(); OU VA CECI ??
		}
		catch (Exception e)
		{
		}
		// Quant tu cree un game interface kev, passe lui primaryStage pis
		// NBPLAYER en param.
	}
}
