
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorser {

	private String					make;

	private Collection<Tutorial>	tutorials;
	private Collection<Application>	applications;
	private Collection<Phase>		plannedPhases;
	private Collection<Finder>		finders;
	private Collection<Note>		notes;
	private Curriculum				curriculum;


	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@OneToMany
	public Collection<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	@OneToMany
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@OneToMany
	public Collection<Phase> getPlannedPhases() {
		return this.plannedPhases;
	}

	public void setPlannedPhases(final Collection<Phase> plannedPhases) {
		this.plannedPhases = plannedPhases;
	}

	@OneToMany
	public Collection<Finder> getFinders() {
		return this.finders;
	}

	public void setFinders(final Collection<Finder> finders) {
		this.finders = finders;
	}

	@OneToMany
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@OneToOne(optional = true)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
