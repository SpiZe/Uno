package Deck;

import java.util.Stack;

import Cards.Card;

/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 * Correspond au jeu de carte déjà joué.
 * @since version 1.0
 * @see Stack
 */
public class PlayedDeck extends Stack<Card>
{
	/**
	 * Crée la stack
	 */
	public PlayedDeck()
	{
		super();
	}

	/**
	 * Retourne si la carte peut etre joué
	 * @param card la carte joué
	 * @return true si la carte est jouable, false sinon
	 * @see Card
	 */
	public boolean CanBePlayed(Card card)
	{
		if (this.lastElement() == null)
		{
			return true; // EST CE QUE LA GAME COMMENCE AVEC 0 CARTES ?
		}

		return card.CanBePlayed(this.lastElement());
	}

}
