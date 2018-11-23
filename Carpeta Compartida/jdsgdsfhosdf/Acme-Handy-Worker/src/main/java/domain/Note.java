
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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date		moment;
	private String		mandatoryComment;
	private String		refereeComment;
	private String		handyWorkerComment;
	private String		customerComment;
	private Customer	customer;
	private Referee		referee;
	private HandyWorker	handyWorker;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	public String getMandatoryComment() {
		return this.mandatoryComment;
	}
	public void setMandatoryComment(final String mandatoryComment) {
		this.mandatoryComment = mandatoryComment;
	}

	public String getRefereeComment() {
		return this.refereeComment;
	}
	public void setRefereeComment(final String refereeComment) {
		this.refereeComment = refereeComment;
	}

	public String getHandyWorkerComment() {
		return this.handyWorkerComment;
	}
	public void setHandyWorkerComment(final String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}

	public String getCustomerComment() {
		return this.customerComment;
	}
	public void setCustomerComment(final String customerComment) {
		this.customerComment = customerComment;
	}
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}
	@Valid
	@ManyToOne(optional = false)
	public Referee getReferee() {
		return this.referee;
	}
	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

	@Valid
	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}
	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
