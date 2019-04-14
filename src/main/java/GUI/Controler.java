package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import Logic.Bank;
import Logic.SavingAccount;
import Logic.SpandingAccount;
import Model.Account;
import Model.Person;

public class Controler {

	private Bank bank;
	private ViewPerson person;
	private ViewAccount account;
	private ViewOperation operation;
	private MainFrame mainFrame;

	public Controler(Bank bank, MainFrame mainFrame) {

		this.bank = bank;
		this.mainFrame = mainFrame;

		mainFrame.addBtnClientListener(new ViewClientActionListener());
		mainFrame.addBtnCloseListener(new CloseMainActionListener());
		mainFrame.addBtnOperationListener(new ViewOperationActionListener());
		mainFrame.addBtnAccountListener(new ViewAccountActionListener());

	}

	public void setVisible() {
		mainFrame.setVisible();
	}

	public class CloseMainActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				FileOutputStream fileOut = new FileOutputStream("bank.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(bank);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			mainFrame.setInvisible();
			mainFrame.destroy();

		}

	}

	public class CloseClientActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			person.setInvisible();
			person.destroy();

			mainFrame.setVisible();

		}

	}

	public class AddClientActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Person p = new Person();
			p.setCNP(person.getCNP());
			p.setName(person.getName());
			p.setAddress(person.getaddress());

			try {
				bank.addPerson(p);
				person.addPerson(p);
				person.emptyFields();
			} catch (Exception ex) {
				ex.printStackTrace();
				person.errorMessage();
			}
		}

	}

	public class DeleteClientActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Person p = person.removeRow();
			try {

				bank.removePerson(p);
			} catch (Exception ex) {
				person.errorMessage();
			}
		}

	}

	public class UpdateClientActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Person pNew = new Person();
			pNew.setCNP(person.getCNP());
			pNew.setName(person.getName());
			pNew.setAddress(person.getaddress());

			Person pOld = person.getSelectedRow();

			try {
				bank.updatePerson(pOld, pNew);
				person.updateTable();
				person.emptyFields();
			} catch (Exception e1) {
				e1.printStackTrace();
				person.errorMessage();
			}

		}

	}

	public class AddAccountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Account a = null;
			String type = account.getSelectedType();

			if (type.equals("Saving Account")) {
				a = new SavingAccount();
			} else {
				a = new SpandingAccount();
			}
			a.setId(account.getid());
			a.setAmount(account.getamount());
			try {
				bank.addAccount(account.getPerson(), a);
				account.addAccount(a);
				account.emptyFields();
				
			} catch (Exception ex) {
				account.errorMessage();
			}
		}
	}

	public class DeleteAccountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				Account a = account.getSelectedRow();
				Person p = bank.findPerson(a);
				bank.removeAccount(p, a);
				account.removeRow();

			} catch (Exception ex) {
				ex.printStackTrace();
				account.errorMessage();
			}

		}

	}

	public class UpdateAccountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Account aOld = account.getSelectedRow();
			String type = account.getSelectedType();
			Account a = null;
			if (type.equals("Saving Account")) {
				a = new SavingAccount();
			} else {
				a = new SpandingAccount();
			}
			a.setId(account.getid());
			a.setAmount(account.getamount());
			Person p = bank.findPerson(a);

			try {
				bank.updateAccount(p, aOld, a);
				account.updateTable();
				account.emptyFields();
			} catch (Exception e1) {
				e1.printStackTrace();
				account.errorMessage();
			}

		}

	}

	public class CloseAccountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			account.setInvisible();
			account.destroy();

			mainFrame.setVisible();

		}

	}

	public class CloseOperationActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			operation.setInvisible();
			operation.destroy();

			mainFrame.setVisible();
		}

	}

	public class ViewClientActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (bank != null) {
				person = new ViewPerson(bank);
			} else {
				person = new ViewPerson(null);
			}

			person.addBtnAddListener(new AddClientActionListener());
			person.addBtnCloseListener(new CloseClientActionListener());
			person.addBtnDeleteListener(new DeleteClientActionListener());
			person.addBtnUpdateListener(new UpdateClientActionListener());
			person.addClickListener(new PersonMouseListener());

			mainFrame.setInvisible();
			person.setVisible();
		}

	}

	public class ViewAccountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (bank != null) {
				account = new ViewAccount(bank);

			} else {
				account = new ViewAccount(null);

			}

			account.addBtnAddListener(new AddAccountActionListener());
			account.addBtnCloseListener(new CloseAccountActionListener());
			account.addBtnDeleteListener(new DeleteAccountActionListener());
			account.addBtnUpdateListener(new UpdateAccountActionListener());
			account.addClickListener(new AccountMouseListener());

			mainFrame.setInvisible();
			account.setVisible();
		}

	}

	public class ViewOperationActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (bank != null) {
				operation = new ViewOperation(bank);

			} else {
				operation = new ViewOperation(null);

			}

			operation.addBtnDepListener(new DepositActionListener());
			operation.addBtnWithListener(new WithdrawActionListener());
			operation.addClickPersonListener(new OperationClientMouseListener());
			operation.addClickAccountListener(new OperationAccountMouseListener());
			operation.addBtnCloseListener(new CloseOperationActionListener());

			mainFrame.setInvisible();
			operation.setVisible();
		}

	}

	public class DepositActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Account a = operation.getAccount();
			Person p = operation.getPerson();
			double amount = operation.getAmount();
			if (!bank.addAmount(p, a, amount)) {
				operation.errorMessage();
			} else {
				operation.setAmmount(a);
			}
		}

	}

	public class WithdrawActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Account a = operation.getAccount();
			Person p =operation.getPerson();
			double amount = operation.getAmount();
			if(bank.withdrw(p, a, amount)) {
			operation.setAmmount(a);
			}
			else {
				operation.errorMessage();
			}
			

		}

	}

	public class PersonMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
			person.setText();

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class AccountMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
			account.setText();

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class OperationClientMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
			Person p = operation.getPerson();
			operation.setAccounts(p);

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class OperationAccountMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
			Account a = operation.getAccount();
			operation.setAmmount(a);

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
