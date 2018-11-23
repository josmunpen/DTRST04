
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	private String	companyName;
	private String	role;
	private String	attachmentURL;
	private String	comment;
	private Date	startDate;
	private Date	endDate;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}
	@NotBlank
	public String getRole() {
		return this.role;
	}
	public void setRole(final String role) {
		this.role = role;
	}
	@URL
	public String getAttachmentURL() {
		return this.attachmentURL;
	}
	public void setAttachmentURL(final String attachmentURL) {
		this.attachmentURL = attachmentURL;
	}
	@NotBlank
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
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
}
