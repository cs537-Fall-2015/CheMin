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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Run full process", "Manual selection"}));
		frmTestClient.getContentPane().add(comboBox, BorderLayout.NORTH);
		
		JButton btnSendCommandsTo = new JButton("Send commands to chemin");
		frmTestClient.getContentPane().add(btnSendCommandsTo, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frmTestClient.getContentPane().add(panel, BorderLayout.WEST);
		
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
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_2))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_3))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_4))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_5))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_6))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_7))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_8))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_9))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxNewCheckBox_1)))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
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
		panel.setLayout(gl_panel);
	}
}
