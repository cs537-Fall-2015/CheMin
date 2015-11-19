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

	public CheminProcess(int port) throws IOException {
		super(port);

	}

	@Override
	public void run() {
		try {			
			while(true){
				System.out.println("Chemin process -> Waiting for Request");
				getRoverServerSocket().openSocket();
				/*	
				 * at this stage, Chemin process can receive the text file which contains the commands to execute
				 */
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message = null;
				try {
					message = oinstr.readObject().toString();
					System.out.println("log message" + message);
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
					//System.out.println("log message2" + bufferedReader.readLine());
					while((line = bufferedReader.readLine()) != null) {
						
						switch(line){
						
						case "launch_Chemin_Process":
							try {
								//System.out.println("Successfully matche" );
								launch_Chemin_Process();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case "xray_set_position":
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
					System.out.println(
							"Unable to open file '" + 
									fileName + "'");                
				} catch(IOException ex) {
					System.out.println(
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
	//		- Position the Xray sensitive CDD imager 
	// Receive drilled powder through the drill, scoop and CHIMRA sorting assembly
	/**** FLILLING PHASE ****/
	//		- Open chemin inlet protection cover
	// 16 dual cells on the sample wheel -> 1piezzo for each dual cell
	//piezzo is active during filling analysis and dumping
	//		- Turn on piezzoelectric actuators number X  
	// put sample in the funnel
	//		- Close inlet protection cover
	//		- If received sample contains more than 5% of contamination then sample rejected
	/**** ANALYSIS PHASE ****/
	//		- Turn on Xray beam 
	//analysis process
	//		- CDD reads out and erase the Xray flux multiple times (+1000times) for analysis
	//data handling
	//		- Identify energy of Xrays strikes by the detector and produce 2D image of diffraction pattern
	//		- Sum all the Xray detected by CDD into a histogram of number of photon vs photon NRJ
	//		- Sum the 2D pattern circumferencially about the central undiffracted beam to create a 1D 2theta plot
	/**** DUMPING PHASE ****/
	//empty the cell after use by inverting and vibrating the sample cell over the sump
	//		- Rotate the sample wheel 180� (sample cell inversion)
	//rotate back to the next sample slot
	//		- Rotate the sample wheel 180�-X (X corresponds to the distance between sample cells)
	//		- Turn off piezzo


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

	//
	void f_xray_set_position(){
		System.out.println("setting and configuring xray beam position...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		v_xray_positioned=true;
		System.out.println("xray position set");
	}
	//
	void f_xray_turn_on(){
		System.out.println("turning xray on...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_xray_on=true;
		System.out.println("xray on");
	}
	//
	void f_sample_receive(){
		System.out.println("launching the powder sample receiving procedure...");
		if(v_inlet_cover_opened)
		{
			System.out.println("error: inlet cover is opened, please close inlet cover first");
			System.out.println("operation aborted");
		} else {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("powder is in the scoop...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("powder is in sorting assembly...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_powder_received=true;
			System.out.println("sample powder received");
		}
	}

	void f_cell_next(){
		System.out.println("turning sample wheel to next sample slot...");
		v_current_sample_cell = v_current_sample_cell++;
		if (v_current_sample_cell>=16)
		{
			v_current_sample_cell=0;
		}
		if(v_current_sample_cell>=16)
		{
			v_cell_full[v_current_sample_cell-16]=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(v_current_sample_cell<16)
		{
			v_cell_full[16+v_current_sample_cell]=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("current sample cell is now: "+v_current_sample_cell);
	}

	void f_cell_prev(){
		System.out.println("turning sample wheel to previous sample slot...");
		v_current_sample_cell = v_current_sample_cell--;
		if (v_current_sample_cell<0)
		{
			v_current_sample_cell=15;
		}
		if(v_current_sample_cell>=16)
		{
			v_cell_full[v_current_sample_cell-16]=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("current sample cell is now: "+v_current_sample_cell);
		}else if(v_current_sample_cell<16)
		{
			v_cell_full[16+v_current_sample_cell]=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("current sample cell is now: "+v_current_sample_cell);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("current sample cell is now: "+v_current_sample_cell);
	}
	//
	void f_cell_go_to(int cell_number){
		if((cell_number<16)&&(cell_number>=0))
		{
			System.out.println("going to sample number: "+cell_number);
			System.out.println("current sample cell is currently: "+v_current_sample_cell);
			if(cell_number<v_current_sample_cell)
			{
				for(int i=0;i<v_current_sample_cell-cell_number;i++)
				{
					f_cell_prev();
				}
			}
			else if(cell_number>v_current_sample_cell)
			{
				for(int i=0;i<cell_number-v_current_sample_cell;i++)
				{
					f_cell_next();
				}
			}
		}
	}
	//
	void f_cell_clean_current(){
		System.out.println("starting cleaning procedure...");
		f_cell_empty_current();
		v_sample_current_contamintaion_checked=true;
		v_sample_current_is_contaminated=false;
		System.out.println("sample is clean...");
	}

	void f_cell_empty_current(){
		System.out.println("starting emptying procedure...");
		System.out.println("turning the sample upside down...");
		for(int i=0;i<16;i++)
		{
			f_cell_next();
		}
		System.out.println("sample is now upside down...");
		f_piezzo_tun_on(v_current_sample_cell/2);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v_cell_full[v_current_sample_cell]=false;
		System.out.println("sample is now empty");
		System.out.println("turning the sample upside down...");
		for(int i=0;i<16;i++)
		{
			f_cell_next();
		}
	}

	void f_inlet_open(){
		System.out.println("opening inlet cover...");
		if(v_inlet_cover_opened)
		{
			System.out.println("inlet cover is already opened");
		} else {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_inlet_cover_opened=true;
			System.out.println("inlet cover now opened");
		}
	}

	void f_inlet_close() {
		System.out.println("closing inlet cover...");
		if(!v_inlet_cover_opened)
		{
			System.out.println("inlet cover is already closed");
		} else {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_inlet_cover_opened=false;
			System.out.println("inlet cover now closed");
		}
	}

	void f_piezzo_tun_on(int piezzo_number) {
		System.out.println("turning piezzo on...");
		if(v_piezzo_on[piezzo_number])
		{
			System.out.println("piezzo "+piezzo_number+ " already on");
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_piezzo_on[piezzo_number]=true;
			System.out.println("piezzo "+piezzo_number+ " now on");
		}
	}

	void f_piezzo_turn_off(int piezzo_number){
		System.out.println("turning piezzo off...");
		if(!v_piezzo_on[piezzo_number])
		{
			System.out.println("piezzo "+piezzo_number+ " already off");
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v_piezzo_on[piezzo_number]=false;
			System.out.println("piezzo "+piezzo_number+ " now off");
		}
	}

	//
	void f_analysis_start(){
		System.out.println("verification that every components ready to start analysis phase....");
		if(v_xray_positioned)
		{
			System.out.println("xray position OK");
			if(v_xray_on)
			{
				System.out.println("xray on OK");
				if(!v_inlet_cover_opened)
				{
					System.out.println("inlet cover closed OK");
					if(v_cell_full[v_current_sample_cell])
					{
						if((v_sample_current_contamintaion_checked)&&(!v_sample_current_is_contaminated)){
							System.out.println("sample cell contamination checked OK");
							System.out.println("sample is not contaminated OK");		
							f_piezzo_turn_off(v_current_sample_cell);
							/************************
							 * ANALYSIS START
							 ************************/
							System.out.println("WARNING: now entering the analysis phase....");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							playSound();
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
							v_xray_positioned=false;

						}else{
							System.out.println("sample cell contamination not checked or sample contaminated");
							System.out.println("operation aborted");
						}
					}else{
						System.out.println("sample not full");
						System.out.println("operation aborted");
					}
				}else{
					System.out.println("inlet cover is opened");
					System.out.println("operation aborted");
				}
			} else{
				System.out.println("xray not on");
				System.out.println("operation aborted");
			}
		} else{
			System.out.println("xray not positioned");
			System.out.println("operation aborted");
		}
	}

	void f_cdd_read_erase(){
		System.out.println("read... erase....");
	}
	//
	void f_cdd_create_diffraction_image(){
		System.out.println("creating diffraction image....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("diffraction image created");
	}
	//
	void f_cdd_create_1d_2t_plot(){
		System.out.println("creating 1D 2theta plot image....");
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
		System.out.println("results created and ready to be sent");
		System.out.println("process now over");
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

	private  MediaPlayer playSound() {

		new JFXPanel();
		String bip = Constants.ROOT_PATH+"Voice.mp3";
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
			System.out.println("End of process, sending results to telecom...");
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
		System.out.println("CHEMIN Process Started:");

		//
		f_xray_set_position();
		//
		f_sample_receive();
		//
		f_cell_go_to(5);
		//
		f_cell_clean_current();

		f_inlet_open();

		f_piezzo_tun_on(v_current_sample_cell/2) ;

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
	}
}

/*
public void CHEMIN_POWER_OFF() {

	System.out.println(" Cryo Cooler off !");
	CMIN_FunnelPiezoOff();
	System.out.println("Funnel Piezo off !");
	CMIN_SamlecellPiezoOff();
	System.out.println("SamplePiezo off !");
	CMIN_XrayOff();
	System.out.println("Xray off!");
}*/


