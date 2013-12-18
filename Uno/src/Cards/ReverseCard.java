package Cards;

public class ReverseCard extends Card
{
	private CardColor color;
	
	/**
	 * Crée une carte avec la couleur
	 * @param color la couleur
	 */
	public ReverseCard(CardColor color)
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
			return "ReverseRed";
		case BLUE:
			return "ReverseBlue";
		case GREEN:
			return "ReverseGreen";
		default:
			return "ReverseYellow";
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
