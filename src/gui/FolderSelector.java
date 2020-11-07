package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import io.ConfigIO;
import utils.FolderSearcher;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.KeyEvent;
import java.awt.Window.Type;

public class FolderSelector extends JFrame {

	private JPanel contentPane;
	public JTextField tfXMLFolder;
	public JTextField tfDBfolder;
	public JButton btnBack;
	public JButton btnCancel;
	public JButton btnFinish;
	public JButton btnFindXML;
	public JButton btnAutoFolderXML;
	public JButton btnAutoFolderDB;
	public JLabel lblAutoFolderXML;
	public JButton btnFindDataBase;
	public JLabel lblAutoFolderDB;
	private JTextField tfDelay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//FolderSelector frame = new FolderSelector();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FolderSelector(int arPoint, boolean fromTrayBar) throws Exception {
		ConfigIO configIO = new ConfigIO();
		
		setResizable(false);
		setTitle("Cadastro das Pastas de Acesso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastroDasPastas = new JLabel("Cadastro das Pastas de Acesso");
		lblCadastroDasPastas.setBounds(10, 11, 202, 14);
		contentPane.add(lblCadastroDasPastas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 424, 2);
		contentPane.add(separator);
		
		JLabel lblPastaDeEntrada = new JLabel("Pasta de Entrada dos XML");
		lblPastaDeEntrada.setBounds(10, 49, 245, 14);
		contentPane.add(lblPastaDeEntrada);
		
		JLabel lblPesquisaManual = new JLabel("Pesquisa Manual");
		lblPesquisaManual.setBounds(20, 74, 192, 14);
		contentPane.add(lblPesquisaManual);
		
		tfXMLFolder = new JTextField();
		tfXMLFolder.setEditable(false);
		tfXMLFolder.setBounds(10, 99, 325, 20);
		contentPane.add(tfXMLFolder);
		tfXMLFolder.setColumns(10);
		
		btnFindXML = new JButton("Encontrar");
		btnFindXML.setBounds(345, 98, 89, 23);
		contentPane.add(btnFindXML);
		
		JLabel lblPesquisaAutomatica = new JLabel("Pesquisa Automatica");
		lblPesquisaAutomatica.setBounds(20, 130, 221, 14);
		contentPane.add(lblPesquisaAutomatica);
		
		btnAutoFolderXML = new JButton("Pesquisa Automatica");
		btnAutoFolderXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAutoFolderXML.setBounds(10, 155, 202, 23);
		contentPane.add(btnAutoFolderXML);
		
		lblAutoFolderXML = new JLabel("Em espera");
		lblAutoFolderXML.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoFolderXML.setBounds(222, 159, 212, 14);
		contentPane.add(lblAutoFolderXML);
		
		btnAutoFolderDB = new JButton("Pesquisa Automatica");
		btnAutoFolderDB.setBounds(10, 308, 202, 23);
		contentPane.add(btnAutoFolderDB);
		
		JLabel lblPesquisaAutomatica_1 = new JLabel("Pesquisa Automatica");
		lblPesquisaAutomatica_1.setBounds(20, 283, 192, 14);
		contentPane.add(lblPesquisaAutomatica_1);
		
		tfDBfolder = new JTextField();
		tfDBfolder.setEditable(false);
		tfDBfolder.setColumns(10);
		tfDBfolder.setBounds(10, 252, 325, 20);
		contentPane.add(tfDBfolder);
		
		JLabel lblPesquisaManual_1 = new JLabel("Pesquisa Manual");
		lblPesquisaManual_1.setBounds(20, 227, 221, 14);
		contentPane.add(lblPesquisaManual_1);
		
		JLabel lblPastaDoBanco = new JLabel("Pasta do Banco de Dados da Sebrae");
		lblPastaDoBanco.setBounds(10, 202, 325, 14);
		contentPane.add(lblPastaDoBanco);
		
		btnFindDataBase = new JButton("Encontrar");
		btnFindDataBase.setBounds(345, 251, 89, 23);
		contentPane.add(btnFindDataBase);
		
		lblAutoFolderDB = new JLabel("Em espera");
		lblAutoFolderDB.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoFolderDB.setBounds(222, 312, 212, 14);
		contentPane.add(lblAutoFolderDB);
		
		btnFinish = new JButton("Finalizar");
		btnFinish.setBounds(345, 388, 89, 23);
		contentPane.add(btnFinish);
		
		btnBack = new JButton("Voltar");
		btnBack.setBounds(246, 388, 89, 23);
		contentPane.add(btnBack);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(10, 388, 89, 23);
		contentPane.add(btnCancel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(67, 189, 281, 2);
		contentPane.add(separator_1);
		

		//Initialize the line edits
		tfXMLFolder.setText(configIO.generalInfo.getXMLFolder());
		tfDBfolder.setText(configIO.generalInfo.getDBFolder());
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(67, 342, 281, 2);
		contentPane.add(separator_1_1);
		
		JLabel lblDelayDeAcesso = new JLabel("Delay de Acesso");
		lblDelayDeAcesso.setBounds(20, 355, 89, 14);
		contentPane.add(lblDelayDeAcesso);
		
		tfDelay = new JTextField();
		tfDelay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) throws NumberFormatException {
				try {
					int i = Integer.parseInt(String.valueOf(e.getKeyChar()));
					//configIO.set("tfDelay", String.valueOf(i));
				} 
				catch (Exception e1) {
					if(!tfDelay.getText().isEmpty()) {
						String i = tfDelay.getText().substring(0, tfDelay.getText().length() - 1);
						tfDelay.setText(i);
						//try {
						//	configIO.set("tfDelay", i);
						//} catch (Exception e2) {
						//	// TODO Auto-generated catch block
						//	e2.printStackTrace();
						//}
					}
				}
			}
		});
		tfDelay.setToolTipText("Usualmente 5 segundos");
		tfDelay.setText(String.valueOf(configIO.generalInfo.getDelay()));
		tfDelay.setBounds(126, 352, 39, 20);
		contentPane.add(tfDelay);
		tfDelay.setColumns(10);
		
		JLabel lblSegundos = new JLabel("Segundos");
		lblSegundos.setBounds(175, 355, 89, 14);
		contentPane.add(lblSegundos);
		
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				CompanyRegister companyRegister = null;
				try {
					companyRegister = new CompanyRegister(arPoint, false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				companyRegister.setLocationRelativeTo(null);
				companyRegister.setVisible(true);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnFindXML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				java.io.File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				
				tfXMLFolder.setText(filename);
				//try {
				//	configIO.set("tfXMLFolder", tfXMLFolder.getText());
				//} catch (Exception e1) {
				//	// TODO Auto-generated catch block
				//	e1.printStackTrace();
				//}
			}
		});
		
		btnFindDataBase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				java.io.File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				
				tfDBfolder.setText(filename);
				//try {
				//	configIO.set("tfDBfolder", tfDBfolder.getText());
				//} catch (Exception e1) {
				//	// TODO Auto-generated catch block
				//	e1.printStackTrace();
				//}
			}
		});
		
		FolderSearcher folderSearcher = new FolderSearcher();
		ArrayList<String> drivers = folderSearcher.getWindowsDrives();
		
		btnAutoFolderXML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblAutoFolderXML.setText("Procurando...");
				btnAutoFolderXML.setEnabled(false);
				btnFindXML.setEnabled(false);
				btnFindDataBase.setEnabled(false);
				btnAutoFolderDB.setEnabled(false);
				btnBack.setEnabled(false);
				btnFinish.setEnabled(false);
				
				new Thread(() -> {
					ArrayList<String> xmlPathResults = new ArrayList<String>();
					for (String drive:drivers) {
						xmlPathResults.add(folderSearcher.getFolderIfExist(drive, 2, "\\CPlusNFe\\Envio\\RecepcaoNFe\\Pendente"));
					}
					
					boolean finded = false;
					
					for(String path:xmlPathResults) {
						if( !(path == null || path.length() == 0) ) {
							finded = true;
							lblAutoFolderXML.setText("Encontrado!");
							tfXMLFolder.setText(path);
							//try {
							//	configIO.set("tfXMLFolder", tfXMLFolder.getText());
							//} catch (Exception e1) {
							//	// TODO Auto-generated catch block
							//	e1.printStackTrace();
							//}
						}
					}
					
					if(!finded) {
						lblAutoFolderXML.setText("Pasta não encontrada, tente manualmente");
					}
					
					btnAutoFolderXML.setEnabled(true);
					btnFindXML.setEnabled(true);
					btnFindDataBase.setEnabled(true);
					btnAutoFolderDB.setEnabled(true);
					btnBack.setEnabled(true);
					btnFinish.setEnabled(true);
				}).start();
			}
		});
		
		btnAutoFolderDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblAutoFolderDB.setText("Procurando...");
				btnAutoFolderXML.setEnabled(false);
				btnFindXML.setEnabled(false);
				btnFindDataBase.setEnabled(false);
				btnAutoFolderDB.setEnabled(false);
				btnBack.setEnabled(false);
				btnFinish.setEnabled(false);
				
				new Thread(() -> {
					ArrayList<String> xmlPathResults = new ArrayList<String>();
					for (String drive:drivers) {
						xmlPathResults.add(folderSearcher.getFolderIfExist(drive, 2, "\\database\\NFE_400"));
					}
					
					boolean finded = false;
					
					for(String path:xmlPathResults) {
						if( !(path == null || path.length() == 0) ) {
							finded = true;
							lblAutoFolderDB.setText("Encontrado!");
							tfDBfolder.setText(path);
							//try {
							//	configIO.set("tfDBfolder", tfDBfolder.getText());
							//} catch (Exception e1) {
								// TODO Auto-generated catch block
							//	e1.printStackTrace();
							//}
						}
					}
					
					if(!finded) {
						lblAutoFolderDB.setText("Pasta não encontrada, tente manualmente");
					}
					
					btnAutoFolderXML.setEnabled(true);
					btnFindXML.setEnabled(true);
					btnFindDataBase.setEnabled(true);
					btnAutoFolderDB.setEnabled(true);
					btnBack.setEnabled(true);
					btnFinish.setEnabled(true);
				}).start();
			}
		});
		
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tfXMLFolder.getText().isEmpty() && !tfDBfolder.getText().isEmpty() && !tfDelay.getText().isEmpty()) {
					setVisible(false);
					
					try {
						configIO.getConfig();
						configIO.generalInfo.setXMLFolder(tfXMLFolder.getText());
						configIO.generalInfo.setDBFolder(tfDBfolder.getText());
						configIO.generalInfo.setDelay(Integer.valueOf(tfDelay.getText()));
						configIO.generalInfo.setFirstInit(false);
						configIO.saveConfig();
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//if(!configIO.get("firstInit").isEmpty()) {						
					//	return;
					//}
					//try {
					//	configIO.set("firstInit", "1");
					//} 
					//catch (Exception e1) {
						// TODO Auto-generated catch block
					//	e1.printStackTrace();
					//}
					setVisible(false);
					if(!fromTrayBar) {
						try {
							TrayBar trayBar = new TrayBar();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		if(fromTrayBar) {
			btnCancel.setVisible(false);
		}
	}
}
