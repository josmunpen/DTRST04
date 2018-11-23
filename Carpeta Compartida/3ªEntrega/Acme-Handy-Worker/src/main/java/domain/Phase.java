
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Phase extends DomainEntity {

	private String	title;
	private String	description;
	private Date	startMoment;
	private Date	endMoment;
	private Integer	number;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}
	public Integer getNumber() {
		return this.number;
	}
	public void setNumber(final Integer number) {
		this.number = number;
	}

}
