package test_client_module;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.security.auth.callback.ChoiceCallback;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.awt.event.ActionEvent;

import json.Constants;

public class TestClientModuleMain {

	private JFrame frmTestClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestClientModuleMain window = new TestClientModuleMain();
					window.frmTestClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestClientModuleMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestClient = new JFrame();
		frmTestClient.setTitle("Test client");
		frmTestClient.setBounds(100, 100, 450, 352);
		frmTestClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComboBox settings_selection = new JComboBox();
		settings_selection.setModel(new DefaultComboBoxModel(new String[] {"Run full process", "Manual selection"}));
		frmTestClient.getContentPane().add(settings_selection, BorderLayout.NORTH);
		
		JButton send_button = new JButton("Send commands to chemin");
		
		frmTestClient.getContentPane().add(send_button, BorderLayout.CENTER);
		
		JPanel commands_selection = new JPanel();
		frmTestClient.getContentPane().add(commands_selection, BorderLayout.WEST);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_6 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_7 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_8 = new JCheckBox("New check box");
		
		JCheckBox chckbxNewCheckBox_9 = new JCheckBox("New check box");
		GroupLayout gl_commands_selection = new GroupLayout(commands_selection);
		gl_commands_selection.setHorizontalGroup(
			gl_commands_selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_commands_selection.createSequentialGroup()
					.addGroup(gl_commands_selection.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_2))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_3))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_4))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_5))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_6))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_7))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_8))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_9))
						.addGroup(gl_commands_selection.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_1)))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		gl_commands_selection.setVerticalGroup(
			gl_commands_selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_commands_selection.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxNewCheckBox_1)
					.addGap(1)
					.addComponent(chckbxNewCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_6)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_7)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_8)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_9)
					.addContainerGap(32, Short.MAX_VALUE))
		);
		commands_selection.setLayout(gl_commands_selection);
		
		
		send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//when send button is pressed, create txt command files and send command file path to chemin
				///// .txt command file creation
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(Constants.ROOT_PATH+"command_file.txt", "UTF-8");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(settings_selection.getSelectedItem()=="Run full process")
				{
					//fill .txt file
					writer.println("launch_Chemin_Process");
					writer.close();
				} else if(settings_selection.getSelectedItem()=="Manual selection")
				{
					frmTestClient.setTitle("sis");
					chckbxNewCheckBox_1.setEnabled(false);
					//fill .txt file
				}
				
	/*			Socket socket=null;
				try {
					socket = new Socket("localhost",9008);
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
				}*/
			}
		});
	}
}
