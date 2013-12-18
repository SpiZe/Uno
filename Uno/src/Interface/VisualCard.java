/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 */

package Interface;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import Cards.Card;
import Cards.Card.CardColor;
import Cards.DrawCard;
import Cards.NumberCard;
import Cards.ReverseCard;
import Cards.SkipCard;
import Cards.WildCard;
import Cards.WildDrawCard;

public class VisualCard extends ImageView
{
	private static final int CARDHEIGHT = 100;
	private static final int CARDWIDTH = 75;
	private static final String WILDRED = "./res/WILDRED.png";
	private static final String WILDBLUE = "./res/WILDBLUE.png";
	private static final String WILDGREEN = "./res/WILDGREEN.png";
	private static final String WILDYELLOW = "./res/WILDYELLOW.png";
	private static final String DEFAULT = "./res/default.png";
	private static final String SKIPBLUE = "./res/SKIPBLUE.png";
	private static final String SKIPRED = "./res/SKIPRED.png";
	private static final String SKIPYELLOW = "./res/SKIPYELLOW.png";
	private static final String SKIPGREEN = "./res/SKIPGREEN.png";
	private static final String WILDDRAWCARD = "./res/WILDDRAW.png";
	private static final String WILDCARD = "./res/WILD.png";
	private static final String DRAWBLUE = "./res/DRAWBLUE.png";
	private static final String DRAWRED = "./res/DRAWRED.png";
	private static final String DRAWGREEN = "./res/DRAWGREEN.png";
	private static final String DRAWYELLOW = "./res/DRAWYELLOW.png";
	private static final String REVERSEBLUE = "./res/REVERSEBLUE.png";
	private static final String REVERSERED = "./res/REVERSERED.png";
	private static final String REVERSEGREEN = "./res/REVERSEGREEN.png";
	private static final String REVERSEYELLOW = "./res/REVERSEYELLOW.png";
	private static final String ZEROBLUE = "./res/ZEROBLUE.png";
	private static final String ZERORED = "./res/ZERORED.png";
	private static final String ZEROGREEN = "./res/ZEROGREEN.png";
	private static final String ZEROYELLOW = "./res/ZEROYELLOW.png";
	private static final String ONEBLUE = "./res/ONEBLUE.png";
	private static final String ONERED = "./res/ONERED.png";
	private static final String ONEGREEN = "./res/ONEGREEN.png";
	private static final String ONEYELLOW = "./res/ONEYELLOW.png";
	private static final String TWOBLUE = "./res/TWOBLUE.png";
	private static final String TWORED = "./res/TWORED.png";
	private static final String TWOGREEN = "./res/TWOGREEN.png";
	private static final String TWOYELLOW = "./res/TWOYELLOW.png";
	private static final String THREEBLUE = "./res/THREEBLUE.png";
	private static final String THREERED = "./res/THREERED.png";
	private static final String THREEGREEN = "./res/THREEGREEN.png";
	private static final String THREEYELLOW = "./res/THREEYELLOW.png";
	private static final String FOURBLUE = "./res/FOURBLUE.png";
	private static final String FOURRED = "./res/FOURRED.png";
	private static final String FOURGREEN = "./res/FOURGREEN.png";
	private static final String FOURYELLOW = "./res/FOURYELLOW.png";
	private static final String FIVEBLUE = "./res/FIVEBLUE.png";
	private static final String FIVERED = "./res/FIVERED.png";
	private static final String FIVEGREEN = "./res/FIVEGREEN.png";
	private static final String FIVEYELLOW = "./res/FIVEYELLOW.png";
	private static final String SIXBLUE = "./res/SIXBLUE.png";
	private static final String SIXRED = "./res/SIXRED.png";
	private static final String SIXGREEN = "./res/SIXGREEN.png";
	private static final String SIXYELLOW = "./res/SIXYELLOW.png";
	private static final String SEVENBLUE = "./res/SEVENBLUE.png";
	private static final String SEVENRED = "./res/SEVENRED.png";
	private static final String SEVENGREEN = "./res/SEVENGREEN.png";
	private static final String SEVENYELLOW = "./res/SEVENYELLOW.png";
	private static final String HEIGHTBLUE = "./res/HEIGHTBLUE.png";
	private static final String HEIGHTRED = "./res/HEIGHTRED.png";
	private static final String HEIGHTGREEN = "./res/HEIGHTGREEN.png";
	private static final String HEIGHTYELLOW = "./res/HEIGHTYELLOW.png";
	private static final String NINEBLUE = "./res/NINEBLUE.png";
	private static final String NINERED = "./res/NINERED.png";
	private static final String NINEGREEN = "./res/NINEGREEN.png";
	private static final String NINEYELLOW = "./res/NINEYELLOW.png";

