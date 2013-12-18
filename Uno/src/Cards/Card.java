package Cards;

/**
 * @author Keven Langlois
 * @author Samuel Gagnon
 * Définit une carte de jeu
 * @since version 1.0
 */
public abstract class Card
{
	/**
	 * Enum correspondant aux 4 couleurs possibles des cartes.
	 *
	 */
	public enum CardColor
	{
		RED,GREEN,BLUE,YELLOW;
	}
	
	/**
	 * Vérifie si la carte est jouable
	 * @param previous la carte sur le dessus du jeu
	 * @return true si elle est jouable, false sinon
	 */
	public abstract boolean CanBePlayed(Card previous);
	
	/**
	 * Renvoie la carte sous un format texte
	 * @return la carte sous format texte
	 */
	public abstract String toString();
	
	/**
	 * Renvoie la couleur de la carte
	 * @return la couleur de la carte
	 */
	public abstract CardColor getColor();
}
