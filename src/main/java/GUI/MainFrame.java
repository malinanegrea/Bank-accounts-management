package GUI;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

import Logic.Bank;

public class MainFrame {

	JFrame frame;

	JButton viewPerson;
	JButton viewAccount;
	JButton operation;
	JButton close;

	public MainFrame(final Bank bank) {

		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 200);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				  try {
		             FileOutputStream fileOut =
		             new FileOutputStream("bank.ser");
		             ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(bank);
		             out.close();
		             fileOut.close();
		          } catch (IOException i) {
		             i.printStackTrace();
		          }
			  }
			});
		

		viewPerson = new JButton("Clients");
		viewPerson.setBounds(35, 35, 150, 25);
		frame.getContentPane().add(viewPerson);

		viewAccount = new JButton("Accounts");
		viewAccount.setBounds(210, 35, 150, 25);
		frame.getContentPane().add(viewAccount);
		
		operation = new JButton("Operations");
		operation.setBounds(35, 80, 150, 25);
		frame.getContentPane().add(operation);
		

		close = new JButton("Close");
		close.setBounds(210, 80, 150, 25);
		frame.getContentPane().add(close);

	}

	public void setVisible() {
		frame.setVisible(true);
	}

	public void setInvisible() {
		frame.setVisible(false);
	}

	public void addBtnCloseListener(ActionListener e) {
		close.addActionListener(e);
		
	}
	public void addBtnClientListener(ActionListener e) {
		viewPerson.addActionListener(e);
	}
	
	public void addBtnOperationListener(ActionListener e) {
		operation.addActionListener(e);
	}
	
	public void addBtnAccountListener(ActionListener e) {
		viewAccount.addActionListener(e);
		
	}
	 
	public void destroy() {
		frame.dispose();
	}
	
}
