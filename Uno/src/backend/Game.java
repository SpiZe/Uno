package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import Cards.Card;
import Cards.Card.CardColor;
import Cards.DrawCard;
import Cards.NumberCard;
import Cards.ReverseCard;
import Cards.SkipCard;
import Cards.WildCard;
import Cards.WildDrawCard;
import Deck.Deck;
import Deck.PlayedDeck;
import Interface.GameInterface;

/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 * @since version 1.0
 * Classe Game, s'occupe du bon fonctionnement de la partie, de la gestion des tours, etc...
 */
public class Game
{
	private static final int NumberOfStartingCardsPerPlayer = 7;
	private static final int CardsToDrawPerWildDrawCard = 4;
	private static final int CardsToDrawPerDrawCard = 2;
	private Deck deck;
	private PlayedDeck playedDeck;
	private Player[] playerList;
	private int nbOfPlayers;
	private int currentPlayer = 0;
	private boolean playerDirection; // True for increment, false for decrement.
	private GameInterface visuals;
	private Socket socket;

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Constructeur de game, Crée un jeu de cartes et le mélange, assigne les attributs à leurs valeurs
	 * originelles et crée les joueurs.
	 * @param numberOfPlayers Nombre de joueurs dans la partie
	 * @param visuals l'interface du jeu
	 * @see Deck
	 * @see Player
	 * @see PlayedDeck
	 * @see GameInterface
	 */
	public Game(int numberOfPlayers, GameInterface visuals, Socket s)
	{
		this.socket = s;
		
		try
		{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object o = in.readObject();
			this.deck = (Deck) o;
		}
		catch (Exception e){}
		
		
		this.visuals = visuals;

		this.nbOfPlayers = numberOfPlayers;
		this.playedDeck = new PlayedDeck();
		this.playerDirection = true;
		this.playerList = new Player[this.nbOfPlayers];

		for (int i = 0; i < numberOfPlayers; i++)
		{
			this.playerList[i] = new Player();
		}
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Constructeur de game, Crée un jeu de cartes et le mélange, assigne les attributs à leurs valeurs
	 * originelles et crée les joueurs.
	 * @param numberOfPlayers Nombre de joueurs dans la partie
	 * @param playerList la liste des joueurs
	 * @param playedDeck le jeu de cartes joué précédemment
	 * @param deck Le jeu de cartes
	 * @param visuals l'interface du jeu
	 * @see Deck
	 * @see Player
	 * @see PlayedDeck
	 * @see GameInterface
	 */
	public Game(Deck deck, Player[] playerList, PlayedDeck playedDeck, int numberOfPlayers, GameInterface visuals)
	{
		this.deck = deck;
		this.deck.Shuffle();

		this.playerList = playerList;
		this.playerDirection = true;
		this.nbOfPlayers = numberOfPlayers;
		this.playedDeck = playedDeck;
		this.visuals = visuals;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Fait piger une carte au joueur courant
	 * @return La carte pigé
	 * @see Card
	 * @see Player
	 */
	public Card DrawCard()
	{
		return this.playerList[this.currentPlayer].DrawCard(this.deck);
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Fait piger les cartes initiales aux joueurs
	 * @return Un array de cartes contenant toutes les cartes des joueurs
	 * @see Card
	 * @see Player
	 */
	public Card[] GameStart()
	{
		Card[] toDraw = new Card[NumberOfStartingCardsPerPlayer];

		for (int o = 0; o < this.nbOfPlayers; o++)
		{
			if (o == 0)
			{
				toDraw = this.playerList[o].DrawCard(NumberOfStartingCardsPerPlayer, this.deck);
			}
			else
			{
				this.playerList[o].DrawCard(NumberOfStartingCardsPerPlayer, this.deck);
			}
		}

		return toDraw;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Tire une carte qui correspond à la première du jeu, si la carte n'est pas un numéro
	 * on repige jusqu'à ce que l'on aille un numéro. Les cartes pigés sont replacés, dans
	 * le jeu de cartes.
	 * @return La premiere carte du jeu
	 * @see Deck
	 * @see PlayedDeck
	 */
	public Card FirstCard()
	{
		Card[] discard = new Card[100];
		boolean validCard = false;
		int counter = 0;
		Card cardToSend = null;

		while (validCard == false)
		{
			cardToSend = this.deck.pop();
			if (cardToSend instanceof NumberCard)
			{
				this.playedDeck.add(cardToSend);
				validCard = true;
			}
			else
			{
				discard[counter] = cardToSend;
			}
		}

		for (int i = counter; i > 0; i++)
		{
			this.deck.add(discard[i]);
		}

		return cardToSend;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Vérifie si la carte passée en paramètre peut-être jouée.
	 * @param card La carte à vérifier
	 * @return true si la carte est jouable, false sinon
	 * @see Card
	 * @see PlayedDeck
	 */
	public boolean CardCanBePlayed(Card card)
	{
		if (this.playedDeck.CanBePlayed(card) == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Voir l'autre fonction CardPlayed
	 * @param card
	 * @return
	 */
	public Card[] CardPlayed(Card card)
	{
		return CardPlayed(card, null);
	}

	/** 
	 * La suite de choses a faire si une carte est joué, la retire de la main, l'ajoute à la pile,
	 * complète le tour du joueur et retourne les cartes pigées.
	 * @param card la carte jouée
	 * @param color la couleur choisie par le joueur si le joueur avait la possibilité de changer la couleur du jeu
	 * @return Un array de cartes correspondant aux cartes à piger.
	 * @see Card
	 * @see PlayerDeck
	 * @see PlayedDeck
	 */
	public Card[] CardPlayed(Card card, CardColor color)
	{
		//Send Card
		SendCard(card);
		
		Card[] cardsToDraw;
		this.playedDeck.add(card);
		this.playerList[this.currentPlayer].getHand().remove(card);
		cardsToDraw = CardEffect(card, color);
		SendNextPlayer();
		AssignNextPlayer();
		return cardsToDraw;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Complete le tour du joueur
	 * @see Card
	 */
	public void EndTurn()
	{
		try
		{
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String xml = in.readLine();
		SAXBuilder saxBuilder = new SAXBuilder();
		Document doc = saxBuilder.build(new StringReader(xml));
		Element cardElem = doc.getRootElement();
		Element typeElem = cardElem.getChild("Type");
		Element colorElem = cardElem.getChild("Color");
		Element numberElem = cardElem.getChild("Number");
		
		CardColor color = FindColorFromString(colorElem.getText());
		int number = Integer.parseInt(numberElem.getText());
		
		CreateCardAndPlay(color, number, typeElem.getText());
		}
		catch(Exception e){}
		
	}
	
	private void CreateCardAndPlay(CardColor color, int number, String type)
	{
		Card card = new NumberCard(0, CardColor.GREEN);
		
		switch (type)
		{
		case "NUMBERCARD":
			card = new NumberCard(number, color);
			break;
		case "DRAWCARD":
			card = new DrawCard(color);
			break;
		case "REVERSECARD":
			card = new ReverseCard(color);
			break;
		case "SKIPCARD":
			card = new SkipCard(color);
			break;
		case "WILDCARD":
			card = new WildCard(color);
			break;
		case "WILDDRAWCARD":
			card = new WildDrawCard(color);
			break;
		}
		
		this.playerList[this.currentPlayer].getHand().remove(card);
		this.visuals.ShowOpponantPlayedCard(card, 1);
		Card[] toSend = CardPlayed(card, color);
		this.visuals.AddDrawnCards(this.currentPlayer, toSend);
	}
	
	private CardColor FindColorFromString(String s)
	{
		switch(s)
		{
		case "RED":
			return CardColor.RED;
		case "BLUE":
			return CardColor.BLUE;
		case "GREEN":
			return CardColor.GREEN;
		case "YELLOW":
			return CardColor.YELLOW;
		default:
			return null;
		}
	}
	
	private void SendNextPlayer()
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(socket.getOutputStream());
			Element player = new Element("Player");
			int i = FindNextPlayer() + 1;
			String number = Integer.toString(i);
			player.setText(number);
			Document doc = new Document(player);
			XMLOutputter xmlOut = new XMLOutputter();
			out.write(xmlOut.outputString(doc));
			out.flush();
		}
		catch (IOException e)
		{
		}
		
	}
	
	private String FindColor(Card card)
	{
		switch(card.getColor())
		{
		case RED:
			return "RED";
		case BLUE:
			return "BLUE";
		case GREEN:
			return "GREEN";
		case YELLOW:
			return "YELLOW";
		default:
			return "NOCOLOR";
		}
	}
	
	private String FindCardType(Card card)
	{
		if (card instanceof DrawCard)
		{
			return "DRAWCARD";
		}
		if (card instanceof WildCard)
		{
			return "WILDCARD";
		}
		if (card instanceof SkipCard)
		{
			return "SKIPCARD";
		}
		if (card instanceof ReverseCard)
		{
			return "REVERSECARD";
		}
		if (card instanceof WildDrawCard)
		{
			return "WILDDRAWCARD";
		}
		return "NUMBERCARD";
	}
	
	private String FindNumber(Card card)
	{
		if (card instanceof NumberCard)
		{
			return Integer.toString(((NumberCard) card).getNumber());
		}
		else
		{
			return "NONUMBER";
		}
	}
	
	private void SendCard(Card card)
	{
		PrintWriter out;
		String color = FindColor(card);
		String type = FindCardType(card);
		String number = FindNumber(card);
		
		try
		{
			out = new PrintWriter(socket.getOutputStream());
			Element cardElement = new Element("Card");
			Element typeElement = new Element("Type");
			typeElement.setText(type);
			Element colorElement = new Element("Color");
			colorElement.setText(color);
			Element numberElement = new Element("Number");
			numberElement.setText(number);
			cardElement.addContent(typeElement);
			cardElement.addContent(colorElement);
			cardElement.addContent(numberElement);
			
			Document doc = new Document(cardElement);
			XMLOutputter xmlOut = new XMLOutputter();
			out.write(xmlOut.outputString(doc));
			out.flush();
		}
		catch (IOException e)
		{
		}
	}

	private Card[] CardEffect(Card card, CardColor color)
	{
		// If card instanceof numbercard -> no special effect
		// MUST SEND CARDS DREWN CARDS TO INTERFACE
		// MUST RECEIVE CHOSEN COLOR FROM INTERFACE

		if (card instanceof DrawCard)
		{
			// Next player draws 2 cards -DONE
			return this.playerList[FindNextPlayer()].DrawCard(CardsToDrawPerDrawCard, this.deck);
		}
		if (card instanceof WildCard)
		{
			// Choose new color -DONE
			((WildCard) card).setColor(color);
			return null;
		}
		if (card instanceof SkipCard)
		{
			// Next player skip turn - DONE
			this.playerList[FindNextPlayer()].setCanPlay(false);
			return null;
		}
		if (card instanceof ReverseCard)
		{
			// Player order reversed - DONE
			this.playerDirection = false;
			return null;
		}
		if (card instanceof WildDrawCard)
		{
			// Choose Color -DONE
			// Next Player draws 4 -DONE
			// Next player skips turn -DONE

			this.playerList[FindNextPlayer()].setCanPlay(false);
			this.playerDirection = false;
			((WildDrawCard) card).setColor(color);
			return this.playerList[FindNextPlayer()].DrawCard(CardsToDrawPerWildDrawCard, this.deck);
		}

		return null; // NumberCard
	}

	private int FindNextPlayer()
	{
		if (this.playerDirection == true)
		{
			if (this.currentPlayer == (this.nbOfPlayers - 1))
			{
				return 0;
			}
			else
			{
				return this.currentPlayer + 1;
			}
		}
		else
		// if direction is reversed
		{
			if (this.currentPlayer == 0)
			{
				return this.nbOfPlayers - 1;
			}
			else
			{
				return this.currentPlayer - 1;
			}
		}
	}

	private void AssignNextPlayer()
	{
		if (this.playerList[this.currentPlayer].VictoryCheck() == true)
		{
			this.visuals.Victory(this.currentPlayer);
		}
		this.currentPlayer = FindNextPlayer();

		if (this.playerList[this.currentPlayer].getCanPlay() == false)
		{
			this.playerList[this.currentPlayer].setCanPlay(true);
			this.currentPlayer = FindNextPlayer();
		}
		
		Element gameRunning = new Element("True");
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
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Renvoie le numéro du joueur courant
	 * @return L'integer du joueur courant
	 */
	public int getCurrentPlayer()
	{
		return this.currentPlayer;
	}

}
