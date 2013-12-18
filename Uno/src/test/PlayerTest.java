package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import Cards.Card;
import Deck.Deck;
import backend.Player;

public class PlayerTest
{
	private Player player = new Player();
	
	@Test
	public void DrawCard()
	{
		Card card = mock(Card.class);
		Deck deck = mock(Deck.class);
		
		when(deck.lastElement()).thenReturn(card);
		assertTrue(card.equals(player.DrawCard(deck)));
	}
	
	@Test
	public void DrawCardWithNumber()
	{
		Card card = mock(Card.class);
		Deck deck = mock(Deck.class);
		Card[] cardArray = new Card[2];
		
		when(deck.lastElement()).thenReturn(card);
		cardArray = player.DrawCard(2,deck);
		assertTrue(cardArray[0] == card && cardArray[1] == card);
	}
}
