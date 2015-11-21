package power_module;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class GuiPowerControlPanel {

		private JFrame frmPowerControlPanel;
		private static GuiPowerControlPanel window=null;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new GuiPowerControlPanel();
						window.frmPowerControlPanel.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}


		/**
		 * Create the application.
		 */
		public GuiPowerControlPanel() {
			initialize();
		}

		private JTextArea txtrPowerControlPanel=null;

		private ActionListener taskPerformer= null;
		static private String message= " ";
		static private String text= " ";
		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frmPowerControlPanel = new JFrame();
			frmPowerControlPanel.setTitle("Power control panel");
			frmPowerControlPanel.setBounds(580, 500, 470, 200);
			frmPowerControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			txtrPowerControlPanel = new JTextArea();
			txtrPowerControlPanel.setEditable(false);
			
			JScrollPane scrollPane = new JScrollPane(txtrPowerControlPanel);	
			
			txtrPowerControlPanel.append(message);
			txtrPowerControlPanel.setFont(new Font("Monospaced", Font.PLAIN, 13));
			DefaultCaret caret = (DefaultCaret)txtrPowerControlPanel.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			
			frmPowerControlPanel.getContentPane().add(scrollPane, BorderLayout.CENTER);
			taskPerformer = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println(message);
					main(null);
					/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
					txtrPowerControlPanel.append(message);
					txtrPowerControlPanel.append("\n");
					/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
				}
			};
		}


		public void write(String mess) {
			message = mess;
		//	text.concat("\n");
			text = text + message + "\n";
			message = text;
			taskPerformer.actionPerformed(null);
		}
	}



