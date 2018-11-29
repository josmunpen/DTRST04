
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Date				moment;
	private String				description;
	private String				attachments;
	private Collection<Note>	notes;


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
	@URL
	public String getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	@OneToMany
	public Collection<Note> getNotes() {
		return this.notes;
	}
	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}


	//Relationships
	private Complaint complaint;


	@OneToOne(optional = false)
	public Complaint getComplaint() {
		return this.complaint;
	}
	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

}
