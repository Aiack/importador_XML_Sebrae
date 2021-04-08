package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import io.CompanyInfo;
import io.ConfigIO;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.spi.TimeZoneNameProvider;

public class CompanyList extends JFrame {

	private JPanel contentPane;
	
	private String[] columnNames = {"CNPJ", "Nome", "Tipo do certificado", "Data criação"};
	
	ConfigIO configIO = new ConfigIO();
	
	private String[][] data = {
			{"1", "Oriza Vieira Lima", "A1", "10-10-2020"},
			{"2", "José Raimundo Uchoa", "A2", "20-11-2021"}
	};

	 private TableModel model = new DefaultTableModel(configIO.getCompanyInfoList(), columnNames)
	  {
	    public boolean isCellEditable(int row, int column)
	    {
	      return false;//This causes all cells to be not editable
	    }
	  };
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyList frame = new CompanyList();
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
	public CompanyList() throws Exception {
		
		JTable table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setTitle("Lista de empresas");
		setBounds(100, 100, 636, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 5, 605, 389);
		contentPane.add(scrollPane);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(521, 405, 89, 23);
		contentPane.add(btnFinalizar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(5, 405, 89, 23);
		contentPane.add(btnRemover);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(203, 405, 89, 23);
		contentPane.add(btnAdicionar);				
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(104, 405, 89, 23);
		contentPane.add(btnEditar);
		
		btnFinalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				int selectedColumn = table.getSelectedRow();
				if(selectedColumn != -1) {
					System.out.println(selectedColumn);
					try {
						configIO.getConfig();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(configIO.companyInfos.size() == 1) {
						JOptionPane.showMessageDialog(null,"Pelo menos uma empresa deve estar cadastrada");
						return;
					}
					configIO.companyInfos.remove(selectedColumn);
					try {
						configIO.saveConfig();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.removeRow(selectedColumn);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					configIO.getConfig();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int lastIndex = configIO.companyInfos.size();
				configIO.companyInfos.add(new CompanyInfo("", "", "A1", "", "", ""));
				try {
					configIO.saveConfig();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					CompanyRegister companyRegister = new CompanyRegister(lastIndex, true);
					companyRegister.setLocationRelativeTo(null);
					companyRegister.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				if(selectedIndex != -1) {
					try {
						configIO.getConfig();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						CompanyRegister companyRegister = new CompanyRegister(selectedIndex, true);
						companyRegister.setLocationRelativeTo(null);
						companyRegister.setVisible(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
				}
			}
		});
	}
}
