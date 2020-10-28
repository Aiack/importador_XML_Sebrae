package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import XMLPipe.AssinarXMLsCertfificadoA1;
import io.ConfigIO;
import utils.WindowsRepo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class CompanyRegister extends JFrame {

	private JPanel contentPane;
	public JTextField tfCompanyName;
	public JTextField tfCertFolder;
	public JTextField txtFilePassword;
	public JButton btnContinue;
	public JButton btnCancel;
	public JButton btnFileLocation;
	public JComboBox cbWindowsRepository;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JRadioButton rdbtnDoArquivo;
	public JRadioButton rdbtnDoRepositorioDo;
	public JLabel lblLocal;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyRegister frame = new CompanyRegister();
					frame.setVisible(true);
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
	public CompanyRegister() throws Exception {
		ConfigIO configIO = new ConfigIO();
		
		setTitle("Cadastro da Empresa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeDaEmpresa = new JLabel("Nome da Empresa");
		lblNomeDaEmpresa.setBounds(10, 11, 113, 14);
		contentPane.add(lblNomeDaEmpresa);
		
		tfCompanyName = new JTextField();
		tfCompanyName.setBounds(137, 8, 287, 20);
		contentPane.add(tfCompanyName);
		tfCompanyName.setColumns(10);
		
		JLabel lblCertificadoDigitalA = new JLabel("Certificado Digital A1");
		lblCertificadoDigitalA.setBounds(10, 45, 140, 14);
		contentPane.add(lblCertificadoDigitalA);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 414, 2);
		contentPane.add(separator);
		
		btnFileLocation = new JButton("Escolher Local do Arquivo");
		btnFileLocation.setBounds(10, 96, 401, 23);
		contentPane.add(btnFileLocation);
		
		tfCertFolder = new JTextField();
		tfCertFolder.setEnabled(false);
		tfCertFolder.setBounds(54, 128, 360, 20);
		contentPane.add(tfCertFolder);
		tfCertFolder.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setBounds(10, 163, 63, 14);
		contentPane.add(lblSenha);
		
		txtFilePassword = new JTextField();
		txtFilePassword.setBounds(54, 159, 360, 20);
		contentPane.add(txtFilePassword);
		txtFilePassword.setColumns(10);
		
		cbWindowsRepository = new JComboBox();
		cbWindowsRepository.setBounds(10, 97, 401, 20);
		contentPane.add(cbWindowsRepository);
		
		btnContinue = new JButton("Continuar");
		btnContinue.setBounds(335, 191, 89, 23);
		contentPane.add(btnContinue);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(10, 191, 89, 23);
		contentPane.add(btnCancel);
		
		rdbtnDoArquivo = new JRadioButton("Do arquivo");
		buttonGroup.add(rdbtnDoArquivo);
		rdbtnDoArquivo.setBounds(10, 66, 109, 23);
		contentPane.add(rdbtnDoArquivo);
		
		rdbtnDoRepositorioDo = new JRadioButton("Do Repositorio do Windows");
		rdbtnDoRepositorioDo.setToolTipText("Ainda n\u00E3o implementado totalmente");
		rdbtnDoRepositorioDo.setEnabled(false);
		buttonGroup.add(rdbtnDoRepositorioDo);
		rdbtnDoRepositorioDo.setBounds(137, 66, 165, 23);
		contentPane.add(rdbtnDoRepositorioDo);
		
		lblLocal = new JLabel("Local");
		lblLocal.setHorizontalAlignment(SwingConstants.LEFT);
		lblLocal.setBounds(10, 130, 50, 14);
		contentPane.add(lblLocal);
		
		//Initialize data from config
		tfCompanyName.setText(configIO.get("tfCompanyName"));
		txtFilePassword.setText(configIO.get("txtFilePassword"));
		tfCertFolder.setText(configIO.get("tfCertFolder"));
				
		//Radio buttons logic
		rdbtnDoArquivo.setSelected(true);
		cbWindowsRepository.setVisible(false);
		
		//Inialize last radioButton
		if(configIO.get("rdbtn").contains("1")) {
			rdbtnDoRepositorioDo.setSelected(true);
			cbWindowsRepository.setVisible(true);
			lblLocal.setVisible(false);
			btnFileLocation.setVisible(false);
			tfCertFolder.setVisible(false);
		}
		
		rdbtnDoArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cbWindowsRepository.setVisible(false);
				lblLocal.setVisible(true);
				btnFileLocation.setVisible(true);
				tfCertFolder.setVisible(true);
				try {
					configIO.set("rdbtn", "0");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		rdbtnDoRepositorioDo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cbWindowsRepository.setVisible(true);
				lblLocal.setVisible(false);
				btnFileLocation.setVisible(false);
				tfCertFolder.setVisible(false);
				try {
					configIO.set("rdbtn", "1");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
				
		//Build the combobox with repo information
		WindowsRepo windowsRepo = new WindowsRepo();
		for(String repo : windowsRepo.getWindowsCert()) {
			cbWindowsRepository.addItem(repo);
		}
		for (int i=0; i<cbWindowsRepository.getItemCount(); i++) {
			if (cbWindowsRepository.getItemAt(i).toString().contains(configIO.get("cbWindowsRepository"))) {
				cbWindowsRepository.setSelectedIndex(i);
			}
		}
		
		btnContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfCompanyName.getText().isEmpty()) {return;}
				if(txtFilePassword.getText().isEmpty()) {return;}
				if(rdbtnDoArquivo.isSelected()) {
					if(tfCertFolder.getText().isEmpty()) {return;}
				}
				
				
				FolderSelector folderSelector = null;
				try {
					folderSelector = new FolderSelector();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(rdbtnDoArquivo.isSelected()) {
					try {
						AssinarXMLsCertfificadoA1 assinarXMLsCertfificadoA1 = new AssinarXMLsCertfificadoA1();
						assinarXMLsCertfificadoA1.checkCertificatePassword(tfCertFolder.getText(), txtFilePassword.getText());
					} 
					catch (Exception e2) {
						JOptionPane.showMessageDialog(null,e2);
						return;
					}
				}

				folderSelector.setLocationRelativeTo(null);
				folderSelector.setVisible(true);

				try {
					configIO.set("tfCompanyName", tfCompanyName.getText());
					configIO.set("txtFilePassword", txtFilePassword.getText());
					configIO.set("tfCertFolder", tfCertFolder.getText());
					configIO.set("cbWindowsRepository", (String) cbWindowsRepository.getSelectedItem());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnFileLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo de Certificado", new String[] {"pfx"});
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showOpenDialog(null);
				java.io.File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				
				tfCertFolder.setText(filename);
			}
		});
	}
}
