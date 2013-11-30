package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;



public class Client
{

	public static void main(String[] zero)
	{

		Socket socket;
		
		try
		{
			socket = new Socket("localhost",51000);
			
			socket.close();
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
	}
}