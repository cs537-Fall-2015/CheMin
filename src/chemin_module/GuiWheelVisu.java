package chemin_module;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class GuiWheelVisu {

	private JFrame frmCheminSampleWheel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiWheelVisu window = new GuiWheelVisu();
					window.frmCheminSampleWheel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiWheelVisu() {
		initialize();
	}

	private static JLabel lblImagelbl = null;
	private static int sample_number = 0;
	private ActionListener taskPerformer= null;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheminSampleWheel = new JFrame();
		frmCheminSampleWheel.setTitle("Chemin sample wheel");
		frmCheminSampleWheel.setBounds(1100, 100, 611, 654);
		frmCheminSampleWheel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lblImagelbl = new JLabel("imagelbl");
		Image img = null;
		switch(sample_number){
		case 0:
			img = new ImageIcon(this.getClass().getResource("/resources/img0.png")).getImage();
			break;
		case 1:
			img = new ImageIcon(this.getClass().getResource("/resources/img1.png")).getImage();
			break;
		case 2:
			img = new ImageIcon(this.getClass().getResource("/resources/img2.png")).getImage();
			break;
		case 3:
			img = new ImageIcon(this.getClass().getResource("/resources/img3.png")).getImage();
			break;
		case 4:
			img = new ImageIcon(this.getClass().getResource("/resources/img4.png")).getImage();
			break;
		case 5:
			img = new ImageIcon(this.getClass().getResource("/resources/img5.png")).getImage();
			break;
		case 6:
			img = new ImageIcon(this.getClass().getResource("/resources/img6.png")).getImage();
			break;
		case 7:
			img = new ImageIcon(this.getClass().getResource("/resources/img7.png")).getImage();
			break;
		case 8:
			img = new ImageIcon(this.getClass().getResource("/resources/img8.png")).getImage();
			break;
		case 9:
			img = new ImageIcon(this.getClass().getResource("/resources/img9.png")).getImage();
			break;
		case 10:
			img = new ImageIcon(this.getClass().getResource("/resources/img10.png")).getImage();
			break;
		case 11:
			img = new ImageIcon(this.getClass().getResource("/resources/img11.png")).getImage();
			break;
		case 12:
			img = new ImageIcon(this.getClass().getResource("/resources/img12.png")).getImage();
			break;
		case 13:
			img = new ImageIcon(this.getClass().getResource("/resources/img13.png")).getImage();
			break;
		case 14:
			img = new ImageIcon(this.getClass().getResource("/resources/img14.png")).getImage();
			break;
		case 15:
			img = new ImageIcon(this.getClass().getResource("/resources/img15.png")).getImage();
			break;
		case 16:
			img = new ImageIcon(this.getClass().getResource("/resources/img16.png")).getImage();
			break;
		case 17:
			img = new ImageIcon(this.getClass().getResource("/resources/img17.png")).getImage();
			break;
		case 18:
			img = new ImageIcon(this.getClass().getResource("/resources/img18.png")).getImage();
			break;
		case 19:
			img = new ImageIcon(this.getClass().getResource("/resources/img19.png")).getImage();
			break;
		case 20:
			img = new ImageIcon(this.getClass().getResource("/resources/img20.png")).getImage();
			break;
		case 21:
			img = new ImageIcon(this.getClass().getResource("/resources/img21.png")).getImage();
			break;
		case 22:
			img = new ImageIcon(this.getClass().getResource("/resources/img22.png")).getImage();
			break;
		case 23:
			img = new ImageIcon(this.getClass().getResource("/resources/img23.png")).getImage();
			break;
		case 24:
			img = new ImageIcon(this.getClass().getResource("/resources/img24.png")).getImage();
			break;
		case 25:
			img = new ImageIcon(this.getClass().getResource("/resources/img25.png")).getImage();
			break;
		case 26:
			img = new ImageIcon(this.getClass().getResource("/resources/img26.png")).getImage();
			break;
		case 27:
			img = new ImageIcon(this.getClass().getResource("/resources/img27.png")).getImage();
			break;
		case 28:
			img = new ImageIcon(this.getClass().getResource("/resources/img28.png")).getImage();
			break;
		case 29:
			img = new ImageIcon(this.getClass().getResource("/resources/img29.png")).getImage();
			break;
		case 30:
			img = new ImageIcon(this.getClass().getResource("/resources/img30.png")).getImage();
			break;
		case 31:
			img = new ImageIcon(this.getClass().getResource("/resources/img31.png")).getImage();
			break;
		}
		
		lblImagelbl.setIcon(new ImageIcon(img));
		frmCheminSampleWheel.getContentPane().add(lblImagelbl, BorderLayout.CENTER);


		taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main(null);
				/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
				/* THIS DOES NOT WORK, DIDNT FIND HOW TO REFRESH VIEW*/
			}
		};
	}


	public void update_wheel(int sample_nbr) {
		sample_number = sample_nbr;
		taskPerformer.actionPerformed(null);
	}



}
