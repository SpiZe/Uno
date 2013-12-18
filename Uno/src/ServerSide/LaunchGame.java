package ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import Deck.Deck;

public class LaunchGame
{
	private Socket player1;
	private Socket player2;
	private BufferedReader in;
	private PrintWriter out;
	private Deck deck;
	private boolean gameIsRunning = false;
	private int playerTurn = 1;

	public LaunchGame(Socket socket1, Socket socket2)
	{
		this.player1 = socket1;
		this.player2 = socket2;
	}

	public void Run()
	{
		LaunchSignal();
		PlayerInfo();

		Deck deck = new Deck();
		deck.Shuffle();

		SendDeck();

		while (gameIsRunning == true)
		{
			if (playerTurn == 1)
			{
				SendCardPlayed(player1, player2);
				gameIsRunning = isGameRunning(player1);
			}
			else
			{
				SendCardPlayed(player1, player2);
				gameIsRunning = isGameRunning(player2);
			}
			
			
		}

	}
	
	private void PlayerInfo()
	{
		Element playerOne = new Element("PlayerNumber");
		playerOne.setText("1");
		Document docP1 = new Document(playerOne);
		
		Element playerTwo =  new Element("PlayerNumber");
		playerTwo.setText("2");
		Document docP2 = new Document(playerTwo);
		
		XMLOutputter xmlOut = new XMLOutputter();
		try
		{
			out = new PrintWriter(player1.getOutputStream());
			out.write(xmlOut.outputString(docP1));
			out.flush();
			
			out = new PrintWriter(player2.getOutputStream());
			out.write(xmlOut.outputString(docP2));
			out.flush();
		}
		catch (Exception e)
		{
		}
	}
	
	private boolean isGameRunning(Socket socket)
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String xml = in.readLine();
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(new StringReader(xml));
			
			if (doc.getRootElement().getName() == "True")
			{
				return true;
			}
		}
		catch (Exception e){}
		
		return false;
		
	}

	private void SendCardPlayed(Socket from, Socket to)
	{
		try
		{
			//Réception
			in = new BufferedReader(new InputStreamReader(from.getInputStream()));
			String xml = in.readLine();
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(new StringReader(xml));
			
			//Émission
			XMLOutputter xmlOut = new XMLOutputter();
			out = new PrintWriter(to.getOutputStream());
			out.write(xmlOut.outputString(doc));
			out.flush();
			
		}
		catch (Exception e)
		{
		}
	}

	private void LaunchSignal()
	{
		Element Start = new Element("Start");
		Start.setText("Start");
		Document doc = new Document(Start);
		XMLOutputter xmlOut = new XMLOutputter();
		try
		{
			out = new PrintWriter(player1.getOutputStream());
			out.write(xmlOut.outputString(doc));
			out.flush();
			out = new PrintWriter(player2.getOutputStream());
			out.write(xmlOut.outputString(doc));
			out.flush();

			gameIsRunning = true;
		}
		catch (Exception e)
		{
		}
	}

	private void SendDeck()
	{
		try
		{
			ObjectOutputStream outStream = new ObjectOutputStream(player1.getOutputStream());
			outStream.writeObject(deck);
			out.flush();

			outStream = new ObjectOutputStream(player2.getOutputStream());
			outStream.writeObject(deck);
			out.flush();
		}
		catch (IOException e)
		{
		}
	}
}
