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
		
		//demander un port
		int port = 51000;

		try
		{
			serverSocket = new ServerSocket(port);
			Thread t = new Thread(new NewClient(serverSocket));
			t.start();
			System.out.println("Mes employeurs sont prêts !");
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
	}
}

class NewClient implements Runnable
{

	private ServerSocket socketserver;
	private Socket socket;
	private int nbrclient = 1;
	InputStream inStream;
	OutputStream outStream;
	
	private String login;
	private String pass;

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
				boolean correctPass = false;
				
				while (correctPass == false)
				{
					
					Element loginNode = new Element("login");
					loginNode.setText("Entrez votre nom d'utilisateur.");
					try
					{
						Document doc = new Document(loginNode);
						XMLOutputter xmlOut = new XMLOutputter();
						xmlOut.output(doc, outStream);
						outStream.flush();
						//lire Login
						Element passNode = new Element("pass");
						passNode.setText("Entrez votre mot de passe.");
						doc = new Document(passNode);
						xmlOut.output(doc, outStream);
						outStream.flush();
					}
					catch (Exception e) {}
					
					//lire Pass

					if (ValidLogin(login, pass))
					{
						//Envoyer un msg au client pour confirmer la connection
						System.out.println(login + " vient de se connecter ");
						
						correctPass = true;
					}
					else
					{
						//Envoyez une erreur au client
					}
				}
				
				nbrclient++;
				socket.close();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean ValidLogin(String login, String pass)
	{
		
		//Vérifiez dans un fichier txt ?
		//On assume que le login est toujours bon ?
		//À vérifier
		return true;
	}
}
