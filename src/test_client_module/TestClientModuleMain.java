package test_client_module;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

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
		frmTestClient.setBounds(100, 100, 450, 300);
		frmTestClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Run full process", "Manual selection"}));
		frmTestClient.getContentPane().add(comboBox, BorderLayout.NORTH);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("hello\r\nje");
		chckbxNewCheckBox.setToolTipText("");
		frmTestClient.getContentPane().add(chckbxNewCheckBox, BorderLayout.WEST);
		
		JButton btnSendCommandsTo = new JButton("Send commands to chemin");
		frmTestClient.getContentPane().add(btnSendCommandsTo, BorderLayout.CENTER);
	}

}
