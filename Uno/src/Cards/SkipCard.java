package Cards;

public class SkipCard extends Card
{

	private CardColor color;

	/**
	 * Crée une carte avec la couleur
	 * @param color la couleur
	 */
	public SkipCard(CardColor color)
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
			return "SkipRed";
		case BLUE:
			return "SkipBlue";
		case GREEN:
			return "SkipGreen";
		default:
			return "SkipYellow";
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
