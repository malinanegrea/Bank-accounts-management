package Model;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Person implements java.io.Serializable, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1644452003017105281L;
	private String CNP;
	private String name;
	private String address;
	
	public Person() {
		
	}

	public Person(String CNP, String Name, String Address) {
		this.setCNP(CNP);
		this.setName(Name);
		this.setAddress(Address);
	}

	public String getCNP() {
		return CNP;
	}

	public void setCNP(String cNP) {
		this.CNP = cNP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean equals(Person p) {
		if (p != null) {
			if (p instanceof Person) {
				if (CNP.equals(p.getCNP()) && name.equals(p.getName()) && address.equals(p.getAddress()) &&
					p.hashCode()==hashCode()) {
					return true;
				}
			}
		}

		return false;

	}

	public int hashCode() {
		int h;
		h = CNP.hashCode() + 2 * name.hashCode() + 3 * address.hashCode();
		return h;
	}

	public void update(Observable arg0, Object arg1) {
		Account a=(Account)arg0;
		System.out.println("Persoana cu CNP "+ CNP +" are in contul " + a.getId() +" suma actualizata "+ arg1);
		
	}
	
	public boolean findPerson(Set<Person> pers) {
		
		for(Person p: pers) {
			if(p.equals(this)) {
				return true;
			}
		}
		return false;
	}
}
