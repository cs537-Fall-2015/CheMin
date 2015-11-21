package chemin_module;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import json.Constants;


public class CheminProcess extends RoverServerRunnable{

	GuiCheminControlPannel control_pannel=null;
	Boolean isExit = false;
	GuiWheelVisu wheel_visu=null;
	public CheminProcess(int port) throws IOException {
		super(port);
		control_pannel = new GuiCheminControlPannel();
		wheel_visu = new GuiWheelVisu();
		GuiCheminControlPannel.main(null);
		GuiWheelVisu.main(null);
	}
	//
	@Override
	public void run() {
		try {
			while(true){
				control_pannel.write("Chemin process -> Waiting for Request");
				getRoverServerSocket().openSocket();
				/*
				 * at this stage, Chemin process can receive the text file which contains the commands to execute
				 */
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message = null;
				try {
					message = oinstr.readObject().toString();
					//control_pannel.write("log message" + message);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// The name of the file to open.
				String fileName = message;

				// This will reference one line at a time
				String line = null;

				try {
					// FileReader reads text files in the default encoding.
					FileReader fileReader = 
							new FileReader(fileName);

					// Always wrap FileReader in BufferedReader.
					BufferedReader bufferedReader = 
							new BufferedReader(fileReader);
					//control_pannel.write("log message2" + bufferedReader.readLine());
					while((line = bufferedReader.readLine()) != null) {

						switch(line){

						case "launch_Chemin_Process":
							try {
								//control_pannel.write("Successfully matche" );
								launch_Chemin_Process();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
							break;
						case "xray_set_position":
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
							f_xray_set_position();
							break;
						case "xray_turn_on":
							f_xray_turn_on();
							break;
						case "sample_receive":
							f_sample_receive();
							break;
						case "cell_next":
							f_cell_next();
							break;
						case "cell_clean_current":
							f_cell_clean_current();
							break;
						case "cell_empty_current":
							f_cell_empty_current();
							break;
						case "inlet_open":
							f_inlet_open();
							break;
						case "sample_fill":
							f_fill_sample_cell();
							break;
						case "inlet_close":
							f_inlet_close();
							break;
						case "analysis_start":
							f_analysis_start();
							break;
						case "cdd_read_erase":
							f_cdd_read_erase(); //1000times in analysis
							break;
						case "cdd_create_diffraction_image":
							f_cdd_create_diffraction_image();
							break;
						case "cdd_create_1d_2t_plot":
							f_cdd_create_1d_2t_plot();
							break;
						case "power_off":
							System.out.println("case power off");
							CHEMIN_POWER_OFF();
							break;
						case "send_results":
							f_send_results();
							break;
						default:
							break;
						}
						for(int i= 0;i<16;i++) {
							if((line.toLowerCase()) == ("cell_go_to "+i)){
								f_cell_go_to(i);
								i=16;
							}
							if((line.toLowerCase()) == ("piezzo_tun_on "+i)) {
								f_piezzo_tun_on(i);
								i=16;
							}
							if((line.toLowerCase()) == ("piezzo_turn_off "+i)){
								f_piezzo_turn_off(i);
								i=16;
							}
						}

					}
					// Always close files.
					bufferedReader.close();         
				} catch(FileNotFoundException ex) {
					control_pannel.write(
							"Unable to open file '" + 
									fileName + "'");                
				} catch(IOException ex) {
					control_pannel.write(
							"Error reading file '" 
									+ fileName + "'");  
				}


				/*
ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
String message = null;
try {
message = oinstr.readObject().toString();
} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

switch (message.toLowerCase()){
case "full process":
try {
launch_Chemin_Process();
} catch (InterruptedException iex) {
System.err.println("Message printer interrupted");
}
break;
}*/
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/*******************************
	 *  FULL PROCEDRE EXPLANATION
	 ****************************/
	/**** CONFIG PHASE ****/
	//	- Position the Xray sensitive CDD imager 
	// Receive drilled powder through the drill, scoop and CHIMRA sorting assembly
	/**** FLILLING PHASE ****/
	//	- Open chemin inlet protection cover
	// 16 dual cells on the sample wheel -> 1piezzo for each dual cell
	//piezzo is active during filling analysis and dumping
	//	- Turn on piezzoelectric actuators number X  
	// put sample in the funnel
	//	- Close inlet protection cover
	//	- If received sample contains more than 5% of contamination then sample rejected
	/**** ANALYSIS PHASE ****/
	//	- Turn on Xray beam 
	//analysis process
	//	- CDD reads out and erase the Xray flux multiple times (+1000times) for analysis
	//data handling
	//	- Identify energy of Xrays strikes by the detector and produce 2D image of diffraction pattern
	//	- Sum all the Xray detected by CDD into a histogram of number of photon vs photon NRJ
	//	- Sum the 2D pattern circumferencially about the central undiffracted beam to create a 1D 2theta plot
	/**** DUMPING PHASE ****/
	//empty the cell after use by inverting and vibrating the sample cell over the sump
	//	- Rotate the sample wheel 180� (sample cell inversion)
	//rotate back to the next sample slot
	//	- Rotate the sample wheel 180�-X (X corresponds to the distance between sample cells)
	//	- Turn off piezzo


	/********************************
	 * COMANDS LIST
	 * *******************************
//xray beam
f_xray_set_position();
f_xray_turn_on();
//sample , sample cell sample wheel
f_sample_receive();
f_cell_next();
f_cell_go_to(cell_number);
f_cell_clean_current();
f_cell_empty_current();
//inlet protection cover
f_inlet_open();
f_inlet_close();
//piezzo
f_piezzo_tun_on(piezzo_number);
f_piezzo_turn_off(piezzo_number);

f_analysis_start();

f_cdd_read_erase(); //1000times in analysis
f_cdd_create_diffraction_image();
f_cdd_create_1d_2t_plot();

f_send_results();
	 ********/


	boolean v_xray_positioned =false;
	boolean v_inlet_cover_opened =false;
	boolean v_powder_received =false;
	boolean[] v_cell_full = new boolean[32]; //16*2 sample slots on the wheel (16dual cells)
	boolean[] v_piezzo_on = new boolean[16]; // 1 piezzo for each dual cell
	boolean v_sample_current_contamintaion_checked =false;
	boolean v_sample_current_is_contaminated = true;
	boolean v_xray_on = false;
	int v_current_sample_cell = 0;
	boolean v_process_over= false;
	boolean v_sample_received=false;
	boolean v_analysis_done=false;

	//
	void f_xray_set_position(){
		control_pannel.write("setting and configuring xray beam position...");
		playSound_xraypos();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		v_xray_positioned=true;
		control_pannel.write("xray position set");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//
	void f_xray_turn_on(){
		control_pannel.write("turning xray on...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_xray_on=true;
		control_pannel.write("xray on");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//
	void f_sample_receive(){
		control_pannel.write("launching the powder sample receiving procedure...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(v_inlet_cover_opened)
		{
			control_pannel.write("error: inlet cover is opened, please close inlet cover first");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("operation aborted");
		} else {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("powder is in the scoop...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("powder is in sorting assembly...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_powder_received=true;
			control_pannel.write("sample powder received");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_sample_received=true;
		}
	}

	void f_cell_next(){
		control_pannel.write("turning sample wheel to next sample slot...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_current_sample_cell++;
		if (v_current_sample_cell>=32)
		{
			v_current_sample_cell=0;
		}
		if(v_current_sample_cell>=16)
		{
			v_cell_full[v_current_sample_cell-16]=false;
		}else if(v_current_sample_cell<16)
		{
			v_cell_full[16+v_current_sample_cell]=false;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!isExit){
			wheel_visu.update_wheel(v_current_sample_cell);
			control_pannel.write("current sample cell is now: "+v_current_sample_cell);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void f_cell_prev(){
		control_pannel.write("turning sample wheel to previous sample slot...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_current_sample_cell--;
		if (v_current_sample_cell<0)
		{
			v_current_sample_cell=31;
		}
		if(v_current_sample_cell>=16)
		{
			v_cell_full[v_current_sample_cell-16]=false;
		}else if(v_current_sample_cell<16)
		{
			v_cell_full[16+v_current_sample_cell]=false;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wheel_visu.update_wheel(v_current_sample_cell);
		control_pannel.write("current sample cell is now: "+v_current_sample_cell);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//
	void f_cell_go_to(int cell_number){
		if((cell_number<16)&&(cell_number>=0))
		{
			control_pannel.write("current sample cell is : "+v_current_sample_cell);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("going to sample number: "+cell_number);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int index = 0;
			if(cell_number<v_current_sample_cell)
			{
				index = v_current_sample_cell-cell_number;
				for(int i=0;i<index;i++)
				{
					f_cell_prev();
				}
			}
			else if(cell_number>v_current_sample_cell)
			{
				index = cell_number-v_current_sample_cell;
				for(int i=0;i<index;i++)
				{
					f_cell_next();
				}
			}
		}
	}
	//
	void f_cell_clean_current(){
		control_pannel.write("starting cleaning procedure...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f_cell_empty_current();
		v_sample_current_contamintaion_checked=true;
		v_sample_current_is_contaminated=false;
		control_pannel.write("sample is clean...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void f_cell_empty_current(){
		control_pannel.write("starting emptying procedure...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		control_pannel.write("turning the sample upside down...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<16;i++)
		{
			System.out.println(isExit);
			if(!isExit)
				f_cell_next();
		}
		control_pannel.write("sample is now upside down...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f_piezzo_tun_on(v_current_sample_cell/2);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_cell_full[v_current_sample_cell]=false;
		control_pannel.write("sample is now empty");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		control_pannel.write("turning the sample upside down...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<16;i++)
		{
			if(!isExit)
				f_cell_next();
		}
	}

	void f_inlet_open(){
		control_pannel.write("opening inlet cover...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(v_inlet_cover_opened)
		{
			control_pannel.write("inlet cover is already opened");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_inlet_cover_opened=true;
			control_pannel.write("inlet cover now opened");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void f_fill_sample_cell(){
		control_pannel.write("filling sample cell...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!v_inlet_cover_opened)
		{
			control_pannel.write("error: inlet cover is not opened");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("aborting procedure");
		} else {
			if(v_sample_received)
			{
				f_piezzo_tun_on(v_current_sample_cell/2);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				control_pannel.write("sample cell is now full");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				v_cell_full[v_current_sample_cell]=true;
				v_powder_received=false;
			} else {
				control_pannel.write("error: no powder received, please receive powder first");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				control_pannel.write("aborting procedure");
			}
		}
	}

	void f_inlet_close() {
		control_pannel.write("closing inlet cover...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!v_inlet_cover_opened)
		{
			control_pannel.write("inlet cover is already closed");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_inlet_cover_opened=false;
			control_pannel.write("inlet cover now closed");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void f_piezzo_tun_on(int piezzo_number) {
		control_pannel.write("turning piezzo on...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playSound_piezzo();
		if(v_piezzo_on[piezzo_number])
		{
			control_pannel.write("piezzo "+piezzo_number+ " already on");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_piezzo_on[piezzo_number]=true;
			control_pannel.write("piezzo "+piezzo_number+ " now on");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void f_piezzo_turn_off(int piezzo_number){
		control_pannel.write("turning piezzo off...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!v_piezzo_on[piezzo_number])
		{
			control_pannel.write("piezzo "+piezzo_number+ " already off");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_piezzo_on[piezzo_number]=false;
			control_pannel.write("piezzo "+piezzo_number+ " now off");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//
	void f_analysis_start(){
		control_pannel.write("verification that every components ready to start analysis phase....");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(v_xray_positioned)
		{
			control_pannel.write("xray position OK");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(v_xray_on)
			{
				control_pannel.write("xray on OK");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!v_inlet_cover_opened)
				{
					control_pannel.write("inlet cover closed OK");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(v_cell_full[v_current_sample_cell])
					{
						if((v_sample_current_contamintaion_checked)&&(!v_sample_current_is_contaminated)){
							control_pannel.write("sample cell contamination checked OK");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							control_pannel.write("sample is not contaminated OK");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							f_piezzo_turn_off(v_current_sample_cell);
							/************************
							 * ANALYSIS START
							 ************************/
							control_pannel.write("WARNING: now entering the analysis phase....");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							playSound_analysis();
							for(int i = 0 ; i<50;i++) //NORMALLY MRE THAN 1000times
							{
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								f_cdd_read_erase();
							}
							control_pannel.write("Analysis terminated, no error detected");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							v_analysis_done=true;
							v_xray_positioned=false;

						}else{
							control_pannel.write("sample cell contamination not checked or sample contaminated");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							control_pannel.write("operation aborted");
						}
					}else{
						control_pannel.write("sample not full");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						control_pannel.write("operation aborted");
					}
				}else{
					control_pannel.write("inlet cover is opened");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					control_pannel.write("operation aborted");
				}
			} else{
				control_pannel.write("xray not on");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				control_pannel.write("operation aborted");
			}
		} else{
			control_pannel.write("xray not positioned");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("operation aborted");
		}
	}

	void f_cdd_read_erase(){
		control_pannel.write("read... erase....");
	}
	//
	void f_cdd_create_diffraction_image(){
		control_pannel.write("creating diffraction image....");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(v_analysis_done){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("diffraction image created");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{
			control_pannel.write("error: no analysis to create diffraction image");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("operation aborted");
		}

	}
	//
	void f_cdd_create_1d_2t_plot(){
		control_pannel.write("creating 1D 2theta plot image....");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(v_analysis_done){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				CMIN_CreateXRDJson();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_process_over=true;
			control_pannel.write("results created and ready to be sent");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("process now over");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{
			control_pannel.write("error: no analysis to create diffraction image");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			control_pannel.write("operation aborted");
		}
	}

	public boolean CMIN_CreateXRDJson() throws IOException{
		JSONObject jsonObject = createJsonFromImage();

		File file = new File(Constants.ROOT_PATH+"XrdDiffraction.json");

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(jsonObject.toString());
		bw.close();

		return true;
	}

	private  MediaPlayer playSound_analysis() {

		new JFXPanel();
		String bip = Constants.ROOT_PATH+"Analysis.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();

		return mediaPlayer;
	}
	private  MediaPlayer playSound_piezzo() {

		new JFXPanel();
		String bip = Constants.ROOT_PATH+"Wheel.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();

		return mediaPlayer;
	}

	private  MediaPlayer playSound_xraypos() {

		new JFXPanel();
		String bip = Constants.ROOT_PATH+"Xraypos.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();

		return mediaPlayer;
	}

	private JSONObject createJsonFromImage() throws IOException {

		String encodedImage = getStringFromImage();

		JSONObject jsonObj = (JSONObject) JSONValue.parse("{\"image\":\"" + encodedImage + "\"}");
		return jsonObj;
	}

	private static String getStringFromImage() throws IOException {

		BufferedImage originalImage = ImageIO.read(new File(Constants.ROOT_PATH+"xrayDiffraction.jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( originalImage, "jpg", baos );
		baos.flush();

		byte[] imageInByte = baos.toByteArray();
		baos.close();

		String encoded = Base64.getEncoder().encodeToString(imageInByte);
		return encoded;
	}

	//
	void f_send_results(){
		if(v_process_over)
		{
			v_process_over=false;
			//end of process, send results to telecom 
			control_pannel.write("End of process, sending results to telecom...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CheminClient telecomclient = null;
			try {
				telecomclient = new CheminClient(9002,null);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			Thread telecomclientthread=RoverThreadHandler.getRoverThreadHandler().getNewThread(telecomclient);
			telecomclientthread.start();
		}
	}


	void launch_Chemin_Process() throws InterruptedException, IOException {
		//	setT(Thread.currentThread());
		control_pannel.write("CHEMIN Process Started:");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		//
		f_xray_set_position();
		//
		f_sample_receive();
		//
		f_cell_go_to(4);
		//
		f_cell_clean_current();

		f_inlet_open();

		f_fill_sample_cell();

		f_inlet_close();

		f_piezzo_turn_off(v_current_sample_cell/2);
		//
		f_xray_turn_on();
		//
		f_analysis_start();
		//
		f_cdd_create_diffraction_image();
		//
		f_cdd_create_1d_2t_plot();
		//
		f_send_results();
		//
		CHEMIN_POWER_OFF();
	}
	public boolean CMIN_FunnelPiezoOff(){
		return true;
	}

	public boolean CMIN_SamlecellPiezoOff(){
		return true;
	}
	public boolean CMIN_XrayOff(){
		return true;
	}

	public void CHEMIN_POWER_OFF() throws UnknownHostException, IOException {

		Socket socket = new Socket("localhost",9013);
		ObjectOutputStream outstr = null;
		try {
			outstr = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outstr.writeObject("turning power off");
			outstr.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("power off");
		control_pannel.write(" Cryo Cooler off !");
		CMIN_FunnelPiezoOff();
		control_pannel.write("Funnel Piezo off !");
		CMIN_SamlecellPiezoOff();
		control_pannel.write("SamplePiezo off !");
		CMIN_XrayOff();
		control_pannel.write("Xray off!");
		Thread.currentThread().interrupt();
	}
}



