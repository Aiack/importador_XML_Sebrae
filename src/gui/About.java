package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.SwingConstants;

public class About extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setResizable(false);
		setTitle("Sobre");
		setBounds(100, 100, 450, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("12-08-2020");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 125, 414, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblCriadoPorJhelison = new JLabel("Criado por Jhelison Gabriel Lima Uchoa");
		lblCriadoPorJhelison.setHorizontalAlignment(SwingConstants.CENTER);
		lblCriadoPorJhelison.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCriadoPorJhelison.setBounds(10, 11, 414, 19);
		contentPane.add(lblCriadoPorJhelison);
		
		JLabel lblJhelisonghotmailcom = new JLabel("jhelisong@hotmail.com");
		lblJhelisonghotmailcom.setHorizontalAlignment(SwingConstants.CENTER);
		lblJhelisonghotmailcom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblJhelisonghotmailcom.setBounds(10, 41, 414, 19);
		contentPane.add(lblJhelisonghotmailcom);
		
		JLabel lblVerso = new JLabel("Vers\u00E3o 1.0");
		lblVerso.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerso.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVerso.setBounds(10, 71, 414, 43);
		contentPane.add(lblVerso);
	}
}
