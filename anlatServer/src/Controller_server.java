

import java.io.BufferedReader;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class Controller_server extends Thread {
	private int SERVERPORT = 8080;
	private ServerSocket sSocket;
	private Socket client = null;
	private boolean isRun = false;
	private OnMessageReceived listenerMessage;
	private PrintWriter mOut;

	
	public static void main(String[] args) {
		View_server frame = new View_server(); //arayuz elementleri...
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // *** center the app *** 
		frame.pack();
		frame.setVisible(true);

	}
	
	/**
	 * Constructor of the class also this is the section read() of the TCP communication.
	 * 
	 * @param messageListener listens for the messages
	 */
	public Controller_server(OnMessageReceived messageListener)
	{
		this.listenerMessage = messageListener;
	}
	
	/**
	 * Method to send the messages from server to client also write() section of the TCP communication
	 * 
	 * @param message the message sent by the server
	 */
	public void sendMessage(String message)
	{
		try
		{
			if (mOut != null && !mOut.checkError())
			{
				System.out.println(message);
				// Here you can connect with database or else you can do what you want with static message
				
				mOut.println(message);
				mOut.flush();
			}
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * 
	 * This section equals to listen() and accept() parts of the TCP communication.
	 * */
	@Override
	public void run()
	{
		super.run();
		isRun = true;
		try
		{
			System.out.println("PA: Connecting...");

			// create a server socket. A server socket waits for requests to
			// come in over the network.
			sSocket = new ServerSocket(SERVERPORT);

			// create client socket... the method accept() listens for a
			// connection to be made to this socket and accepts it.
			try
			{
				client = sSocket.accept();
				System.out.println("S: Receiving...");
				// server in yazdigi mesaj mOut bufferindan client e gonderilir.
				mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
				System.out.println("PA: Sent");
				System.out.println("PA: Connecting Done.");
				// clientten gelen mesaj burada server "in" bufferina gelir.
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

				sendMessage("Server connected with Android Client now you can chat with socket server.");

				// in this while we wait to receive messages from client (it's an infinite loop)
				// this while it's like a listener for messages
				while (isRun)
				{
					String message = in.readLine();
					//Zemberek iþlemi...
					Zemberek z = new Zemberek(new TurkiyeTurkcesi());
					if(z.kelimeDenetle(message))
						System.out.print("Dogrudur");
					else
						System.out.print("Yanlistir");
					//eo Zemberek..
					if (message != null && listenerMessage != null)
					{
						// call the method messageReceived from ServerBoard class
						listenerMessage.messageReceived(message);
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("PA: Error: "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				client.close();
				System.out.println("PA: Done.");
			}
		}
		catch (Exception e)
		{
			System.out.println("PA: Error");
			e.printStackTrace();
		}

	}

	
	public interface OnMessageReceived
	{
		public void messageReceived(String message);
	}

}
