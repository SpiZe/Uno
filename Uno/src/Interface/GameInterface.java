/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 */

package Interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Cards.Card;
import Cards.Card.CardColor;
import Cards.WildCard;
import Cards.WildDrawCard;
import backend.Game;

public class GameInterface
{
	private static final String DEFAULT = "./res/default.png";
	private BorderPane pane;
	private Scene scene;
	private int nbPlayer;
	private GridPane centerPane;
	private GridPane player1;
	private GridPane player2;
	private Group playerList;
	private Game game;
	protected GameInterface gameInt;
	private final Stage gameStage;
	private Socket socket;
	private int playerNumber;

	public GameInterface(Stage stage, int nbPlayer, Socket socket)
	{
		this.socket = socket;
		gameStage = new Stage(StageStyle.UTILITY);
		gameStage.initModality(Modality.WINDOW_MODAL);
		gameStage.initOwner(stage);
		gameStage.setTitle("UNO - Jeu");
		gameStage.setResizable(true);
		pane = new BorderPane();
		scene = new Scene(pane, Color.GREEN);
		this.nbPlayer = nbPlayer;
		this.gameInt = this;
		playerList = new Group();

		game = new Game(nbPlayer, this, socket);
		this.playerNumber = WhoAMI();

		setPlayer();
		createGameSpace();
		pane.setBottom(this.player1);
		pane.setTop(this.player2);
		pane.setPadding(new Insets(20, 20, 20, 20));
		gameStage.setScene(scene);
		gameStage.showAndWait();
	}

	private int WhoAMI()
	{
		int i = 1;
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String xml = in.readLine();
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(new StringReader(xml));

			String player = doc.getRootElement().getText();
			i = Integer.parseInt(player);
		}
		catch (Exception e)
		{
		}

