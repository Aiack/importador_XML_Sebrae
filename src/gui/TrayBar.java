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
					CompanyList companyList = null;
					try {
						companyList = new CompanyList();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					companyList.setLocationRelativeTo(null);
					companyList.setVisible(true);
				}
			};
			
			ActionListener forceExecution = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						configIO.getConfig();
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
			
			ActionListener folderSelectorAction = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					FolderSelector folderSelector = null;
					try {
						folderSelector = new FolderSelector(0, true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					folderSelector.setLocationRelativeTo(null);
					folderSelector.setVisible(true);
					
				}
			};
			
			PopupMenu popup = new PopupMenu("Menu de Opções");
			
			MenuItem fExecution = new MenuItem("Forçar Execução");
			pauseExecution = new CheckboxMenuItem("Pausar Execução");
			MenuItem sCompany = new MenuItem("Abrir Listagem de Empresa");
			MenuItem sfolders = new MenuItem("Abrir Configuração das Pastas");
			MenuItem about = new MenuItem("Sobre");
			
			MenuItem defaultItem = new MenuItem("Sair");
			defaultItem.addActionListener(exitListener);
			sCompany.addActionListener(companyScreen);
			fExecution.addActionListener(forceExecution);
			about.addActionListener(aboutScreen);
			sfolders.addActionListener(folderSelectorAction);
			
			popup.add(fExecution);
			popup.add(pauseExecution);
			popup.addSeparator();
			popup.add(sCompany);
			popup.add(sfolders);
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
		    			configIO.getConfig();
		    			String delay = String.valueOf(configIO.generalInfo.getDelay());
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
		String XMLFolder = configIO.generalInfo.getXMLFolder();
		String DBFolder = configIO.generalInfo.getDBFolder();
		File path = new File(XMLFolder);
		File[] filesList = path.listFiles();
		for (File file : filesList) {
			if(file.getName().endsWith(".xml")) {
				try {
					new XMLToDBPipeline(file.getAbsolutePath(), DBFolder);
					supressWarning = false;
					file.delete();
					trayIcon.displayMessage("Importador","Nota Fiscal Processada Com Sucesso!",TrayIcon.MessageType.INFO);
				} catch (Exception e) {
					String dupliError = "Nota Fiscal já cadastrado no banco de dados";
					String openedConnection = "Conexão do DB sob acesso, feche o sistema da SEBRAE";
					String certA3Error = "Erro ao processar nota com certificado A3, nota inserida como EM DIGITAÇÂO";
					
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
					else if (e.getMessage().equals(certA3Error)) {
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
