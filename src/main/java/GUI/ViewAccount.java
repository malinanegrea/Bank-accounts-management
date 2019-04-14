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

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Logic.Bank;
import Logic.SavingAccount;
import Logic.SpandingAccount;
import Model.Account;
import Model.Person;

public class ViewAccount {

	JFrame frame;
	JTable table;
	JButton add;
	JButton delete;
	JButton update;
	JTextField id = null;
	JTextField amount = null;
	JButton close;
	DefaultTableModel model;
	JRadioButton saving;
	JRadioButton spanding;
	ButtonGroup group;
	private DefaultListModel<String> listModelPerson;
	private JList<String> listPerson;
	private Set<Person> p;

	public ViewAccount(final Bank bank) {

		this.p = bank.getPerson();

		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 500);
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

		table = new JTable();
		table.setBounds(10, 20, 400, 400);
		frame.getContentPane().add(table);

		JLabel idLabel = new JLabel("id");
		idLabel.setBounds(430, 20, 170, 25);
		frame.getContentPane().add(idLabel);

		id = new JTextField();
		id.setBounds(430, 60, 170, 25);
		frame.getContentPane().add(id);
		id.setText(null);

		JLabel amountLabel = new JLabel("amount");
		amountLabel.setBounds(430, 95, 170, 25);
		frame.getContentPane().add(amountLabel);

		amount = new JTextField();
		amount.setBounds(430, 130, 170, 25);
		frame.getContentPane().add(amount);
		amount.setText(null);

		JLabel CNPLabel = new JLabel("CNP");
		CNPLabel.setBounds(430, 165, 170, 25);
		frame.getContentPane().add(CNPLabel);

		saving = new JRadioButton("Saving Account");
		saving.setBounds(430, 200, 150, 25);
		frame.getContentPane().add(saving);

		spanding = new JRadioButton("Spanding Accont");
		spanding.setBounds(430, 230, 150, 25);
		frame.getContentPane().add(spanding);

		group = new ButtonGroup();
		group.add(saving);
		group.add(spanding);

		add = new JButton("Create");
		add.setBounds(430, 260, 150, 25);
		frame.getContentPane().add(add);

		delete = new JButton("Delete");
		delete.setBounds(430, 290, 150, 25);
		frame.getContentPane().add(delete);

		update = new JButton("Update");
		update.setBounds(430, 325, 150, 25);
		frame.getContentPane().add(update);

		close = new JButton("Close");
		close.setBounds(700, 400, 130, 25);
		frame.getContentPane().add(close);

		model = (DefaultTableModel) table.getModel();
		model.addColumn("ID");
		model.addColumn("Amount");
		model.addColumn("Type");
		model.addRow(new Object[] { "ID", "Amount", "Type" });

		if (bank != null) {
			Collection<Set<Account>> account = bank.getAccounts();
			if (account != null) {
				for (Set<Account> a : account) {
					if (a != null)
						for (Account ac : a)
							addAccount(ac);
				}
			}
			listModelPerson = new DefaultListModel<String>();
			listModelPerson.addElement("Person");
			if (p != null) {
				for (Person per : p) {
					listModelPerson.addElement(per.getCNP());
				}
			}
			listPerson = new JList<String>(listModelPerson);
			listPerson.setBounds(620, 60, 150, 250);
			frame.getContentPane().add(listPerson);
		}
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

	public void addBtnAddListener(ActionListener e) {
		add.addActionListener(e);
	}

	public void addBtnDeleteListener(ActionListener e) {
		delete.addActionListener(e);

	}

	public void addBtnUpdateListener(ActionListener e) {
		update.addActionListener(e);
	}

	public void addClickListener(MouseListener m) {
		table.addMouseListener(m);
	}

	public int getid() {
		if (!id.getText().equals("")) {
			return Integer.parseInt(id.getText());
		} else
			return -1;
	}

	public double getamount() {
		if (amount.getText().equals("")) {
			return -1;
		} else {
			return Double.parseDouble(amount.getText());
		}

	}

	public Account getSelectedRow() {
		int s = table.getSelectedRow();
		if (s > 0) {
			Account c;
			if (table.getValueAt(s, 2).toString().equals("Saving Account")) {
				c = new SavingAccount();
			} else {
				c = new SpandingAccount();
			}
			c.setId((Integer) table.getValueAt(s, 0));
			c.setAmount((Double) table.getValueAt(s, 1));

			return c;
		}
		return null;
	}

	public Person getPerson() {
		for (Person per : p) {
			if (per.getCNP().equals(listPerson.getSelectedValue())) {
				return per;
			}
		}
		return null;
	}

	public Account removeRow() {
		Account a = getSelectedRow();
		model.removeRow(table.getSelectedRow());
		return a;
	}

	public void errorMessage() {
		JOptionPane.showMessageDialog(frame, "Error", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void updateTable() {
		int s = table.getSelectedRow();
		if (!id.getText().equals("")) {
			model.setValueAt(getid(), s, 0);
		}
		if (getamount() != -1) {
			model.setValueAt(getamount(), s, 1);
		}
		emptyFields();
	}

	public void emptyFields() {
		id.setText("");
		amount.setText("");
	}

	public void addAccount(Account a) {
		double am;
		String type = null;
		if (a instanceof SavingAccount) {
			type = "Saving Account";
			SavingAccount account = (SavingAccount) a;
			am = a.getAmount() + account.getInterest();
		} else {
			type = "Spanding Account";
			am = a.getAmount();
		}
		model.addRow(new Object[] { a.getId(), am, type });
	}

	public String getSelectedType() {
		if (saving.isSelected())
			return saving.getText();
		else
			return spanding.getText();
	}

	public void setText() {
		Account a = getSelectedRow();
		if (a != null) {
			id.setText(String.valueOf(a.getId()));
			amount.setText(String.valueOf(a.getAmount()));
		}
	}

	public void destroy() {
		frame.dispose();
	}
}
