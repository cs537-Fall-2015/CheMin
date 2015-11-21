package test_client_module;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.security.auth.callback.ChoiceCallback;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.awt.event.ActionEvent;

import json.Constants;

public class TestClientModuleMain {

private JFrame frmTestClient;
protected int flag = 0;

/**
* Launch the application.
*/
public static void main(String[] args) {
	
//int flag = 0;
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
TestClientModuleMain window = new TestClientModuleMain();
window.frmTestClient.setVisible(true);
} catch (Exception e) {
e.printStackTrace();
}
}
});
}

/**
* Create the application.
*/
public TestClientModuleMain() {
initialize();
}

/**
* Initialize the contents of the frame.
*/
private void initialize() {
frmTestClient = new JFrame();
frmTestClient.setTitle("Test client");
frmTestClient.setBounds(100, 100, 500, 500);
frmTestClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JComboBox settings_selection = new JComboBox();
settings_selection.setModel(new DefaultComboBoxModel(new String[] {"Run full process", "Manual selection"}));
frmTestClient.getContentPane().add(settings_selection, BorderLayout.NORTH);
JButton send_button = new JButton("Send commands to chemin");
JButton reset_button = new JButton("Reset");
frmTestClient.getContentPane().add(send_button, BorderLayout.CENTER);
frmTestClient.getContentPane().add(reset_button, BorderLayout.LINE_END);
JPanel commands_selection = new JPanel();
frmTestClient.getContentPane().add(commands_selection, BorderLayout.WEST);
JCheckBox chckbxNewCheckBox_1 = new JCheckBox("sample_receive");
JCheckBox chckbxNewCheckBox = new JCheckBox("xray_set_position");
JCheckBox chckbxNewCheckBox_2 = new JCheckBox("cell_next");
JCheckBox chckbxNewCheckBox_3 = new JCheckBox("cell_clean_current");
JCheckBox chckbxNewCheckBox_4 = new JCheckBox("inlet_open");
JCheckBox chckbxNewCheckBox_5 = new JCheckBox("inlet_close");
JCheckBox chckbxNewCheckBox_6 = new JCheckBox("xray_turn_on");
JCheckBox chckbxNewCheckBox_7 = new JCheckBox("analysis_start");
JCheckBox chckbxNewCheckBox_8 = new JCheckBox("cdd_create_diffraction_image");
JCheckBox chckbxNewCheckBox_9 = new JCheckBox("cdd_create_1d_2t_plot");
JCheckBox chckbxNewCheckBox_10 = new JCheckBox("send_results");
JCheckBox chckbxNewCheckBox_11 = new JCheckBox("power_off");
chckbxNewCheckBox.setEnabled(false);
chckbxNewCheckBox_1.setEnabled(false);
chckbxNewCheckBox_2.setEnabled(false);
chckbxNewCheckBox_3.setEnabled(false);
chckbxNewCheckBox_4.setEnabled(false);
chckbxNewCheckBox_5.setEnabled(false);
chckbxNewCheckBox_6.setEnabled(false);
chckbxNewCheckBox_7.setEnabled(false);
chckbxNewCheckBox_8.setEnabled(false);
chckbxNewCheckBox_9.setEnabled(false);
chckbxNewCheckBox_10.setEnabled(false);
chckbxNewCheckBox_11.setEnabled(false);
GroupLayout gl_commands_selection = new GroupLayout(commands_selection);
gl_commands_selection.setHorizontalGroup(
gl_commands_selection.createParallelGroup(Alignment.LEADING)
.addGroup(gl_commands_selection.createSequentialGroup()
.addContainerGap()
.addGroup(gl_commands_selection.createParallelGroup(Alignment.LEADING)
.addComponent(chckbxNewCheckBox_2)
.addComponent(chckbxNewCheckBox_3)
.addComponent(chckbxNewCheckBox_4)
.addComponent(chckbxNewCheckBox_5)
.addComponent(chckbxNewCheckBox_11)
.addComponent(chckbxNewCheckBox_6)
.addComponent(chckbxNewCheckBox_7)
.addComponent(chckbxNewCheckBox_8)
.addComponent(chckbxNewCheckBox_9)
.addComponent(chckbxNewCheckBox)
.addComponent(chckbxNewCheckBox_1)
.addComponent(chckbxNewCheckBox_10)
)
       .addContainerGap(121, Short.MAX_VALUE))
);
gl_commands_selection.setVerticalGroup(
gl_commands_selection.createParallelGroup(Alignment.LEADING)
.addGroup(gl_commands_selection.createSequentialGroup()
.addContainerGap()
.addComponent(chckbxNewCheckBox)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_1)
.addGap(1)
.addComponent(chckbxNewCheckBox_2)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_3)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_4)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_5)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_6)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_7)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_8)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_9)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_10)
.addPreferredGap(ComponentPlacement.UNRELATED)
.addComponent(chckbxNewCheckBox_11)
.addContainerGap(17, Short.MAX_VALUE))
);
commands_selection.setLayout(gl_commands_selection);
settings_selection.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
if(settings_selection.getSelectedItem()=="Run full process")
{
chckbxNewCheckBox.setEnabled(false);
chckbxNewCheckBox_1.setEnabled(false);
chckbxNewCheckBox_2.setEnabled(false);
chckbxNewCheckBox_3.setEnabled(false);
chckbxNewCheckBox_4.setEnabled(false);
chckbxNewCheckBox_5.setEnabled(false);
chckbxNewCheckBox_6.setEnabled(false);
chckbxNewCheckBox_7.setEnabled(false);
chckbxNewCheckBox_8.setEnabled(false);
chckbxNewCheckBox_9.setEnabled(false);
chckbxNewCheckBox_10.setEnabled(false);
chckbxNewCheckBox_11.setEnabled(false);
//fill .txt file
} else if(settings_selection.getSelectedItem()=="Manual selection")
{
chckbxNewCheckBox.setEnabled(true);
chckbxNewCheckBox_1.setEnabled(true);
chckbxNewCheckBox_2.setEnabled(true);
chckbxNewCheckBox_3.setEnabled(true);
chckbxNewCheckBox_4.setEnabled(true);
chckbxNewCheckBox_5.setEnabled(true);
chckbxNewCheckBox_6.setEnabled(true);
chckbxNewCheckBox_7.setEnabled(true);
chckbxNewCheckBox_8.setEnabled(true);
chckbxNewCheckBox_9.setEnabled(true);
chckbxNewCheckBox_10.setEnabled(true);
chckbxNewCheckBox_11.setEnabled(true);
//fill .txt file
}
}
});

