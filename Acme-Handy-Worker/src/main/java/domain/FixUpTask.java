
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

	private String	ticker;
	private Date	moment;
	private String	description;
	private String	address;
	private Money	maximumPrice;
	private Date	startDate;
	private Date	endDate;


	@Column(unique = true)
	@Pattern(regexp = "^([0][0-9]|[1][0-9])(0[0-9]|1[0-2])(0[0-9]|[12][0-9]|3[01])-[A-Z0-9_]{6}$")
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
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
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}
	@Valid
	public Money getMaximumPrice() {
		return this.maximumPrice;
	}
	public void setMaximumPrice(final Money maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	//Relationships
	private Category				category;
	private Collection<Warranty>	warranty;
	private Collection<Phase>		phases;
	private Collection<Complaint>	complaints;
	private Collection<Application>	applications;


	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Application> getApplications() {
		return this.applications;
	}
	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(final Category category) {
		this.category = category;
	}
	@OneToMany
	public Collection<Warranty> getWarranty() {
		return this.warranty;
	}
	public void setWarranty(final Collection<Warranty> warranty) {
		this.warranty = warranty;
	}
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Phase> getPhases() {
		return this.phases;
	}
	public void setPhases(final Collection<Phase> phases) {
		this.phases = phases;
	}

	@Valid
	@OneToMany
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}
	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

}
