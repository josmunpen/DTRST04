
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String							ticker;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;
	private Collection<EducationRecord>		educationRecords;
	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<PersonalRecord>		personalRecords;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([0][0-9]|[1][0-9])(0[0-9]|1[0-2])(0[0-9]|[12][0-9]|3[01])-[A-Z0-9_]{6}$")
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}
	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}
	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}
	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}
	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PersonalRecord> getPersonalRecords() {
		return this.personalRecords;
	}
	public void setPersonalRecords(final Collection<PersonalRecord> personalRecords) {
		this.personalRecords = personalRecords;
	}

}
