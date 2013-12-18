package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Server
{

	public static void main(String[] zero) throws ParserConfigurationException
	{

		ServerSocket serverSocket;

		// demander un port
		int port = 51004;

		try
		{
			serverSocket = new ServerSocket(port);
			Thread t = new Thread(new NewClient(serverSocket));
			t.start();
			System.out.println("Mes employeurs sont prêts !");
		}
		catch (IOException e)
		{
		}
	}
}

class NewClient implements Runnable
{

	private ServerSocket socketserver;
	private Socket socket;
	private Socket socketTemp;
	private int nbrclient = 0;
	InputStream inStream;
	PrintWriter out;

	public NewClient(ServerSocket serversocket)
	{
		this.socketserver = serversocket;
	}

	public void run()
	{

		try
		{
			while (true)
			{

				socket = socketserver.accept();
				nbrclient++;
				
				out = new PrintWriter(socket.getOutputStream());
				
				if (nbrclient > 2)
				{
					ClientDenied();
					socket.close();
				}
				else
				{
					if (nbrclient == 2)
					{
						LaunchGame game = new LaunchGame(socketTemp, socket);
						game.Run();
					}
					else
					{
						socketTemp = socket;
					}
					//socket.close();
				}
				
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void ClientDenied()
	{
		Element error = new Element("Error");
		Document doc = new Document(error);
		XMLOutputter xmlOut = new XMLOutputter();
		try
		{
			out = new PrintWriter(socket.getOutputStream());
			out.write(xmlOut.outputString(doc));
			out.flush();
		}
		catch (IOException e)
		{
		}

	}
}
