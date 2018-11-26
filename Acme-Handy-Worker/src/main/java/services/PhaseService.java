
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Repository
	@Autowired
	public PhaseRepository		phaseRepository;

	//Services
	@Autowired
	public HandyWorkerService	handyWorkerService;


	//Constructor
	public PhaseService() {
		super();
	}

	//Simple CRUD methods
	//11.4
	public Phase create() {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Phase res = new Phase();
		//TODO: Revisar create
		return res;
	}

	public Collection<Phase> findByHandyWorker() {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		Assert.notNull(handyWorker.getId());

		Collection<Phase> res;
		res = this.phaseRepository.findByHandyWorkerId(handyWorker.getId());
		return res;
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);

		final Phase res;

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handyWorker is phase's owner
		final HandyWorker logHandyWorker;
		logHandyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.notNull(logHandyWorker.getId());
		final Collection<Phase> phases = logHandyWorker.getPlannedPhases();
		Assert.isTrue(phases.contains(phase));

		Assert.notNull(phase.getDescription());
		Assert.notNull(phase.getNumber());
		//TODO: Comprobar que el number es correcto
		Assert.notNull(phase.getStartMoment());
		Assert.notNull(phase.getEndMoment());
		Assert.isTrue(phase.getEndMoment().after(phase.getStartMoment()));
		Assert.notNull(phase.getTitle());

		res = this.phaseRepository.save(phase);
		return res;
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handyWorker is phase's owner
		final HandyWorker logHandyWorker;
		logHandyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.notNull(logHandyWorker.getId());
		final Collection<Phase> phases = logHandyWorker.getPlannedPhases();
		Assert.isTrue(phases.contains(phase));

		Assert.notNull(phase.getDescription());
		Assert.notNull(phase.getNumber());
		//TODO: Comprobar que el number es correcto
		Assert.notNull(phase.getStartMoment());
		Assert.notNull(phase.getEndMoment());
		Assert.isTrue(phase.getEndMoment().after(phase.getStartMoment()));
		Assert.notNull(phase.getTitle());

		this.phaseRepository.delete(phase);
	}
}
