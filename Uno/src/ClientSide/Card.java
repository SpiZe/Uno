package ClientSide;

public class Card
{
	private CardNumber number;
	private CardColor color;
	private CardType type;
	
	public enum CardNumber
	{
		ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE;
	}
	
	public enum CardType
	{
		NUMBER,SKIP,DRAW,REVERSE,WILD,WILDDRAW;
	}
	
	public enum CardColor
	{
		RED,GREEN,BLUE,YELLOW;
	}
	
	public Card(CardNumber number, CardColor color, CardType type)
	{
		this.color = color;
		this.number = number;
		this.type = type;
	}
	
	public Card(CardColor color, CardType type)
	{
		this.color = color;
		this.type = type;
	}
	
	public Card(CardType type)
	{
		this.type = type;
	}

	public CardColor getColor()
	{
		return color;
	}

	public CardNumber getNumber()
	{
		return number;
	}
}
