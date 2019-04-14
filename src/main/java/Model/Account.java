package Model;

import java.util.Observable;
import java.util.Set;

public abstract class Account extends Observable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double amount;
	
	public Account() {
		
	}
	
	public Account(int id, double amount) {
		this.setId(id);
		this.setAmount(amount);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int hashCode() {
		int h= Integer.valueOf(getId());
		return h;
	}
	public boolean eqauls(Account a) {
		if(a!=null) {
			if(a instanceof Account) {
				if(a.getId()==getId() && a.hashCode()==this.hashCode()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract boolean add(double amount);
	
	public abstract boolean withdraw(double amount);
	
	public boolean findAccount(Set<Account> acc) {
		
		for(Account a:acc) {
			if(a.equals(this)) {
				return true;
			}
		}
		return false;
	}
}
