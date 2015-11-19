package chemin_module;

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

public class GuiCheminControlPannel {

	private JFrame frmCheminControlPanel;
	private static GuiCheminControlPannel window=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GuiCheminControlPannel();
					window.frmCheminControlPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public GuiCheminControlPannel() {
		initialize();
	}

	private JTextArea txtrCheminControlPanel=null;
	
	private ActionListener taskPerformer= null;
	static private String message= "hello";
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheminControlPanel = new JFrame();
		frmCheminControlPanel.setTitle("Chemin control panel");
		frmCheminControlPanel.setBounds(100, 100, 470, 433);
		frmCheminControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtrCheminControlPanel = new JTextArea();
		txtrCheminControlPanel.setEditable(false);
		
		txtrCheminControlPanel.append(message);
		txtrCheminControlPanel.setFont(new Font("Monospaced", Font.PLAIN, 13));
		frmCheminControlPanel.getContentPane().add(txtrCheminControlPanel, BorderLayout.CENTER);
		
		JButton btnBut = new JButton("press");
		btnBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrCheminControlPanel.append(message);
				txtrCheminControlPanel.append("\n");
				System.out.println("reach2");
			}
		});
		
		taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(message);
			}
	      };
	      
		frmCheminControlPanel.getContentPane().add(btnBut, BorderLayout.SOUTH);
	}

	
	public void write(String mess) {
		message = mess;
		taskPerformer.actionPerformed(null);
	}
}
