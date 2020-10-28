package gui;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.io.File;

import javax.swing.JOptionPane;

import XMLPipe.XMLToDBPipeline;
import io.ConfigIO;

public class TrayBar {
	TrayIcon trayIcon = null;
	
	ConfigIO configIO = new ConfigIO();
	
	boolean supressWarning = false;
	
	private CheckboxMenuItem pauseExecution;
	
	public TrayBar() throws Exception{
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage("tray.png");
			
			ActionListener exitListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,"Saindo");
					System.exit(0);
				}
			};
			
			ActionListener companyScreen = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CompanyRegister companyRegister = null;
					try {
						companyRegister = new CompanyRegister();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					companyRegister.setLocationRelativeTo(null);
					companyRegister.setVisible(true);
				}
			};
			
			ActionListener forceExecution = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						configIO.updateConfig();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainDBprocess();
				}
			};
			
			ActionListener aboutScreen = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					About aboutJpanel =  new About();
					aboutJpanel.setLocationRelativeTo(null);
					aboutJpanel.setVisible(true);
					
				}
			};
			
			PopupMenu popup = new PopupMenu("Menu de Opções");
			
			MenuItem fExecution = new MenuItem("Forçar Execução");
			pauseExecution = new CheckboxMenuItem("Pausar Execução");
			MenuItem sCompany = new MenuItem("Abrir Configurações");
			MenuItem about = new MenuItem("Sobre");
			
			MenuItem defaultItem = new MenuItem("Sair");
			defaultItem.addActionListener(exitListener);
			sCompany.addActionListener(companyScreen);
			fExecution.addActionListener(forceExecution);
			about.addActionListener(aboutScreen);
			
			popup.add(fExecution);
			popup.add(pauseExecution);
			popup.addSeparator();
			popup.add(sCompany);
			popup.addSeparator();
			popup.add(about);
			popup.addSeparator();
			popup.add(defaultItem);
			
			trayIcon = new TrayIcon(image, "Importador de XML para SEBRAE", popup);

			trayIcon.setImageAutoSize(true);
			
						
		    try {
		        tray.add(trayIcon);
		    } 
		    catch (AWTException e) {
		        System.err.println("Erro, TrayIcon não sera adicionado.");
		    }
		    
		    trayIcon.displayMessage("Importador","Importador de NF inicializado",TrayIcon.MessageType.INFO);
		    
		    new Thread(() -> {
		    	while(true) {
		    		try {
		    			configIO.updateConfig();
		    			String delay = configIO.get("tfDelay");
		    			int d;
		    			if(delay.isEmpty()) {
		    				d = 5;
		    			}
		    			else {
		    				d = Integer.parseInt(delay);
		    			}
						
						Thread.sleep(d * 1000);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		if(!pauseExecution.getState()) {
			    		mainDBprocess();
		    		}
		    	}
		    }).start();
		}
		else {
			JOptionPane.showMessageDialog(null,"recurso ainda não esta disponível pra o seu sistema");
		}		
	}
	
	private void mainDBprocess() {
		File path = new File(configIO.get("tfXMLFolder"));
		File[] filesList = path.listFiles();
		for (File file : filesList) {
			if(file.getName().endsWith(".xml")) {
				try {
					new XMLToDBPipeline(file.getAbsolutePath(), configIO.get("tfCertFolder"), configIO.get("txtFilePassword"), configIO.get("tfDBfolder"));
					supressWarning = false;
					file.delete();
					trayIcon.displayMessage("Importador","Nota Fiscal Processada Com Sucesso!",TrayIcon.MessageType.INFO);
				} catch (Exception e) {
					String dupliError = "Nota Fiscal já cadastrado no banco de dados";
					String openedConnection = "Conexão do DB sob acesso, feche o sistema da SEBRAE";
					
					if(e.getMessage().equals(openedConnection)) {
						if(supressWarning) {
							return;
						}
						else {
							supressWarning = true;
						}
					}
					else if (e.getMessage().equals(dupliError)) {
						file.delete();
					}
					else {
						pauseExecution.setState(true);
					}
					
					trayIcon.displayMessage("Importador",e.getMessage(),TrayIcon.MessageType.ERROR);
				}
			}
		}
	}
}
