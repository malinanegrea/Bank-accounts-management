package Logic;

import Model.Account;

public class SpandingAccount  extends Account implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpandingAccount() {
		
	}

	public SpandingAccount(int id, double amount) {
		super(id, amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(double amount) {
		setAmount(amount+getAmount());
		this.setChanged();
		this.notifyObservers(this.getAmount());
		return true;
	}

	@Override
	public boolean withdraw(double amount) {
		if(amount< getAmount()) {
			setAmount(getAmount()-amount);
			this.setChanged();
			this.notifyObservers(this.getAmount());
			return true;
		}
		return false;
	}

}
