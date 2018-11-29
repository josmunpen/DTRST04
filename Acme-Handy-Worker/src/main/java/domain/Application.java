
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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;
	private String		status;
	private Money		offeredPrice;
	private String		comment;
	private String		rejectedCause;
	private CreditCard	creditCard;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "^pending|accepted|rejected$")
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	public Money getOfferedPrice() {
		return this.offeredPrice;
	}
	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@NotBlank
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getRejectedCause() {
		return this.rejectedCause;
	}
	public void setRejectedCause(final String rejectedCause) {
		this.rejectedCause = rejectedCause;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	//Relationships
	private FixUpTask	fixUpTask;


	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}
	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}
}
