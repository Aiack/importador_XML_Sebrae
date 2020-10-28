package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.ConfigIO;
import utils.FolderSearcher;
import utils.WindowsRepo;


public class Register extends JFrame {
	private static final int PORT = 9999;
	private static ServerSocket socket;   
	
	public static void main(String[] contentPane) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Register frame = new Register();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Register() throws Exception {
		ConfigIO configIO = new ConfigIO();
		
		try {
			//Make sure only one instance is running at the same time
			socket = new ServerSocket(PORT,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
			
			if(configIO.get("firstInit").isEmpty()) {
				CompanyRegister companyRegister = new CompanyRegister();
				companyRegister.setLocationRelativeTo(null);
				companyRegister.setVisible(true);
			}
			else {
				TrayBar trayBar = new TrayBar();
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Uma instancia do programa já está aberta!");
		}
	}
}
