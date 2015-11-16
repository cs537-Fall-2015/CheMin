package cheminProcess;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import generic.RoverServerRunnable;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import json.Constants;
import json.GlobalReader;

public class Chemin_process extends RoverServerRunnable{
	private Thread t = null;
	private boolean power_is_on = false;

	public Chemin_process(int port) throws IOException {
		super(port);
	}

	public void run() {
		try {				
			System.out.println("Chemin process -> Waiting for Request");
			getRoverServerSocket().openSocket();
			/*		------------------------> NEED TO BE DONE <-------------------------------
			 * at this stage, Chemin process can receive the following commands:
			 * 		POWER_IS_ON (from power)	:	chemin knows that it is turned on and is ready to receive orders to start working 
			 * 		POWER_IS_OFF (from power)	:	chemin knows that the power has been turned off and needs to stop everything immediatly
			 * 		CHEMIN_START				:	Full chemin cycle starting (required: Power_is_on)
			 * 		----> other commands yet to come
			 */
			ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
			String message=oinstr.readObject().toString();
			System.out.println(message);
			// TO BE CONTINUED HERE 
			if(message.toLowerCase()=="POWER_IS_ON" && getRoverServerSocket().getSocket().getPort()==POWER_SOCKET) //to be corrected
			{
				power_is_on = true;
			}
			if(message.toLowerCase()=="POWER_IS_OFF" && getRoverServerSocket().getSocket().getPort()==POWER_SOCKET) //to be corrected
			{
				power_is_on = true;
			}
			if(getRoverServerSocket().getSocket().getPort()==CHEMIN_SERVER_SOCKET) //to be corrected
			{
				switch (message.toLowerCase()){
				case "CHEMIN_START": 
					launch_Chemin_Process();
					break;
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	boolean launch_Chemin_Process() throws InterruptedException, IOException {
		setT(Thread.currentThread());
		System.out.println("CHEMIN Process Started:");
		Thread.sleep(2000);
		if(CMIN_RemoveFunnelContamination()){
			System.out.println("\t\tCryo Cooler On");
			Thread.sleep(5000);
			System.out.println("\t\t\tRemoving Funnel contamination");
			Thread.sleep(2000);
			if(CMIN_CheckCHIMRA()){
				System.out.println("\t\t\t\tChecking if CHIMRA has Sample or not");
				Thread.sleep(2000);
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

