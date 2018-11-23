
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends Actor {

	private Collection<Endorsement>	endorsements;
	private Integer						score;


	@OneToMany
	public Collection<Endorsement> getEndorsements() {
		return this.endorsements;
	}

	public void setEndorsements(final Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	@NotNull
	public Integer getScore() {
		return this.score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

}
