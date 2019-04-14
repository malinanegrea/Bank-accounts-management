package Logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Model.Account;
import Model.BankProc;
import Model.Person;

public class Bank implements BankProc, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Person, Set<Account>> bank;

	// invariant bank instanceof HashMaP<?,?>
	public Bank() {
		setBank(new HashMap<Person, Set<Account>>());
	}

	public void addPerson(Person p) {
		assert !findPerson(p) : "Person found";
		bank.put(p, null);
		assert findPerson(p) : "Person not found";
		assert bank instanceof HashMap<?, ?>;
	}

	public void removePerson(Person p) {
		assert findPerson(p) : "Person not found";
		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (p.equals(pp)) {
				bank.remove(pp);
				return;
			}
		}
	
		assert !findPerson(p) : "Person found";
		assert bank instanceof HashMap<?, ?>;

	}

	public void updatePerson(Person old, Person newP) {

		assert findPerson(old) : "Person not found";

		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (old.equals(pp)) {
				pp.setAddress(newP.getAddress());
				pp.setCNP(newP.getCNP());
				pp.setName(newP.getName());
				return;
			}
		}

		assert findPerson(newP) : "New person not found";

		assert bank instanceof HashMap<?, ?>;
	}

	public void updateAccount(Person p, Account old, Account newA) {

		assert findPerson(p) : "Person not found";
		Set<Account> set = bank.get(p);

		for (Account a : set) {
			if (a.eqauls(old)) {
				a.setId(newA.getId());
				a.setAmount(newA.getAmount());
				a.hasChanged();
				a.notifyObservers(a.getAmount());
				return;
			}
		}

		assert bank.get(p).contains(newA) : "Account not found";
		assert bank instanceof HashMap<?, ?>;
	}

	public void addAccount(Person p, Account a) {

		assert findPerson(p) : "Person not found";

		Set<Account> set = bank.get(p);
		if (set == null) {
			set = new HashSet<Account>();
		}
		set.add(a);
		bank.put(p, set);
		Account acc=findAccount1(p,a);
		acc.addObserver(p);
		
		assert findAccount(p,a) : "Account not found";
		assert bank instanceof HashMap<?, ?>;
	}

	public boolean removeAccount(Person p, Account a) {
		assert findPerson(p) : "Person not found";

		assert findAccount(p,a): "Account not found";

		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (p.equals(pp)) {
				Set<Account> set = bank.get(pp);
				for (Account acc : set) {
					if (acc.eqauls(a)) {
						set.remove(acc);
						bank.put(pp, set);
						return true;
					}
				}

			}
		}
		assert !bank.get(p).contains(a) : "Account found";
		assert bank instanceof HashMap<?, ?>;
		return false;

	}

	public Map<Person, Set<Account>> getBank() {
		return bank;
	}

	public void setBank(Map<Person, Set<Account>> bank) {
		this.bank = bank;
	}

	public boolean addAmount(Person p, Account a, double amount) {

		assert findPerson(p) : "Person not found";
		assert findAccount(p,a) : "Account not found";
		assert amount > 0 : "Negativ amount";

		double sum = a.getAmount() + amount;
		Set<Account> set = bank.get(p);
		if (set != null) {
			for (Account ac : set) {
				if (ac.eqauls(a)) {
					return ac.add(amount);
				}
			}
		}
		assert a.getAmount() == sum : "Amount not updated";
		assert bank instanceof HashMap<?, ?>;
		return false;
	}

	public boolean withdrw(Person p, Account a, double amount) {
		assert findPerson(p) : "Person not found";
		assert findAccount(p,a): "Account not found";
		assert amount > 0 : "Negativ amount";
		Set<Account> set = bank.get(p);
		double sum = a.getAmount() - amount;
		if (set != null) {
			for (Account ac : set) {
				if (ac.getId() == a.getId()) {
					return a.withdraw(amount);
				}
			}
		}
		assert a.getAmount() == sum : "Amount not updated";
		assert bank instanceof HashMap<?, ?>;
		return false;
	}

	public Set<Person> getPerson() {
		Set<Person> p = new HashSet<Person>();
		p = bank.keySet();
		assert bank instanceof HashMap<?, ?>;
		return p;
	}

	public Collection<Set<Account>> getAccounts() {
		return bank.values();
	}

	public Set<Account> getAccounts(Person p) {
		return bank.get(p);
	}

	public boolean findPerson(Person p) {
		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (p.equals(pp))
				return true;
		}
		assert bank instanceof HashMap<?, ?>;
		return false;

	}

	public Person findPerson(Account a) {

		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			Set<Account> set = bank.get(pp);
			for (Account acc : set) {
				if (acc.eqauls(a)) {
					return pp;
				}
			}

		}
		return null;
	}

	public boolean findAccount(Person p, Account a) {

		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (pp.equals(p)) {
				Set<Account> set = bank.get(pp);
				for (Account acc : set) {
					if (acc.eqauls(a)) {
						return true;
					}
				}

			}
		}
		return false;
	}
	
	private Account findAccount1(Person p, Account a) {

		Set<Person> per = bank.keySet();
		for (Person pp : per) {
			if (pp.equals(p)) {
				Set<Account> set = bank.get(pp);
				for (Account acc : set) {
					if (acc.eqauls(a)) {
						return acc;
					}
				}

			}
		}
		return null;
	}
}
