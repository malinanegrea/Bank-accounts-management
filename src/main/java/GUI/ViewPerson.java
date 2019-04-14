package GUI;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Logic.Bank;
import Model.Person;

public class ViewPerson {
	JFrame frame;
	JTable table;
	JButton add;
	JButton delete;
	JButton update;
	JTextField name = null;
	JTextField address = null;
	JTextField CNP = null;
	JButton close;
	DefaultTableModel model;

	public ViewPerson(final Bank bank) {
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

		table = new JTable();
		table.setBounds(10, 20, 400, 400);
		frame.getContentPane().add(table);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(430, 20, 170, 25);
		frame.getContentPane().add(nameLabel);

		name = new JTextField();
		name.setBounds(430, 60, 170, 25);
		frame.getContentPane().add(name);
		name.setText(null);

		JLabel addressLabel = new JLabel("address");
		addressLabel.setBounds(430, 95, 170, 25);
		frame.getContentPane().add(addressLabel);

		address = new JTextField();
		address.setBounds(430, 130, 170, 25);
		frame.getContentPane().add(address);
		address.setText(null);

		JLabel CNPLabel = new JLabel("CNP");
		CNPLabel.setBounds(430, 165, 170, 25);
		frame.getContentPane().add(CNPLabel);

		CNP = new JTextField();
		CNP.setBounds(430, 200, 170, 25);
		frame.getContentPane().add(CNP);
		CNP.setText(null);

		add = new JButton("Create");
		add.setBounds(430, 250, 150, 25);
		frame.getContentPane().add(add);

		delete = new JButton("Delete");
		delete.setBounds(430, 285, 150, 25);
		frame.getContentPane().add(delete);

		update = new JButton("Update");
		update.setBounds(430, 320, 150, 25);
		frame.getContentPane().add(update);

		close = new JButton("Close");
		close.setBounds(500, 400, 130, 25);
		frame.getContentPane().add(close);

		model = (DefaultTableModel) table.getModel();
		model = (DefaultTableModel) table.getModel();
		model.addColumn("CNP");
		model.addColumn("Name");
		model.addColumn("Address");
		model.addRow(new Object[] { "CNP", "Name", "Address" });
		if (bank != null) {
			Set<Person> person = bank.getPerson();
			if (person != null) {
				for (Person p : person) {
					addPerson(p);
				}
			}
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

	public String getName() {
		if (!name.getText().equals("")) {
			return name.getText();
		} else
			return null;
	}

	public String getCNP() {
		if (!CNP.getText().equals("")) {
			return CNP.getText();
		} else {
			return null;
		}
	}

	public String getaddress() {
		if (address.getText().equals("")) {
			return null;
		} else {
			return address.getText();
		}

	}

	public Person getSelectedRow() {
		int s = table.getSelectedRow();
		if (s != -1) {
			Person c = new Person();
			c.setCNP((String) table.getValueAt(s, 0));
			c.setName((String) table.getValueAt(s, 1));
			c.setAddress((String) table.getValueAt(s, 2));
			return c;
		} else
			return null;
	}

	public Person removeRow() {
		Person p = getSelectedRow();
		model.removeRow(table.getSelectedRow());
		return p;
	}

	public void errorMessage() {
		JOptionPane.showMessageDialog(frame, "Error", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void updateTable() {
		int s = table.getSelectedRow();
		if (!name.getText().equals("")) {
			model.setValueAt(getName(), s, 1);
		}
		if (!address.getText().equals("")) {
			model.setValueAt(getaddress(), s, 2);
		}
		if (!CNP.getText().equals("")) {
			model.setValueAt(getCNP(), s, 0);
		}
		emptyFields();
	}

	public void emptyFields() {
		name.setText("");
		address.setText("");
		CNP.setText("");
	}

	public void addProduct(Person p) {
		addPerson(p);
	}

	public void addPerson(Person p) {
		model.addRow(new Object[] { p.getCNP(), p.getName(), p.getAddress() });
	}

	public void setText() {
		Person p = getSelectedRow();
		if (p != null) {
			CNP.setText(p.getCNP());
			name.setText(p.getName());
			address.setText(p.getAddress());
		}
	}

	public void destroy() {
		frame.dispose();
	}
}
