package CheMin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import generic.RoverClientRunnable;
import generic.RoverServerRunnable;
import generic.RoverServerSocket;
import generic.RoverSocket;
import generic.RoverThread;
import generic.RoverThreadHandler;

import json.Constants;
import json.GlobalReader;
import json.MyWriter;

import java.util.Base64;
import java.awt.image.BufferedImage;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;

import Power.Power_Server;
import Telecommunication.Telecom_Server;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

//main
public class CheMinMain {

	public static void main(String[] args) {
		
		
		int port_chemin = 9008;
		
		try {
			
			// Create a thread for CHEMIN SERVER one
			CHEMIN_Server serverChemin = new CHEMIN_Server(port_chemin);
			Thread server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverChemin);
			server_chemin.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		Power_Server ps=null;
		try {
			ps = new Power_Server(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	
		Telecom_Server ts=null;
		try {
			ts = new Telecom_Server(9002);
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

	@Override
	public void run() {
		
		try {
			
			while (true) {
				
				System.out.println("CHEMIN Server -> Waiting for Request");
				
				// creating socket and waiting for client connection or for CCU
				getRoverServerSocket().openSocket();
				
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				Thread t=Thread.currentThread();
				t.sleep(7000);
				System.out.println("Message Received is: "+message);
				if(t.isInterrupted()){
					message = "power off";
				}
				
				switch(message.toLowerCase()){
				case "chemin_on":
					ccuYes = true;
					System.out.println("CCU has send request to CHEMIN to turn on");
					System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
					CHEMIN_Client client=new CHEMIN_Client(9013,null);
					Thread power=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
					power.start();
					break;
				
				case "power on":
					
					boolean process = false;
					if(ccuYes){
						CHEMIN chemin = new CHEMIN();
						process=chemin.CHEMIN_Process();
					}else{
						System.out.println(" CHEMIN dont have permission from CCU ,Yet !");
					}
						
					if(process){
						CHEMIN_Client teleclient=new CHEMIN_Client(9002,null);
						Thread telecommunication=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
						telecommunication.start();
						
					}
					
					break;
				
				case "power off":
					System.out.println(" Deleting Thread ..");
					new CHEMIN().CHEMIN_POWER_OFF();
					break;
				}
				// close resources
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
			
			if(getRoverSocket().getSocket().getPort()==9013){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				System.out.println("CHEMIN-->Power roup");
				System.out.println("CHEMIN-->Sending you my requirements in a json file");
				GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"PowerRequirement");
				JSONObject json= gr.getJSONObject();
				outstr.writeObject(json);
			}
				
				if(getRoverSocket().getSocket().getPort()==9002){
					System.out.println("contacting to tele success");
					ObjectOutputStream ostr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
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

}

class CHEMIN {
	private Thread t = null;
	
	boolean CHEMIN_Process() throws InterruptedException, IOException{
		t=Thread.currentThread();
		System.out.println("CHEMIN Process Started:");
		t.sleep(2000);
		if(CMIN_RemoveFunnelContamination()){
			System.out.println("\t\tCryo Cooler On");
			t.sleep(5000);
			System.out.println("\t\t\tRemoving Funnel contamination");
			t.sleep(2000);
			if(CMIN_CheckCHIMRA()){
				System.out.println("\t\t\t\tChecking if CHIMRA has Sample or not");
				t.sleep(2000);
				if(CMIN_RemoveSampleCellContamination()){
					System.out.println("\t\t\t\t\t\tCleaning Sample cell ");
					t.sleep(2000);
					if(CMIN_FunnelPiezoOn()){
						System.out.println("\t\t\t\t\t\tFunnel piezo is on now");
							playMusic();
						System.out.println("\t\t\t\t\t\t\t\tWait for 5 seconds:for sample to go in sample cell");
						t.sleep(5000);
						System.out.println("\t\t\t\t\t\t\t\t\t\tSample is in sample cell now");
						
						CMIN_SamlecellPiezoOn();
						playMusic();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tSample cell piezo Started");
						CMIN_XrayOn();
						System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tXrays are on now");
						t.sleep(10000);
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
	
	public boolean CMIN_RemoveSampleCellContamination(){   //for dilution
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

		File file = new File("XrdDiffraction.json");

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
	}