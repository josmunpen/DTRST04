
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	//Repository
	@Autowired
	public TutorialRepository	tutorialRepository;


	//Services

	//Constructor
	public TutorialService() {
		super();
	}

	//Complex methods
	//47.2
	public Collection<Tutorial> findAll() {
		Collection<Tutorial> res;
		res = this.tutorialRepository.findAll();
		return res;
	}

	//49.1
	public Tutorial save(final Tutorial tutorial) {
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));
		Assert.isTrue(tutorial.getHandyWorker().getUserAccount().equals(user));

		Assert.notNull(tutorial);
		Assert.notNull(tutorial.getId());
		Assert.notNull(tutorial.getTitle());
		Assert.notNull(tutorial.getSummary());

		final Tutorial res = this.tutorialRepository.save(tutorial);

		return res;
	}

	public void delete(final Tutorial tutorial) {
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));
		Assert.isTrue(tutorial.getHandyWorker().getUserAccount().equals(user));

		Assert.notNull(tutorial);
		Assert.notNull(tutorial.getId());
		Assert.notNull(tutorial.getTitle());
		Assert.notNull(tutorial.getSummary());

		this.tutorialRepository.delete(tutorial);

	}
}