		return i;
	}

	private void createGameSpace()
	{
		centerDeck();
		gameInit();
		OpponantDeck();
		playerDeck();
	}

	private void gameInit()
	{
		VisualCard deckCard = new VisualCard(game.FirstCard(), centerPane);
		centerPane.add(deckCard, 1, 2);
	}

	public void Victory(int nbOfPlayer)
	{
		Element gameRunning = new Element("False");
		Document doc = new Document(gameRunning);
		XMLOutputter xmlOut = new XMLOutputter();
		try
		{
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.write(xmlOut.outputString(doc));
			out.flush();
		}
		catch (IOException e)
		{
		}
		
		WinBox win = new WinBox(nbOfPlayer);
		gameStage.close();
	}

	private void playerDeck()
	{
		this.player1 = new GridPane();
		int i = 0;
		Card[] cardList = game.GameStart();
		for (Card card : cardList)
		{
			this.player1.add(new VisualCard(card, this.player1), i, 0);
			i++;
		}

		Button btnPlay = new Button("Jouer");
		btnPlay.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (playerNumber == game.getCurrentPlayer() + 1)
				{
					Card cardToPlay = null;
					VisualCard toRemove = null;

					for (Node node : gameInt.player1.getChildren())
					{
						if (node instanceof VisualCard)
						{
							VisualCard vCard = (VisualCard) node;
							if (vCard.getUp())
							{
								cardToPlay = vCard.getCard();
								if (game.CardCanBePlayed(cardToPlay))
								{
									toRemove = vCard;
								}
							}
						}
					}

					if (toRemove != null)
					{
						toRemove.Delete(player1);
					}

					Card[] cardToAdd = null;
					if (cardToPlay != null && game.CardCanBePlayed(cardToPlay))
					{
						VisualCard deckCard = new VisualCard(cardToPlay, centerPane);
						if (cardToPlay instanceof WildCard || cardToPlay instanceof WildDrawCard)
						{
							CardColor color = GetChangedColor();
							cardToAdd = game.CardPlayed(cardToPlay, color);
							deckCard = new VisualCard(centerPane, color);
						}
						else
						{
							cardToAdd = game.CardPlayed(cardToPlay);
							deckCard = new VisualCard(cardToPlay, centerPane);
						}

						centerPane.add(deckCard, 1, 2);

						if (cardToAdd != null)
						{
							switch (gameInt.game.getCurrentPlayer())
							{
							case 0:
								// On devrait pouvoir voir celle du joueur.
								for (Card card : cardToAdd)
								{
									player1.add(new VisualCard(card, player1), player1.getChildren().size() + 1, 0);
								}
								break;
							case 1:
								AddCard(player2, cardToAdd.length);
								break;
							}
						}
						gameInt.game.EndTurn();
					}
				}
			}
		});
		this.player1.add(btnPlay, 0, 1);
	}

	public void ShowOpponantPlayedCard(Card card, int playerNumber)
	{
		VisualCard deckCard = new VisualCard(card, centerPane);
		centerPane.add(deckCard, 1, 2);

		switch (playerNumber)
		{
		case 0:
			removeOpponantCard(player1, 1);
			break;
		case 1:
			removeOpponantCard(player2, 1);
			break;
		}
	}

	private CardColor GetChangedColor()
	{
		ColorBox color = new ColorBox();
		return color.getColor();
	}

	private void centerDeck()
	{
		this.centerPane = new GridPane();
		Button btnPickCard = new Button("Pigé une carte");
		btnPickCard.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				for (int i = 0; i < 50; i++)
				{
					AddPlayerCard();
				}
				game.EndTurn();
			}
		});
		this.centerPane.add(btnPickCard, 2, 2);
		pane.setCenter(this.centerPane);
	}

	private void AddPlayerCard()
	{
		this.player1.add(new VisualCard(game.DrawCard(), this.player1), this.player1.getChildren().size() + 1, 0);
	}

	public void AddDrawnCards(int playerNumber, Card[] cards)
	{
		if (cards != null)
		{
			switch (playerNumber)
			{
			case 0:
				for (Card card : cards)
				{
					this.player1.add(new VisualCard(card, this.player1), player1.getChildren().size() + 1, 0);
				}
				break;
			case 1:
				AddCard(player2, cards.length);
				break;
			}
		}
	}

	private void AddCard(GridPane playerDeck, int nbCard)
	{
		for (Node player : pane.getChildren())
		{
			try
			{
				if (player instanceof GridPane)
				{
					GridPane playerPane = (GridPane) player;

					if (playerPane == playerDeck)
					{
						for (int i = 0; i < nbCard; i++)
						{
							VisualCard vCard = new VisualCard(playerDeck);
							playerPane.add(vCard, playerPane.getChildren().size() + 1, 0);
						}
					}
				}
			}
			catch (Exception e)
			{
				// ne fait que passer par dessus car ce n'est pas un GridPane,
				// ne rien faire
			}
		}
	}

	private void removeOpponantCard(GridPane playerDeck, int nbCard)
	{
		for (Node player : pane.getChildren())
		{
			try
			{
				if (player instanceof GridPane)
				{
					GridPane playerPane = (GridPane) player;

					if (playerPane == playerDeck)
					{
						for (int i = 0; i < nbCard; i++)
						{
							playerPane.getChildren().remove(playerPane.getChildren().size() - 1);
						}
					}
				}
			}
			catch (Exception e)
			{
				// ne fait que passer par dessus car ce n'est pas un GridPane,
				// ne rien faire
			}
		}
	}

	private void OpponantDeck()
	{
		boolean top = true;
		for (Node player : playerList.getChildren())
		{
			for (int i = 0; i < 7; i++)
			{
				VisualCard vCard = new VisualCard(this.player2);
				GridPane playerPane = (GridPane) player;
				if (playerPane instanceof GridPane)
				{
					if (top)
					{
						playerPane.add(vCard, i, 0);
					}
					else
					{
						playerPane.add(vCard, 0, i);
					}
				}
			}
			if (top)
			{
				top = false;
			}
		}
	}

	private void setPlayer()
	{

		switch (nbPlayer)
		{
		case 2:
			this.player2 = new GridPane();
			this.player2.setUserData("player2");
			playerList.getChildren().add(this.player2);
			break;
		}
	}
}