	private String cardType;
	private Card card;
	private boolean up = false;
	private GridPane currentDeck;

	public VisualCard(GridPane deck)
	{
		this.currentDeck = deck;
		this.cardType = DEFAULT;
		this.setUserData("default");
		getString();
		setEvent();
	}
	
	public VisualCard(GridPane deck, CardColor color)
	{
		this.currentDeck = deck;
		this.cardType = "WILD" + caseCardColor(color);
		this.setUserData("default");
		getString();
		setEvent();
	}
	
	private String caseCardColor(CardColor color)
	{
		switch(color)
		{
		case BLUE :
			return "BLUE"; 
		case RED :
			return "RED";
		case GREEN : 
			return "GREEN";
		default :
			return "YELLOW";
		}
	}

	public VisualCard(Card card, GridPane deck)
	{
		this.card = card;
		this.currentDeck = deck;
		if (card instanceof WildDrawCard)
		{
			this.cardType = "WILDDRAWNCARD";
		}
		else if (card instanceof SkipCard)
		{
			cardType = "SKIP" + caseColor();
		}
		else if (card instanceof DrawCard)
		{
			cardType = "DRAW" + caseColor();
		}
		else if (card instanceof ReverseCard)
		{
			cardType = "REVERSE" + caseColor();
		}
		else if (card instanceof WildCard)
		{
			this.cardType = "WILDCARD";
		}
		else if (card instanceof NumberCard)
		{
			this.cardType = caseNumber() + caseColor();
		}
		getString();
		setEvent();

	}

	private void getString()
	{
		switch (cardType)
		{
		case "DRAWRED":
			setCard(DRAWRED);
			break;
		case "DRAWBLUE":
			setCard(DRAWBLUE);
			break;
		case "DRAWGREEN":
			setCard(DRAWGREEN);
			break;
		case "DRAWYELLOW":
			setCard(DRAWYELLOW);
			break;
		case "SKIPRED":
			setCard(SKIPRED);
			break;
		case "SKIPBLUE":
			setCard(SKIPBLUE);
			break;
		case "SKIPGREEN":
			setCard(SKIPGREEN);
			break;
		case "SKIPYELLOW":
			setCard(SKIPYELLOW);
			break;
		case "WILDCARD":
			setCard(WILDCARD);
			break;
		case "WILDDRAWNCARD":
			setCard(WILDDRAWCARD);
			break;
		case "REVERSEBLUE":
			setCard(REVERSEBLUE);
			break;
		case "REVERSERED":
			setCard(REVERSERED);
			break;
		case "REVERSEYELLOW":
			setCard(REVERSEYELLOW);
			break;
		case "REVERSEGREEN":
			setCard(REVERSEGREEN);
			break;
		case "ZEROBLUE":
			setCard(ZEROBLUE);
			break;
		case "ZERORED":
			setCard(ZERORED);
			break;
		case "ZEROGREEN":
			setCard(ZEROGREEN);
			break;
		case "ZEROYELLOW":
			setCard(ZEROYELLOW);
			break;
		case "ONEBLUE":
			setCard(ONEBLUE);
			break;
		case "ONERED":
			setCard(ONERED);
			break;
		case "ONEGREEN":
			setCard(ONEGREEN);
			break;
		case "ONEYELLOW":
			setCard(ONEYELLOW);
			break;
		case "TWOBLUE":
			setCard(TWOBLUE);
			break;
		case "TWORED":
			setCard(TWORED);
			break;
		case "TWOGREEN":
			setCard(TWOGREEN);
			break;
		case "TWOYELLOW":
			setCard(TWOYELLOW);
			break;
		case "THREEBLUE":
			setCard(THREEBLUE);
			break;
		case "THREERED":
			setCard(THREERED);
			break;
		case "THREEGREEN":
			setCard(THREEGREEN);
			break;
		case "THREEYELLOW":
			setCard(THREEYELLOW);
			break;
		case "FOURBLUE":
			setCard(FOURBLUE);
			break;
		case "FOURRED":
			setCard(FOURRED);
			break;
		case "FOURGREEN":
			setCard(FOURGREEN);
			break;
		case "FOURYELLOW":
			setCard(FOURYELLOW);
			break;
		case "FIVEBLUE":
			setCard(FIVEBLUE);
			break;
		case "FIVERED":
			setCard(FIVERED);
			break;
		case "FIVEGREEN":
			setCard(FIVEGREEN);
			break;
		case "FIVEYELLOW":
			setCard(FIVEYELLOW);
			break;
		case "SIXBLUE":
			setCard(SIXBLUE);
			break;
		case "SIXRED":
			setCard(SIXRED);
			break;
		case "SIXGREEM":
			setCard(SIXGREEN);
			break;
		case "SIXYELLOW":
			setCard(SIXYELLOW);
			break;
		case "SEVENBLUE":
			setCard(SEVENBLUE);
			break;
		case "SEVENRED":
			setCard(SEVENRED);
			break;
		case "SEVENGREEN":
			setCard(SEVENGREEN);
			break;
		case "SEVENYELLOW":
			setCard(SEVENYELLOW);
			break;
		case "HEIGHTBLUE":
			setCard(HEIGHTBLUE);
			break;
		case "HEIGHTRED":
			setCard(HEIGHTRED);
			break;
		case "HEIGHTGREEN":
			setCard(HEIGHTGREEN);
			break;
		case "HEIGHTYELLOW":
			setCard(HEIGHTYELLOW);
			break;
		case "NINEBLUE":
			setCard(NINEBLUE);
			break;
		case "NINERED":
			setCard(NINERED);
			break;
		case "NINEGREEN":
			setCard(NINEGREEN);
			break;
		case "NINEYELLOW":
			setCard(NINEYELLOW);
			break;
		case "WILDRED" :
			setCard(WILDRED);
		case "WILDBLUE" :
			setCard(WILDBLUE);
		case "WILDGREEN" :
			setCard(WILDGREEN);
		case "WILDYELLOW" :
			setCard(WILDYELLOW);
		default:
			setCard(DEFAULT);
			break;
		}
	}

