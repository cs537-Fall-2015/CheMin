package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import generic.RoverClientRunnable;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;
import json.Constants;
import json.GlobalReader;
import power.Power;
import telecommunication.Telecom;
import java.util.Base64;
import java.awt.image.BufferedImage;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;

public class CheMinMain {

	public static void main(String[] args) {
		
		// Defining a port for CheMin
		int port_chemin = 9008;
		
		try {
			
			// Create a thread for CHEMIN SERVER
			CHEMIN_Server serverChemin = new CHEMIN_Server(port_chemin);
			Thread server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverChemin);
			server_chemin.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create a thread for Power SERVER
		Power ps=null;
		try {
			ps = new Power(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	
		// Create a thread for CHEMIN SERVER
		Telecom ts=null;
		try {
			ts = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread servertele=RoverThreadHandler.getRoverThreadHandler().getNewThread(ts);
		servertele.start();
	}
	}

class CHEMIN_Server extends RoverServerRunnable {

	private static boolean ccuYes = false;
	
	public CHEMIN_Server(int port) throws IOException {
		super(port);
	}
// Start
	@Override
	public void run() {
		
		try {
			
			while (true) {
				
				System.out.println("CHEMIN Server -> Waiting for Request");
				
				// Creating socket and waiting for client connection or for CCUMain
				getRoverServerSocket().openSocket();
				
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				Thread t=Thread.currentThread();
				
				//Wait for 7 seconds
				Thread.sleep(7000);
				System.out.println("Message Received is: "+message);
				
				//If thread is interrupted, set the message to power off
				if(t.isInterrupted()){
					message = "power off";
				}
				
				switch(message.toLowerCase()){
				
				// If message is chemin_on, set the CCU to True
				case "chemin_on":
					ccuYes = true;
					System.out.println("CCU has send request to CHEMIN to turn on");
					System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
					CHEMIN_Client client=new CHEMIN_Client(9013,null);
					Thread power=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
					power.start();
					break;
				// If message is power on, start the chemin process else print the required message 
				case "power on":
					
					boolean process = false;
					if(ccuYes){
						CHEMIN chemin = new CHEMIN();
						process=chemin.CHEMIN_Process();
					}else{
						System.out.println(" CHEMIN dont have permission from CCU ,Yet !");
					}
					// 	If process is true, Create the new CHEMIN_Client and make the client to listen on port no. 9002
					if(process){
						CHEMIN_Client teleclient=new CHEMIN_Client(9002,null);
						Thread telecommunication=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
						telecommunication.start();
						
					}
					
					break;
				// If power is off,Call the CHEMIN_POWER_OFF function of CHEMIN module
				case "power off":
					System.out.println(" Deleting Thread ..");
					new CHEMIN().CHEMIN_POWER_OFF();
					break;
				}
				// Stop getting request from CHEMIN_Client and close all the resources
				inputFromAnotherObject.close();
				}
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error: " + error.getMessage());
		}

	}
}

class CHEMIN_Client extends RoverClientRunnable{
	
	public CHEMIN_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			
			// Call the getPort method on the object returned by getSocket method
			// Check if the value is returned by the getPort = 9013 then create the output string
			
			
			if(getRoverSocket().getSocket().getPort()==9013){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				System.out.println("CHEMIN-->Power group");
				System.out.println("CHEMIN-->Sending you my requirements in a json file");
				
				// Read the power requirement from GlobalReader and convert that into json object
				
				GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"PowerRequirement");
				
				// send that json object by output string to CHEMIN_Server

				JSONObject json= gr.getJSONObject();
				outstr.writeObject(json);
			}
			// If the value returned by getPort method = 9002 then read the image from GlobalReader and convert that into json object
				
				if(getRoverSocket().getSocket().getPort()==9002){
					
					System.out.println("contacting to tele success");
					// Create new object and read image from Rover socket
					ObjectOutputStream ostr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
					
					// write the image object
					ostr.writeObject("Chemin--> telecommunication: Read this image json");
					GlobalReader gr2=new GlobalReader(Constants.ROOT_PATH+"XrdDiffraction");
					
					// Send json object to Server
					JSONObject jsonTele= gr2.getJSONObject();
					ostr.writeObject(jsonTele);
				}
			
}	        
        catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class CHEMIN {
	private Thread t = null;
	
	boolean CHEMIN_Process() throws InterruptedException, IOException{
		// Get the current thread and make the current thread to sleep for 2 seconds
		setT(Thread.currentThread());
		
		System.out.println("CHEMIN Process Started:");
		Thread.sleep(2000);
		// If the Funnel Contamination is removed, start the cryo cooler and wait for 5 seconds
		if(CMIN_RemoveFunnelContamination()){
			System.out.println("\t\tCryo Cooler On");
			Thread.sleep(5000);
			System.out.println("\t\t\tRemoving Funnel contamination");

			// Another wait for 2 seconds 
			Thread.sleep(2000);
			
			// Check if CHIMRA has any sample or not
			// If any CHIMRA sample then wait for 2 seconds
			if(CMIN_CheckCHIMRA()){
				System.out.println("\t\t\t\tChecking if CHIMRA has Sample or not");
				Thread.sleep(2000);
				// If Sample Cell Contamination is removed, wait for 2 seconds
				if(CMIN_RemoveSampleCellContamination()){
					System.out.println("\t\t\t\t\t\tCleaning Sample cell ");
					Thread.sleep(2000);
					// Check if FunnelPiezo is ON and start Play Music
					if(CMIN_FunnelPiezoOn()){
						System.out.println("\t\t\t\t\t\tFunnel piezo is on now");
							playMusic();
						System.out.println("\t\t\t\t\t\t\t\tWait for 5 seconds:for sample to go in sample cell");
						Thread.sleep(5000);
						System.out.println("\t\t\t\t\t\t\t\t\t\tSample is in sample cell now");
						
						CMIN_SamlecellPiezoOn();
						playMusic();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tSample cell piezo Started");
						// Start the Xray and wait for 10 seconds
						CMIN_XrayOn();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tXrays are on now");
						Thread.sleep(10000);
						
						// Read CCD from CCUMain
						// Try to create XRD json file and if created, do nothing
						CMIN_ReadCCD();  //each 30 seconds and compress
						if(CMIN_CreateXRDJson()){
							
						}
					}
					// Else print the required message for Sample Cell
				}else{
					System.out.println("Sample cell is not clean");
					return false;
				}
				// Else print the required message for CHIMRA
			}else{
				System.out.println("No sample with CHIMRA");
				return false;
			}
			// Else print the required message for Contamination
		}else{
			System.out.println("Contamination not removed yet!! Remove Contamination first");
			return false;
		}
		// Send a command power off to chemin
		CHEMIN_POWER_OFF();
		
		return true;
		
		
	}

	public void CHEMIN_POWER_OFF() {
		// Switch off Cryo cooler
		System.out.println(" Cryo Cooler off !");
		// Switch off Funnel Piezo
		CMIN_FunnelPiezoOff();
		System.out.println("Funnel Piezo off !");
		// Switch off Samplecell Piezo
		CMIN_SamlecellPiezoOff();
		System.out.println("SamplePiezo off !");
		// Switch off Xray
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
		
		// create json from image
		JSONObject jsonObject = createJsonFromImage();
		// create a new file called XRDiffraction.json
		File file = new File("XrdDiffraction.json");
		// If file is not there, create a new file
		if (!file.exists()) {
			file.createNewFile();
		}
		// Write the json object into that file named "XRDiffraction.json"
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(jsonObject.toString());
		bw.close();
		
		return true;
	}
	
private  MediaPlayer playMusic() {
		// Create the media player and play the sound which is in the file Voice.mp3
		new JFXPanel();
		String bip = "Voice.mp3";
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
		
		BufferedImage originalImage = ImageIO.read(new File("xrayDiffraction.jpg"));
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