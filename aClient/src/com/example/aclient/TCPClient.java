//Client side TCP sections are ; socket,connect,write,read and close.
package com.example.aclient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import android.util.Log;
public class TCPClient {

	/**
	 *  
	 * Handle the TCPClient with Socket Server. 
	 * */
	
	
    /**
	 * Specify the Server Ip Address here. Whereas our Socket Server is started.
	 * */
	public static final String SERVERIP = "192.168.2.151"; // your computer IP address
    public static final int SERVERPORT = 8080;
    private OnMessageReceived listenerMessage = null;
    private boolean isRun = false;
    private String serverMessage;
    private PrintWriter out = null;
    private BufferedReader in = null;
    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(final OnMessageReceived listener) 
    {
        listenerMessage = listener;
    }
    
    /**
     * Sends the message entered by client to the server  also write() section of the TCP-Client side .
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
        	System.out.println("message: "+ message);
            out.println(message);
            out.flush();
        }
    }
    
    public void stopClient(){
        isRun = false;
    }
    
    public void run() {
    	 
        isRun = true;
 
        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
 
            Log.e("TCP SI Client", "SI: Connecting...");
 
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
          
                //send the message to the server.write() section of the TCP Client side.
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
 
                Log.e("TCP SI Client", "SI: Sent.");
 
                Log.e("TCP SI Client", "SI: Done.");
                
                //receive the message which the server sends back
                //TCP read() section happens here.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
                //in this while the client listens for the messages sent by the server
                while (isRun) {
                	serverMessage = in.readLine();
 
                    if (serverMessage != null && listenerMessage != null) {
                        //call the method messageReceived from MyActivity class
                        listenerMessage.messageReceived(serverMessage);
                        Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
                    }
                    serverMessage = null;
                }
            }
            catch (Exception e) 
            {
                Log.e("TCP SI Error", "SI: Error", e);
                e.printStackTrace();
            }
            finally 
            {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
            	//TCP close() section happens here.
                socket.close();
            }
 
        } catch (Exception e) {
 
            Log.e("TCP SI Error", "SI: Error", e);
 
        }
        
        
 
    }
    
  //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
