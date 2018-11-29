
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.PROPERTY)
public class Authority extends DomainEntity {

	private String	Admin		= "ADMIN";
	private String	Customer	= "CUSTOMER";
	private String	Reviewer	= "REVIEWER";


	public String getAdmin() {
		return this.Admin;
	}

	public void setAdmin(final String admin) {
		this.Admin = admin;
	}

	public String getCustomer() {
		return this.Customer;
	}

	public void setCustomer(final String customer) {
		this.Customer = customer;
	}

	public String getReviewer() {
		return this.Reviewer;
	}

	public void setReviewer(final String reviewer) {
		this.Reviewer = reviewer;
	}

}
