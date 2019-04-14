package GUI;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Logic.Bank;
import Logic.SavingAccount;
import Model.Account;
import Model.Person;

public class ViewOperation {

	private JFrame frame;
	private JTextField amount;
	private DefaultListModel<String> listModelPerson;
	private DefaultListModel<Integer> listModelAccount;
	private JList<String> listPerson;
	private JList<Integer> listAccount;
	private JButton deposit;
	private JButton withdrawal;
	JButton close;
	private JTextField presentAmount ;

	private Bank bank;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ViewOperation(final Bank bank) {

		this.bank = bank;

		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					FileOutputStream fileOut = new FileOutputStream("bank.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(bank);
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			}
		});

		JLabel amountLabel = new JLabel("amount");
		amountLabel.setBounds(430, 20, 170, 25);
		frame.getContentPane().add(amountLabel);

		amount = new JTextField();
		amount.setBounds(430, 60, 170, 25);
		frame.getContentPane().add(amount);
		amount.setText(null);
		
		JLabel presentAmountLabel = new JLabel("available amount");
		presentAmountLabel.setBounds(430, 100, 170, 25);
		frame.getContentPane().add(presentAmountLabel);

		presentAmount = new JTextField();
		presentAmount.setBounds(430, 140, 170, 25);
		frame.getContentPane().add(presentAmount);

		
		listModelPerson = new DefaultListModel();
		listModelPerson.addElement("Person");
		if (bank != null) {
			for (Person per : bank.getPerson()) {
				listModelPerson.addElement(per.getCNP());
			}
		}
		listPerson = new JList(listModelPerson);

		listPerson.setBounds(10, 20, 170, 250);
		frame.getContentPane().add(listPerson);

		deposit = new JButton("Deposit");
		deposit.setBounds(10, 300, 150, 25);
		frame.getContentPane().add(deposit);

		withdrawal = new JButton("Withdrawal");
		withdrawal.setBounds(180, 300, 150, 25);
		frame.getContentPane().add(withdrawal);

		close = new JButton("Close");
		close.setBounds(500, 400, 130, 25);
		frame.getContentPane().add(close);
		
		listModelAccount = new DefaultListModel<Integer>();
		listAccount = new JList<Integer>(listModelAccount);

		listAccount.setBounds(200, 20, 170, 250);
		frame.getContentPane().add(listAccount);
		listAccount.setVisible(false);
	}

	public void setVisible() {
		frame.setVisible(true);
	}

	public void setInvisible() {
		frame.setVisible(false);
		frame.dispose();
	}

	public void addBtnCloseListener(ActionListener e) {
		close.addActionListener(e);

	}

	public void addBtnDepListener(ActionListener e) {
		deposit.addActionListener(e);

	}

	public void addBtnWithListener(ActionListener e) {
		withdrawal.addActionListener(e);
	}

	public void addClickPersonListener(MouseListener m) {
		listPerson.addMouseListener(m);
	}
	public void addClickAccountListener(MouseListener m) {
		listAccount.addMouseListener(m);
	}

	public double getAmount() {
		return Double.parseDouble(amount.getText());
	}

	public Person getPerson() {
		for (Person per : bank.getPerson()) {
			if (per.getCNP().equals(listPerson.getSelectedValue())) {
				return per;
			}
		}
		return null;
	}

	public Account getAccount() {
		Set<Account> a = bank.getAccounts(getPerson());
		for (Account ac : a) {
			if (ac.getId() == listAccount.getSelectedValue()) {
				return ac;
			}
		}
		return null;
	}

	public void setAccounts(Person p) {
		
		Set<Account> ac = bank.getAccounts(p);
		if (ac != null) {
			for (Account a : ac) {
				listModelAccount.addElement(a.getId());
			}
			listAccount.setVisible(true);
		}
		
	}

	public void destroy() {
		frame.dispose();
	}
	
	public void setAmmount(Account a) {
		double am;
		if (a instanceof SavingAccount) {
			SavingAccount account = (SavingAccount) a;
			am = a.getAmount() + account.getInterest();
		} else {
			am = a.getAmount();
		}
		presentAmount.setText(String.valueOf(am));
	}
	
	public void errorMessage() {
		JOptionPane.showMessageDialog(frame, "Error", "Error", JOptionPane.ERROR_MESSAGE);
	}


}
