package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import Cards.Card;
import Cards.DrawCard;
import Cards.NumberCard;
import Deck.Deck;
import Deck.PlayedDeck;
import Deck.PlayerDeck;
import Interface.GameInterface;
import backend.Game;
import backend.Player;

public class GameTest
{
	private Card card = mock(Card.class);
	private Player player = mock(Player.class);
	private Deck deck = mock(Deck.class);
	private PlayerDeck hand = mock(PlayerDeck.class);
	private PlayedDeck playedDeck = mock(PlayedDeck.class);
	private GameInterface visuals = mock(GameInterface.class);
	private Player[] playerList = {player,player};
	private Game game;
	
	@Test
	public void GameStart()
	{
		game = new Game(deck, playerList, playedDeck, 2, visuals);
		Card[] cardArray = {card,card,card,card,card,card,card};
		Card[] cardResult = new Card[7];
		when(player.DrawCard(7, deck)).thenReturn(cardArray);
		cardResult = game.GameStart();
		assertTrue(cardArray == cardResult);
	}
	
	@Test
	public void CardCanBePlayed()
	{
		Game game = new Game(deck, playerList, playedDeck, 2, visuals);
		when(playedDeck.CanBePlayed(card)).thenReturn(true);
		assertTrue(game.CardCanBePlayed(card));
		
		when(playedDeck.CanBePlayed(card)).thenReturn(false);
		assertFalse(game.CardCanBePlayed(card));
	}
	
	@Test
	public void CardPlayed()
	{
		game = new Game(deck, playerList, playedDeck, 2, visuals);
		card = mock(NumberCard.class);
		
		when(playedDeck.add(card)).thenReturn(true);
		when(player.getHand()).thenReturn(hand);
		when(hand.remove(card)).thenReturn(true);
		Card[] cardArray = {card,card};
		when(player.DrawCard(2, deck)).thenReturn(cardArray);
		
		assertTrue(game.CardPlayed(card) == null); //null car aucune carte ne doit etre pigée
		
		card = mock(DrawCard.class);
		assertTrue(game.CardPlayed(card) == cardArray); //2 cartes pigées
	}
	
	@Test
	public void EndTurn()
	{
		
	}
}
