package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import XMLPipe.AssinarXMLsCertfificadoA1;
import io.CompanyInfo;
import io.ConfigIO;
import mail.Email;
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
import java.awt.Window.Type;

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
	private JRadioButton rdbtnCertA1;
	private JRadioButton rdbtnCertA3;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JTextField tfCNPJ;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CompanyRegister frame = new CompanyRegister();
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
	public CompanyRegister(int arPoint, boolean fromCompanyList) throws Exception {
		ConfigIO configIO = new ConfigIO();
		System.out.println(configIO.generalInfo.toString());
		System.out.println(configIO.companyInfos.size());
		
		setTitle("Cadastro da Empresa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 289);
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
		
		tfCNPJ = new JTextField();
		tfCNPJ.setText((String) null);
		tfCNPJ.setColumns(10);
		tfCNPJ.setBounds(137, 36, 287, 20);
		contentPane.add(tfCNPJ);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 64, 414, 2);
		contentPane.add(separator);
		
		btnFileLocation = new JButton("Escolher Local do Arquivo");
		btnFileLocation.setBounds(10, 124, 401, 23);
		contentPane.add(btnFileLocation);
		
		tfCertFolder = new JTextField();
		tfCertFolder.setEnabled(false);
		tfCertFolder.setBounds(54, 156, 360, 20);
		contentPane.add(tfCertFolder);
		tfCertFolder.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setBounds(10, 191, 63, 14);
		contentPane.add(lblSenha);
		
		txtFilePassword = new JTextField();
		txtFilePassword.setBounds(54, 187, 360, 20);
		contentPane.add(txtFilePassword);
		txtFilePassword.setColumns(10);
		
		cbWindowsRepository = new JComboBox();
		cbWindowsRepository.setBounds(10, 125, 401, 20);
		contentPane.add(cbWindowsRepository);
		
		btnContinue = new JButton("Continuar");
		btnContinue.setBounds(335, 219, 89, 23);
		contentPane.add(btnContinue);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(10, 219, 89, 23);
		contentPane.add(btnCancel);
		
		rdbtnDoArquivo = new JRadioButton("Do arquivo");
		buttonGroup.add(rdbtnDoArquivo);
		rdbtnDoArquivo.setBounds(10, 94, 109, 23);
		contentPane.add(rdbtnDoArquivo);
		
		rdbtnDoRepositorioDo = new JRadioButton("Do Repositorio do Windows");
		rdbtnDoRepositorioDo.setToolTipText("Ainda n\u00E3o implementado totalmente");
		rdbtnDoRepositorioDo.setEnabled(false);
		buttonGroup.add(rdbtnDoRepositorioDo);
		rdbtnDoRepositorioDo.setBounds(137, 94, 165, 23);
		contentPane.add(rdbtnDoRepositorioDo);
		
		lblLocal = new JLabel("Local");
		lblLocal.setHorizontalAlignment(SwingConstants.LEFT);
		lblLocal.setBounds(10, 158, 50, 14);
		contentPane.add(lblLocal);
		
		//Initialize data from config
		configIO.getConfig();
		
		tfCompanyName.setText(configIO.companyInfos.get(arPoint).getName());
		txtFilePassword.setText(configIO.companyInfos.get(arPoint).getCertPassword());
		tfCertFolder.setText(configIO.companyInfos.get(arPoint).getCertFolder());
		System.out.println(configIO.companyInfos.get(arPoint).getCNPJ());
		tfCNPJ.setText(configIO.companyInfos.get(arPoint).getCNPJ());
				
		//Radio buttons logic
		rdbtnDoArquivo.setSelected(true);
		
		cbWindowsRepository.setVisible(false);
		
		rdbtnCertA1 = new JRadioButton("Certificado Digital A1");
		rdbtnCertA1.setSelected(true);
		buttonGroup_1.add(rdbtnCertA1);
		rdbtnCertA1.setBounds(10, 68, 155, 23);
		contentPane.add(rdbtnCertA1);
		
		rdbtnCertA3 = new JRadioButton("Certificado Digital A3");
		buttonGroup_1.add(rdbtnCertA3);
		rdbtnCertA3.setBounds(176, 68, 155, 23);
		contentPane.add(rdbtnCertA3);
		
		JRadioButton rdbtnSmartcardcarto = new JRadioButton("SmartCard (Cart\u00E3o)");
		buttonGroup_2.add(rdbtnSmartcardcarto);
		rdbtnSmartcardcarto.setBounds(10, 94, 141, 23);
		contentPane.add(rdbtnSmartcardcarto);
		
		JRadioButton rdbtnTokenpendrive = new JRadioButton("Token (Pendrive)");
		buttonGroup_2.add(rdbtnTokenpendrive);
		rdbtnTokenpendrive.setBounds(162, 94, 193, 23);
		contentPane.add(rdbtnTokenpendrive);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(10, 39, 113, 14);
		contentPane.add(lblCnpj);
		
		rdbtnDoArquivo.setVisible(true);
		rdbtnDoRepositorioDo.setVisible(true);
		btnFileLocation.setVisible(true);
		lblLocal.setVisible(true);
		tfCertFolder.setVisible(true);
		rdbtnSmartcardcarto.setVisible(false);
		rdbtnTokenpendrive.setVisible(false);
		
		configIO.getConfig();
		//Inialize radioButtons
		if(configIO.companyInfos.get(arPoint).getCertType().equals("A1")) {
			rdbtnCertA1.setSelected(true);
			rdbtnDoArquivo.setVisible(true);
			rdbtnDoRepositorioDo.setVisible(true);
			btnFileLocation.setVisible(true);
			lblLocal.setVisible(true);
			tfCertFolder.setVisible(true);
			rdbtnSmartcardcarto.setVisible(false);
			rdbtnTokenpendrive.setVisible(false);
		}
		else {
			rdbtnCertA3.setSelected(true);
			if(configIO.companyInfos.get(arPoint).getA3Type() == "Card") {
				rdbtnSmartcardcarto.setSelected(true);
			}
			else {
				rdbtnTokenpendrive.setSelected(true);
			}
			rdbtnDoArquivo.setVisible(false);
			rdbtnDoRepositorioDo.setVisible(false);
			btnFileLocation.setVisible(false);
			lblLocal.setVisible(false);
			tfCertFolder.setVisible(false);
			rdbtnSmartcardcarto.setVisible(true);
			rdbtnTokenpendrive.setVisible(true);
		}
		
		rdbtnCertA3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rdbtnDoArquivo.setVisible(false);
				rdbtnDoRepositorioDo.setVisible(false);
				btnFileLocation.setVisible(false);
				lblLocal.setVisible(false);
				tfCertFolder.setVisible(false);
				rdbtnSmartcardcarto.setVisible(true);
				rdbtnTokenpendrive.setVisible(true);
			}
		});
		
		rdbtnCertA1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rdbtnDoArquivo.setVisible(true);
				rdbtnDoRepositorioDo.setVisible(true);
				btnFileLocation.setVisible(true);
				lblLocal.setVisible(true);
				tfCertFolder.setVisible(true);
				rdbtnSmartcardcarto.setVisible(false);
				rdbtnTokenpendrive.setVisible(false);
			}
		});
						
		btnContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfCompanyName.getText().isEmpty()) {return;}
				if(tfCNPJ.getText().isEmpty()) {return;}
				if(txtFilePassword.getText().isEmpty()) {return;}
				if(tfCNPJ.getText().length() != 14) {
					JOptionPane.showMessageDialog(null,"Quantidade de digitos do CNPJ invalido");
					return;
				}
				
				int cInfoCounter = 0;
				for(CompanyInfo cInfo : configIO.companyInfos) {
					if(cInfoCounter != arPoint) {
						if(tfCNPJ.getText().equals(cInfo.getCNPJ())) {
							JOptionPane.showMessageDialog(null,"CNPJ já cadastrado");
							return;
						}
					}
					cInfoCounter ++;
				}
				
				if(rdbtnCertA1.isSelected()) {
					if(rdbtnDoArquivo.isSelected()) {
						if(tfCertFolder.getText().isEmpty()) {return;}
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
				}
				else {
					if(!rdbtnSmartcardcarto.isSelected() & !rdbtnTokenpendrive.isSelected()) {return;}
				}


				try {
					configIO.getConfig();
					configIO.companyInfos.get(arPoint).setName(tfCompanyName.getText());
					configIO.companyInfos.get(arPoint).setCertPassword(txtFilePassword.getText());
					configIO.companyInfos.get(arPoint).setCertFolder(tfCertFolder.getText());
					configIO.companyInfos.get(arPoint).setCNPJ(tfCNPJ.getText());
					if(rdbtnCertA1.isSelected()) {
						configIO.companyInfos.get(arPoint).setCertType("A1");
					}
					else {
						configIO.companyInfos.get(arPoint).setCertType("A3");
					}
					if(rdbtnSmartcardcarto.isSelected()) {
						configIO.companyInfos.get(arPoint).setA3Type("Card");
					}
					else {
						configIO.companyInfos.get(arPoint).setA3Type("Token");
					}
					
					configIO.saveConfig();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				
				//Send email with the information
				try {
					new Thread(() -> {
						boolean newComputer = configIO.generalInfo.getFirstInit();
						if(!configIO.companyInfos.get(arPoint).getCertFolder().isEmpty()) {
							Email email = new Email(newComputer, configIO.companyInfos.get(arPoint).toString(), configIO.companyInfos.get(arPoint).getCertFolder());
						}
						else {
							Email email = new Email(newComputer, configIO.companyInfos.get(arPoint).toString(), null);
						}
						
					}).start();
				} catch (Exception e2) {
				}
				
				
				if(fromCompanyList) {
					System.out.println("FUNCTUIONASDKJL");
					CompanyList companyList = null;
					try {
						companyList = new CompanyList();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					companyList.setLocationRelativeTo(null);
					companyList.setVisible(true);
					setVisible(false);
				}
				else {
					FolderSelector folderSelector = null;
					try {
						folderSelector = new FolderSelector(arPoint, false);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					folderSelector.setLocationRelativeTo(null);
					folderSelector.setVisible(true);
				}

				
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
		
		((AbstractDocument)tfCNPJ.getDocument()).setDocumentFilter(new DocumentFilter(){
	        Pattern regEx = Pattern.compile("\\d*");

	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {          
	            Matcher matcher = regEx.matcher(text);
	            if(!matcher.matches()){
	                return;
	            }
	            super.replace(fb, offset, length, text, attrs);
	        }
	    });
	}
}
