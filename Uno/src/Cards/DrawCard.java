package Cards;

public class DrawCard extends Card
{
	private CardColor color;
	
	/**
	 * Crée une carte avec la couleur
	 * @param color la couleur
	 */
	public DrawCard(CardColor color)
	{
		this.color = color;
	}

	public CardColor getColor()
	{
		return color;
	}
	
	public String toString()
	{
		switch (color)
		{
		case RED:
			return "DrawRed";
		case BLUE:
			return "DrawBlue";
		case GREEN:
			return "DrawGreen";
		default:
			return "DrawYellow";
		}
	}

	@Override
	public boolean CanBePlayed(Card previous)
	{
		if (this.color == previous.getColor())
		{
			return true;
		}
		return false;
	}

}
