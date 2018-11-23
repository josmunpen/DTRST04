
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	private String	fullName;
	private String	email;
	private String	phoneNumber;
	private String	linkedInURL;
	private String	comment;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	//@Pattern(regexp = Pattern("^\+\d{1,3} \(\d{1,3}\) \d{4,}$)|(^\+\d{1,3} \d{4,}$)|(^\d{4,}$"))
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@URL
	public String getLinkedInURL() {
		return this.linkedInURL;
	}
	public void setLinkedInURL(final String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}
}
