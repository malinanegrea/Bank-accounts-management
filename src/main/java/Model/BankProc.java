package Model;

public interface BankProc {

	/*
	 * @pre the person is not a client of the bank
	 * 
	 * @post the person is now a client
	 */
	public void addPerson(Person p);

	/*
	 * @pre the person is a client of the bank
	 * 
	 * @post the person is not a client of the bank
	 */
	public void removePerson(Person p);

	/*
	 * @pre the person is a client of the bank
	 * 
	 * @post the account in registered
	 * 
	 * @post the account is associated with the person
	 */
	public void addAccount(Person p, Account a);

	/*
	 * @pre the person is a client of the bank
	 * 
	 * @pre the account in registered
	 * 
	 * @post the account is no longer registered
	 */
	public boolean removeAccount(Person p, Account a);

	/*
	 * @pre the person is a client of the bank
	 * 
	 * @pre the account in registered
	 * 
	 * @pre amount is a positive number
	 * 
	 * @post the amount is updated
	 */
	public boolean addAmount(Person p, Account a, double amount);

	/*
	 * @pre the person is a client of the bank
	 * 
	 * @pre the account in registered
	 * 
	 * @pre amount is a positive number
	 * 
	 * @post the amount is updated
	 */
	public boolean withdrw(Person p, Account a, double amount);

	/*
	 * @pre the old person is a client of the bank
	 * 
	 * @post the data is update
	 */
	public void updatePerson(Person old, Person newP);

	/*
	 * @pre the old person is a client of the bank
	 * 
	 * @post the data is update
	 */
	public void updateAccount(Person p, Account old, Account newA);

}
