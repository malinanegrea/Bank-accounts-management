import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Logic.Bank;
import Logic.SavingAccount;
import Logic.SpandingAccount;
import Model.Account;
import Model.Person;

public class BankTest {

	@Test
	public void testAddPerson() {
		
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		assertEquals("add Person", bank.findPerson(p), true);
	}
	
	@Test
	public void testRemovePerson() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		bank.removePerson(p);
		assertEquals("remove Person", bank.findPerson(p), false);
	}
	
	@Test
	public void testUpdatePerson() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Person newP = new Person("1781205784520", "Gal Vasile","Str. Graurilor nr. 10");;
		bank.updatePerson(p, newP);
		assertEquals("remove Person", bank.findPerson(newP), true);
	}
	
	@Test
	public void testAddAccount() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Account a= new SavingAccount();
		a.setId(1);
		a.setAmount(500.0);
		
		bank.addAccount(p, a);
		
		assertEquals("add account", bank.findAccount(p, a), true);
	}
	
	@Test
	public void testRemoveAccount() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Account a= new SavingAccount();
		a.setId(1);
		a.setAmount(500.0);
		
		bank.addAccount(p, a);
		
		bank.removeAccount(p, a);
		assertEquals("remove account", bank.findAccount(p, a), false);
	}
	
	@Test
	public void testUpdateAccount() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Account a= new SavingAccount();
		a.setId(1);
		a.setAmount(500.0);
		bank.addAccount(p, a);
		
		Account newA= new SavingAccount();
		newA.setId(2);
		newA.setAmount(50.0);
		
		bank.updateAccount(p, a, newA);
		assertEquals("add account", bank.findAccount(p, newA), true);
	}
	
	@Test
	public void testWithdrawal() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Account a= new SpandingAccount();
		a.setId(1);
		a.setAmount(500.0);
		
		bank.addAccount(p, a);
		
		double am = 35.0;
		double sum = a.getAmount()-am;
		
		bank.withdrw(p, a, am);
		assertEquals("withdrawal", a.getAmount()==sum, true);
	}
	
	@Test
	public void testDeposit() {
		Person p= new Person("1781205784520", "Gal Vasile"," Str. Graurilor nr 5");
		Bank bank = new Bank();
		
		bank.addPerson(p);
		
		Account a= new SpandingAccount();
		a.setId(1);
		a.setAmount(500.0);
		
		bank.addAccount(p, a);
		
		double am = 35.0;
		double sum = a.getAmount()+am;
		
		bank.addAmount(p, a, am);
		assertEquals("deposit", a.getAmount()==sum, true);
	}
	
	
}
