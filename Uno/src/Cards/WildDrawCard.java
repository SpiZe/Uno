package Cards;

import Cards.Card.CardColor;


public class WildDrawCard extends Card
{
	private CardColor color;
	
	/**
	 * Crée une carte avec une couleur nulle, car elle n'a pas de couleur
	 */
	public WildDrawCard()
	{
		this.color = null;
	}
	
	public WildDrawCard(CardColor color)
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
		return "WildDraw";
	}

	@Override
	public boolean CanBePlayed(Card previous)
	{
		return true;
	}
}
