
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private String		comment;
	private Date		moment;
	private Endorser	sender;
	private Endorser	recipient;


	@NotNull
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Valid
	@ManyToOne(optional = false)
	public Endorser getSender() {
		return this.sender;
	}

	public void setSender(final Endorser sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional = false)
	public Endorser getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Endorser recipient) {
		this.recipient = recipient;
	}

}