	private String caseColor()
	{
		switch (this.card.getColor())
		{
		case BLUE:
			return "BLUE";

		case RED:
			return "RED";

		case GREEN:
			return "GREEN";

		default:
			return "YELLOW";

		}
	}

	private String caseNumber()
	{
		switch (((NumberCard) this.card).getNumber())
		{
		case 0:
			return "ZERO";

		case 1:
			return "ONE";

		case 2:
			return "TWO";

		case 3:
			return "THREE";

		case 4:
			return "FOUR";

		case 5:
			return "FIVE";

		case 6:
			return "SIX";

		case 7:
			return "SEVEN";

		case 8:
			return "HEIGHT";
		default:
			return "NINE";
		}
	}

	private void setCard(String cardString)
	{
		try
		{
			setImage(new Image(getClass().getResourceAsStream(cardString)));
			this.setFitHeight(CARDHEIGHT);
			this.setFitWidth(CARDWIDTH);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	private void setEvent()
	{
		this.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				VisualCard card = (VisualCard) event.getSource();
				if (card.getUserData() != "default")
				{
					boolean cardSelected = false;
					for (Node cardDeck : currentDeck.getChildren())
					{
						if (cardDeck instanceof VisualCard)
						{
							VisualCard currentCard = (VisualCard) cardDeck;
							if (currentCard.getUp())
							{
								cardSelected = true;
							}
						}
					}
					if (!cardSelected)
					{
						if (card.up == false)
						{
							card.setTranslateY(card.getTranslateY() - 5);
							card.up = true;
						}
					}
					else if (card.up == true)
					{
						card.up = false;
						card.setTranslateY(card.getTranslateY() + 5);
					}
				}
			}

		});
	}

	public Card getCard()
	{
		return this.card;
	}

	public boolean getUp()
	{
		return this.up;
	}
	
	public void Delete(GridPane deck)
	{
		currentDeck.getChildren().remove(this);
		
		/*for(Node node : currentDeck.getChildren())
		{
			if(node instanceof VisualCard)
			{
				VisualCard card = (VisualCard) node;
				if(card == this)
				{
					try
					{
						this.setVisible(false);
					}
					catch(Exception e)
					{
						System.out.println("Votre carte à été supprimé.");
					}
				}
			}
		}
		*/
	}
}
