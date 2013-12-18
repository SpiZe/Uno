package Cards;

public class NumberCard extends Card
{
	private int number;
	private CardColor color;

	/**
	 * Cr�e une carte avec un num�ro et une couleur
	 * @param number Le num�ro
	 * @param color La couleur
	 */
	public NumberCard(int number, Card.CardColor color)
	{
		this.number = number;
		this.color = color;
	}

	public CardColor getColor()
	{
		return color;
	}

	/**
	 * Le num�ro de la carte
	 * @return Le num�ro de la carte
	 */
	public int getNumber()
	{
		return number;
	}

	public String toString()
	{
		switch (color)
		{
		case RED:
			return Integer.toString(number) + "Red";
		case BLUE:
			return Integer.toString(number) + "Blue";
		case GREEN:
			return Integer.toString(number) + "Green";
		default:
			return Integer.toString(number) + "Yellow";
		}
	}

	@Override
	public boolean CanBePlayed(Card previous)
	{
		if (this.color == previous.getColor())
		{
			return true;
		}
		if (previous instanceof NumberCard && this.number == ((NumberCard) previous).getNumber())
		{
			return true;
		}
		return false;
	}

}