reset_button.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Socket socket = null;
		try {
			
			
		
			socket = new Socket("localhost",9008);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectOutputStream outstr = null;
		try {
			outstr = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			outstr.writeObject("reset");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
});
send_button.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent arg0) {
//when send button is pressed, create txt command files and send command file path to chemin
///// .txt command file creation
String command_file_name = "command_file.txt";
PrintWriter writer = null;
try {
writer = new PrintWriter(Constants.ROOT_PATH+command_file_name, "UTF-8");
} catch (FileNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
if(settings_selection.getSelectedItem()=="Run full process")
{
//fill .txt file
writer.println("launch_Chemin_Process");
writer.close();
} else if(settings_selection.getSelectedItem()=="Manual selection")
{
//fill .txt file
if(chckbxNewCheckBox.isSelected())
{
writer.println("xray_set_position");
}
if(chckbxNewCheckBox_1.isSelected())
{
writer.println("sample_receive");
}
if(chckbxNewCheckBox_2.isSelected())
{
writer.println("cell_next");
}
if(chckbxNewCheckBox_3.isSelected())
{
writer.println("cell_clean_current");
}
if(chckbxNewCheckBox_4.isSelected())
{
writer.println("inlet_open");
}
if(chckbxNewCheckBox_5.isSelected())
{
writer.println("inlet_close");
}
if(chckbxNewCheckBox_6.isSelected())
{
writer.println("xray_turn_on");
}
if(chckbxNewCheckBox_7.isSelected())
{
writer.println("analysis_start");
}
if(chckbxNewCheckBox_8.isSelected())
{
writer.println("cdd_create_diffraction_image");
}
if(chckbxNewCheckBox_9.isSelected())
{
writer.println("cdd_create_1d_2t_plot");
}
if(chckbxNewCheckBox_10.isSelected())
{
writer.println("send_results");
}
if(chckbxNewCheckBox_11.isSelected())
{
writer.print("");
writer.close();
try {
	writer = new PrintWriter(Constants.ROOT_PATH+command_file_name, "UTF-8");
} catch (FileNotFoundException | UnsupportedEncodingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
writer.println("power_off");
flag = 1;
}
writer.close();
}
Socket socket = null;
if(flag == 0) {
	try {
		
	socket = new Socket("localhost",9010);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
ObjectOutputStream outstr = null;
try {
	outstr = new ObjectOutputStream(socket.getOutputStream());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	
	outstr.writeObject(Constants.ROOT_PATH+command_file_name);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
} else {
	
try {
		
		
		flag = 0;
		socket = new Socket("localhost",9008);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ObjectOutputStream outstr = null;
	try {
		outstr = new ObjectOutputStream(socket.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		
		outstr.writeObject("power_off");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
	


}});

}
}

