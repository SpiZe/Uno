package Deck;

import java.util.Stack;

import Cards.Card;
import Cards.Card.CardColor;
import Cards.DrawCard;
import Cards.NumberCard;
import Cards.ReverseCard;
import Cards.SkipCard;
import Cards.WildCard;
import Cards.WildDrawCard;

/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 * Représente le jeu de cartes
 * @see Stack
 */
public class Deck extends Stack<Card>
{
	/**
	 * Crée un jeu de cartes, avec les 108 cartes
	 * @see Card
	 */
	public Deck()
	{
		super();

		// Cartes Bleues
		createNumberCards(CardColor.BLUE);
		// Cartes Jaunes
		createNumberCards(CardColor.YELLOW);
		// Cartes Rouges
		createNumberCards(CardColor.RED);
		// Cartes Vertes
		createNumberCards(CardColor.GREEN);
		// Cartes Draw,Reverse,Skip
		createColoredSpecialCards(CardColor.BLUE);
		createColoredSpecialCards(CardColor.YELLOW);
		createColoredSpecialCards(CardColor.RED);
		createColoredSpecialCards(CardColor.GREEN);
		// Cartes Wild et WildDraw
		createWildCards();
	}

	private void createNumberCards(CardColor color)
	{
		for (int i = 0; i < 10; i++)
		{
			if (i != 0)
			{
				// 2 de chaque numéro et de chaque couleur
				this.add(new NumberCard(i, color));
				this.add(new NumberCard(i, color));
			}
			else
			{
				// seulement une seule carte "0"
				this.add(new NumberCard(i, color));
			}
		}
	}

	private void createColoredSpecialCards(CardColor color)
	{

		// 2 de chaque couleur.
		this.add(new DrawCard(color));
		this.add(new ReverseCard(color));
		this.add(new SkipCard(color));

		this.add(new DrawCard(color));
		this.add(new ReverseCard(color));
		this.add(new SkipCard(color));
	}

	private void createWildCards()
	{
		for (int i = 0; i < 4; i++)
		{
			this.add(new WildCard());
			this.add(new WildDrawCard());
		}
	}

	/**
	 * Mélange la stack
	 * @see Card
	 * @see http://www2.hawaii.edu/~ztomasze/ta/ics211-sp05/example/Deck.java
	 */
	public void Shuffle()
	{
		// http://www2.hawaii.edu/~ztomasze/ta/ics211-sp05/example/Deck.java

		Card temp;
		int tempIndex;
		for (int o = 0; o < 3; o++)
		{
			for (int i = this.size() - 1; i >= 0; i--)
			{
				tempIndex = ((int) (i * Math.random()));
				if (tempIndex != i)
				{ // swap if different elements
					temp = this.get(i);
					this.set(i, this.get(tempIndex));
					this.set(tempIndex, temp);
				}
			}
		}
	}
}
