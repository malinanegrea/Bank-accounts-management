package Logic;



import Model.Account;

public class SavingAccount extends Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double rate = 5;
	private double maxSum = 1000.0;
	private boolean enDep = true;
	private boolean enWithdr = true;
	
	public SavingAccount() {
		maxSum = (double)500;
	}

	public SavingAccount(int id, double amount, double rate) {
		super(id, amount);
		this.rate = rate;
	}

	public double getInterest() {
		return super.getAmount() * rate / 100;
	}

	@Override
	public boolean add(double amount) {
		if (amount >= maxSum) {
			if (enDep == true) {
				setAmount(getAmount() + amount);
				enDep = false;
				this.setChanged();
				this.notifyObservers(amount);
				
				return true;
			} else
				return false;
		} else {
			setAmount(getAmount() + amount);
			this.setChanged();
			this.notifyObservers(this.getAmount());
			return true;
		}
	}

	@Override
	public boolean withdraw(double amount) {
		if (amount < getAmount()) {
			if (amount >= maxSum) {
				if (enWithdr == true) {
					setAmount(getAmount() - amount);
					enWithdr = false;
					this.setChanged();
					this.notifyObservers(amount);
					return true;
				} else
					return false;
			} else {
				setAmount(getAmount() - amount);
				this.setChanged();
				this.notifyObservers(this.getAmount());
				return true;
			}
		}
		return false;
	}
	
	public double getAmount() {
		return super.getAmount()+getInterest();
	}
	
	
}
