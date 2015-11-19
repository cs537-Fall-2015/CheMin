package telecom_module;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;


public class GuiTelecomControlPanel {

	private JFrame frmTelecomControlPanel;
	private static GuiTelecomControlPanel window=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GuiTelecomControlPanel();
					window.frmTelecomControlPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public GuiTelecomControlPanel() {
		initialize();
	}

	private JTextArea txtrTelecomControlPanel=null;

	private ActionListener taskPerformer= null;
	static private String message= "hello";
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTelecomControlPanel = new JFrame();
		frmTelecomControlPanel.setTitle("Telecom control panel");
		frmTelecomControlPanel.setBounds(580, 610, 470, 100);
		frmTelecomControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		txtrTelecomControlPanel = new JTextArea();
		txtrTelecomControlPanel.setEditable(false);

		txtrTelecomControlPanel.append(message);
		txtrTelecomControlPanel.setFont(new Font("Monospaced", Font.PLAIN, 13));
		frmTelecomControlPanel.getContentPane().add(txtrTelecomControlPanel, BorderLayout.CENTER);

		JButton btnBut = new JButton("press");
		btnBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrTelecomControlPanel.append(message);
				txtrTelecomControlPanel.append("\n");
			}
		});

		taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(message);
				main(null);
				/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
				txtrTelecomControlPanel.append(message);
				txtrTelecomControlPanel.append("\n");
				/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
			}
		};

		frmTelecomControlPanel.getContentPane().add(btnBut, BorderLayout.SOUTH);
	}


	public void write(String mess) {
		message = mess;
		taskPerformer.actionPerformed(null);
	}
}

