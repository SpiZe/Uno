package ServerSide;

import java.io.IOException;
import java.net.*;

public class Server
{

	public static void main(String[] zero)
	{

		ServerSocket socket;
		try
		{
			socket = new ServerSocket(2009);
			Thread t = new Thread(new Accepter_clients(socket));
			t.start();
			System.out.println("Mes employeurs sont prêts !");

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
	}
}

class Accepter_clients implements Runnable
{

	private ServerSocket socketserver;
	private Socket socket;
	private int nbrclient = 1;

	public Accepter_clients(ServerSocket s)
	{
		socketserver = s;
	}

	public void run()
	{

		try
		{
			while (true)
			{
				socket = socketserver.accept(); // Un client se connecte on
												// l'accepte
				System.out.println("Le client numéro " + nbrclient + " est connecté !");
				nbrclient++;
				socket.close();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}