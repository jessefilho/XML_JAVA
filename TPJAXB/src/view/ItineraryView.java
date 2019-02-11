package view;
import Controller.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;



@SuppressWarnings("serial")
public class ItineraryView extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPOI;
	private JTextField textFieldDesc;
	private JTextField textFieldDuration;
	private JTextField textFieldRating;
	private JTextField textFieldTheme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItineraryView frame = new ItineraryView();
					
					System.out.println("Hello");
					 
			        //frame.setSize(400, 400);
			        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	public ItineraryView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Theme Title");
		lblTitle.setBounds(25, 63, 95, 16);
		contentPane.add(lblTitle);
		
		JButton btnNewButton = new JButton("New Day");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Well Done!";
			    System.out.println(message);
			    
				
				ItineraryController itinerary = new ItineraryController(); 
				try {
					itinerary.insertItinararyDay(textFieldPOI.getText(),textFieldTheme.getText(),textFieldDesc.getText(),textFieldDuration.getText(),Integer.parseInt(textFieldRating.getText()));
				} catch (JAXBException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} catch (DatatypeConfigurationException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
			 
			    
			 
			    //JOptionPane.showMessageDialog(this, message);
			}
		});
		btnNewButton.setBounds(333, 243, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblPoiTitle = new JLabel("POI Title");
		lblPoiTitle.setBounds(25, 25, 61, 16);
		contentPane.add(lblPoiTitle);
		
		textFieldPOI = new JTextField();
		textFieldPOI.setBounds(132, 20, 130, 26);
		contentPane.add(textFieldPOI);
		textFieldPOI.setColumns(10);
		
		textFieldDesc = new JTextField();
		textFieldDesc.setBounds(132, 96, 130, 26);
		contentPane.add(textFieldDesc);
		textFieldDesc.setColumns(50);
		
		textFieldDuration = new JTextField();
		textFieldDuration.setBounds(132, 134, 130, 26);
		contentPane.add(textFieldDuration);
		textFieldDuration.setColumns(10);
		
		textFieldRating = new JTextField();
		textFieldRating.setBounds(132, 172, 130, 26);
		contentPane.add(textFieldRating);
		textFieldRating.setColumns(10);
		
		textFieldTheme = new JTextField();
		textFieldTheme.setBounds(132, 58, 130, 26);
		contentPane.add(textFieldTheme);
		textFieldTheme.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("description");
		lblNewLabel.setBounds(25, 101, 95, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Duration");
		lblNewLabel_1.setBounds(25, 139, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Rating");
		lblNewLabel_2.setBounds(25, 177, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		
		
		
	}
}
