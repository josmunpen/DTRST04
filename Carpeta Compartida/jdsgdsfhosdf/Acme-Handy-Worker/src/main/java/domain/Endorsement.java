
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
	private Endorser	endorser;


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
	public Endorser getEndorser() {
		return this.endorser;
	}

	public void setEndorser(final Endorser endorser) {
		this.endorser = endorser;
	}

}
