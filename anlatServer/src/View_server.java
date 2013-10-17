
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class View_server extends JFrame {
	private JTextArea areaMessage;
	private JButton buttonSend;
	private JButton buttonStart;
	private JTextField textMessage;
	private Controller_server server;

	public View_server(){
		super("-----YFP----");
		//Birinci panel areaMessage ve buttonStart i icermektedir.
		JPanel pField = new JPanel();
		pField.setLayout(new BoxLayout(pField, BoxLayout.X_AXIS));
		//Ikinci panel textMessage ve buttonSend i icermektedir.
		JPanel pField2 = new JPanel();
		pField2.setLayout(new BoxLayout(pField2, BoxLayout.X_AXIS));		
		//Mesajlarin goruntulenecegi yer
		areaMessage = new JTextArea();
		areaMessage.setColumns(30);
		areaMessage.setRows(10);
		areaMessage.setEditable(false);	
		buttonSend = new JButton("Send");
		buttonSend.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				String messageText = textMessage.getText();
				if(messageText.toString().trim().equals("")) 
				{
					textMessage.setText("");
					textMessage.requestFocus(); 
					return;
				}				
				messageText = "Server: " + messageText;
				areaMessage.append("\n"+messageText);
				// cliente mesaj gonderirken
				server.sendMessage(messageText);
				// temizleme
				textMessage.setText("");
			}
		});		
		buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// disable the start button
				buttonStart.setEnabled(false);
				areaMessage.append("Server Started, now start Android Client");
				// creates the object OnMessageReceived asked by the DispatcherServer
				// constructor
				server = new Controller_server(new Controller_server.OnMessageReceived()
				{
					@Override
					// this method declared in the interface from DispatcherServer
					// class is implemented here
					// this method is actually a callback method, because it
					// will run every time when it will be called from
					// DispatcherServer class (at while)
					public void messageReceived(String message)
					{
						System.out.println("Msg Recieved");
						areaMessage.append("\n" + message);
					}
				});
				server.start();

			}
		});
		
		// the box where the user enters the text (EditText is called in
				// Android)
				textMessage = new JTextField();
				textMessage.setSize(200, 20);
				textMessage.setScrollOffset(1);

				// add the buttons and the text fields to the panel
				JScrollPane sp = new JScrollPane(areaMessage);
				pField.add(sp);
				pField.add(buttonStart);

				pField2.add(textMessage);
				pField2.add(buttonSend);

				getContentPane().add(pField);
				getContentPane().add(pField2);

				getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));//panellerin yatay hizalanmasi icin.

				setSize(300, 170);
				setVisible(true);

	}

}
