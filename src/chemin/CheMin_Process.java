package chemin;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.util.Base64;
import java.awt.image.BufferedImage;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;

public class CheMin_Process {
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