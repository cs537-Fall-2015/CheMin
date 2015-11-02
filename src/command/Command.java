package command;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Command extends JFrame{
	JTextField portno;
	JTextField message;
	Command(){
		JPanel blank=new JPanel();
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		JLabel port=new JLabel("Port Number");
		p1.add(port);
		portno=new JTextField();
		p1.add(portno);
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(1,2));
		JLabel mesage=new JLabel("Command");
		p2.add(mesage);
		message=new JTextField();
		p2.add(message);
		JPanel p3=new JPanel();
		p3.setLayout(new GridLayout(2,2));
		p3.add(p1);
		p3.add(p2);
		add(blank,BorderLayout.NORTH);
		add(p3,BorderLayout.CENTER);
		JButton submit=new JButton("Send");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String port=portno.getText();
				String msg=message.getText();
				Socket socket=null;
				try {
					socket = new Socket("localhost",Integer.parseInt(port));
				} catch (IOException ex) {
				ex.printStackTrace();
				}
				ObjectOutputStream outstr=null;
				try {
					outstr = new ObjectOutputStream(socket.getOutputStream());
				} catch (IOException ey) {
				ey.printStackTrace();
				}
				try {
					outstr.writeObject(msg);
				} catch (IOException ez) {
				ez.printStackTrace();
				}
			}});
		add(submit,BorderLayout.SOUTH);
		}
		
	public static void main(String[] args) {
	JFrame frame=new Command();	
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	frame.setSize(300,200);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	return;
	}
}