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
		
		chckbxNewCheckBox.setEnabled(false);
		chckbxNewCheckBox_1.setEnabled(false);
		chckbxNewCheckBox_2.setEnabled(false);
		chckbxNewCheckBox_3.setEnabled(false);
		chckbxNewCheckBox_4.setEnabled(false);
		chckbxNewCheckBox_5.setEnabled(false);
		chckbxNewCheckBox_6.setEnabled(false);
		chckbxNewCheckBox_7.setEnabled(false);
		chckbxNewCheckBox_8.setEnabled(false);
		chckbxNewCheckBox_9.setEnabled(false);
		
		GroupLayout gl_commands_selection = new GroupLayout(commands_selection);
		gl_commands_selection.setHorizontalGroup(
			gl_commands_selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_commands_selection.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_commands_selection.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxNewCheckBox_2)
						.addComponent(chckbxNewCheckBox_3)
						.addComponent(chckbxNewCheckBox_4)
						.addComponent(chckbxNewCheckBox_5)
						.addComponent(chckbxNewCheckBox_6)
						.addComponent(chckbxNewCheckBox_7)
						.addComponent(chckbxNewCheckBox_8)
						.addComponent(chckbxNewCheckBox_9)
						.addComponent(chckbxNewCheckBox)
						.addComponent(chckbxNewCheckBox_1))
					.addContainerGap(121, Short.MAX_VALUE))
		);
		gl_commands_selection.setVerticalGroup(
			gl_commands_selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_commands_selection.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxNewCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox_1)
					.addGap(1)
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
		
		settings_selection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(settings_selection.getSelectedItem()=="Run full process")
				{
					chckbxNewCheckBox.setEnabled(false);
					chckbxNewCheckBox_1.setEnabled(false);
					chckbxNewCheckBox_2.setEnabled(false);
					chckbxNewCheckBox_3.setEnabled(false);
					chckbxNewCheckBox_4.setEnabled(false);
					chckbxNewCheckBox_5.setEnabled(false);
					chckbxNewCheckBox_6.setEnabled(false);
					chckbxNewCheckBox_7.setEnabled(false);
					chckbxNewCheckBox_8.setEnabled(false);
					chckbxNewCheckBox_9.setEnabled(false);
					//fill .txt file
				} else if(settings_selection.getSelectedItem()=="Manual selection")
				{
					chckbxNewCheckBox.setEnabled(true);
					chckbxNewCheckBox_1.setEnabled(true);
					chckbxNewCheckBox_2.setEnabled(true);
					chckbxNewCheckBox_3.setEnabled(true);
					chckbxNewCheckBox_4.setEnabled(true);
					chckbxNewCheckBox_5.setEnabled(true);
					chckbxNewCheckBox_6.setEnabled(true);
					chckbxNewCheckBox_7.setEnabled(true);
					chckbxNewCheckBox_8.setEnabled(true);
					chckbxNewCheckBox_9.setEnabled(true);
					//fill .txt file
				}
			}
		});
		
		
		send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//when send button is pressed, create txt command files and send command file path to chemin
				///// .txt command file creation
				String command_file_name = "command_file.txt";
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(Constants.ROOT_PATH+command_file_name, "UTF-8");
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
				
				Socket socket=null;
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
					outstr.writeObject(Constants.ROOT_PATH+command_file_name);
				} catch (IOException ez) {
				ez.printStackTrace();
				}
			}
		});
	}
}
