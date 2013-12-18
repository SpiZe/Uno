package Cards;

import Cards.Card.CardColor;

public class WildCard extends Card
{
	private CardColor color;
	
	/**
	 * Crée une carte avec une couleur nulle, car elle n'a pas de couleur
	 */
	public WildCard()
	{
		this.color = null;
	}
	
	public WildCard(CardColor color)
	{
		this.color = color;
	}

	public CardColor getColor()
	{
		return this.color;
	}
	
	public void setColor(CardColor color)
	{
		this.color = color;
	}
	
	public String toString()
	{
		return "Wild";
	}

	@Override
	public boolean CanBePlayed(Card previous)
	{
		return true;
	}

}
