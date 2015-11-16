package chemin_module;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import json.GlobalReader;


public class CheminProcess extends RoverServerRunnable{
	private Thread t = null;

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
		/*		ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();

				switch (message.toLowerCase()){
				case "full process":
					try {
						launch_Chemin_Process();
					} catch (InterruptedException iex) {
						System.err.println("Message printer interrupted");
					}
					break;
				}	*/
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	boolean v_xray_positioned =false;
	boolean v_inlet_cover_opened =false;
	boolean v_sample_wheel_turning =false;
	boolean[] v_sample_full = new boolean[32]; //16*2 sample slots on the wheel (16dual cells)
	boolean[] v_piezzo_on = new boolean[16]; // 1 piezzo for each dual cell
	boolean v_sample_contamintaion_checked =false;
	boolean v_sample_is_contaminated = true;
	boolean v_xray_on = false;
	int v_current_sample_cell = 0;
	boolean v_process_over= false;
	
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
	
	void f_position_xray(){
	}
	void f_receive_sample(){
	}
	
	void f_receive_sample(){
	}
	void f_receive_sample(){
	}
	void f_receive_sample(){
	}
	
	boolean launch_Chemin_Process() throws InterruptedException, IOException {
		setT(Thread.currentThread());
		System.out.println("CHEMIN Process Started:");
		
		//xray beam
		f_position_xray();
		//sample , sample cell sample wheel
		f_receive_sample();
		f_next_sample_cell();
		f_go_to_sample_cell(int cell_number);
		f_clean_sample_cell(int cell_number);
		//inlet protection cover
		f_open_inlet();
		f_close_inlet();
		//piezzo
		f_tun_on_piezzo(int piezzo_number);
		f_turn_off_piezzo(int piezzo_number);

		f_send_results();
		
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
//		- Rotate the sample wheel 180° (sample cell inversion)
		//rotate back to the next sample slot
//		- Rotate the sample wheel 180°-X (X corresponds to the distance between sample cells)
//		- Turn off piezzo
		
		
		
		Thread.sleep(2000);
		if(CMIN_RemoveFunnelContamination()){
			System.out.println("\t\tCryo Cooler On");
			Thread.sleep(5000);
		}
			System.out.println("\t\t\tRemoving Funnel contamination");
			Thread.sleep(2000);
	}
			if(CMIN_CheckCHIMRA()){
				System.out.println("\t\t\t\tChecking if CHIMRA has Sample or not");
				Thread.sleep(2000);
			}
				if(CMIN_RemoveSampleCellContamination()){
					System.out.println("\t\t\t\t\t\tCleaning Sample cell ");
					Thread.sleep(2000);
					if(CMIN_FunnelPiezoOn()){
						System.out.println("\t\t\t\t\t\tFunnel piezo is on now");
						playMusic();
						System.out.println("\t\t\t\t\t\t\t\tWait for 5 seconds:for sample to go in sample cell");
						Thread.sleep(5000);
						System.out.println("\t\t\t\t\t\t\t\t\t\tSample is in sample cell now");

						CMIN_SamlecellPiezoOn();
						playMusic();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tSample cell piezo Started");
						CMIN_XrayOn();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tXrays are on now");
						Thread.sleep(10000);
						CMIN_ReadCCD();  //each 30 seconds and compress
						if(CMIN_CreateXRDJson()){

						}
						
					}
				}else{
					System.out.println("Sample cell is not clean");
					return false;
				}
			}else{
				System.out.println("No sample with CHIMRA");
				return false;
			}
		}else{
			System.out.println("Contamination not removed yet!! Remove Contamination first");
			return false;
		}

		CHEMIN_POWER_OFF();

		return true;				
	}

	void send_requirments_to_power() {
		try{
			if(getRoverServerSocket().getSocket().getPort()==9013){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				System.out.println("CHEMIN-->Power roup");
				System.out.println("CHEMIN-->Sending you my requirements in a json file");
				GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"PowerRequirement");
				JSONObject json= gr.getJSONObject();
				outstr.writeObject(json);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	void send_image_to_telecom() {
		try{
			if(getRoverServerSocket().getSocket().getPort()==9002){
				System.out.println("contacting to tele success");
				ObjectOutputStream ostr=new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				ostr.writeObject("Chemin--> telecommunication: Read this image json");
				GlobalReader gr2=new GlobalReader(Constants.ROOT_PATH+"XrdDiffraction");
				JSONObject jsonTele= gr2.getJSONObject();
				ostr.writeObject(jsonTele);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	        

	public void CHEMIN_POWER_OFF() {

		System.out.println(" Cryo Cooler off !");
		CMIN_FunnelPiezoOff();
		System.out.println("Funnel Piezo off !");
		CMIN_SamlecellPiezoOff();
		System.out.println("SamplePiezo off !");
		CMIN_XrayOff();
		System.out.println("Xray off!");
	}

	public boolean CMIN_RemoveFunnelContamination(){
		return true;
	}

	public boolean CMIN_RemoveSampleCellContamination(){ 
		return true;
	}
	boolean CMIN_CheckCHIMRA(){
		return true;
	}

	public boolean CMIN_FunnelPiezoOn() {

		return true;
	}
	public boolean CMIN_FunnelPiezoOff(){
		return true;
	}

	public boolean CMIN_SamlecellPiezoOn(){

		return true;
	}

	public boolean CMIN_SamlecellPiezoOff(){
		return true;
	}
	public boolean CMIN_XrayOn(){
		return true;
	}
	public boolean CMIN_XrayOff(){
		return true;
	}
	public boolean CMIN_ReadCCD(){
		return true;
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

	private  MediaPlayer playMusic() {

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

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}
}
