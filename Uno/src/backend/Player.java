package backend;

import Cards.Card;
import Deck.Deck;
import Deck.PlayerDeck;

/**
 * @author Samuel Gagnon
 * @author Keven Langlois
 * Définit un joueur et ses méthodes liés. Possède une main.
 */
public class Player
{
	private PlayerDeck hand;
	private boolean canPlay;

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Instancie un joueur, sa main et ses attributs. Le premier joueur instancié est
	 * humain, les autres non.
	 * @see AI
	 * @see PlayerDeck
	 */
	public Player()
	{
		this.hand = new PlayerDeck();
		this.canPlay = true;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Vérifie si le joueur a gagné la partie
	 * @return true si le joueur est gagnant, false sinon
	 * @see PlayerDeck
	 */
	public boolean VictoryCheck()
	{
		if (this.getHand().isEmpty())
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
	 * Pige une seule carte du jeu et la place dans la main du joueur.
	 * @param deck Le jeu de cartes
	 * @return La carte pigé
	 * @see Card
	 * @see PlayerDeck
	 * @see Deck
	 */
	public Card DrawCard(Deck deck)
	{
		Card toRemove = deck.lastElement();
		this.hand.add(deck.pop());
		return toRemove;
	}

	/**
	 * @author Keven Langlois
	 * @author Samuel Gagnon
	 * Pige plusieurs cartes dans le jeu
	 * @param nbOfCardsToDraw le nombre de cartes à piger
	 * @param deck Le jeu de cartes
	 * @return Un array de cartes correspondant aux cartes pigés
	 * @see Card
	 * @see Deck
	 * @see PlayerDeck
	 */
	public Card[] DrawCard(int nbOfCardsToDraw, Deck deck)
	{
		Card[] cardsToDraw = new Card[nbOfCardsToDraw];
		for (int i = 0; i < nbOfCardsToDraw; i++)
		{
			cardsToDraw[i] = deck.lastElement();
			this.hand.add(deck.pop());
		}
		return cardsToDraw;
	}

	/**
	 * Retourne la main
	 * @return La main du joueur
	 * @see PlayerDeck
	 */
	public PlayerDeck getHand()
	{
		return hand;
	}

	/**
	 * Retourne si le joueur peut jouer
	 * @return true si le joueur peut jouer, false sinon
	 */
	public boolean getCanPlay()
	{
		return canPlay;
	}


	/**
	 * Change si le joueur peut jouer ou non
	 * @param canPlay true si le joueur peut jouer, false sinon
	 */
	public void setCanPlay(boolean canPlay)
	{
		this.canPlay = canPlay;
	}

}
