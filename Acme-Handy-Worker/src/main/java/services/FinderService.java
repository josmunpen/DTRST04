
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	//Repositories
	@Autowired
	public FinderRepository		finderRepository;

	//Services
	@Autowired
	public HandyWorkerService	handyWorkerService;


	//TODO: 11.2
	/*
	 * @Autowired
	 * public FixUpTaskService fixUpTaskService;
	 */

	//Constructor
	public FinderService() {
		super();
	}
	//methods
	public Finder create() {
		//Logged Account must be a HandyWorker
		Assert.isTrue(!LoginService.getPrincipal().isAccountNonLocked());
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Finder fd = new Finder();
		return fd;
	}

	//37.1
	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		//Logged Account must be a HandyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//finder owner's ID must be the same as Logged HandyWorker's ID
		final HandyWorker logHandyWorker;
		logHandyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.isTrue(logHandyWorker.getFinders().contains(finder));

		final Finder f;
		f = this.finderRepository.save(finder);
		return f;
	}
	//TODO: 11.2
	//37.2
	/*
	 * public Finder saveResultsFixUpTasks(final Finder finder, final Collection<FixUpTask> results) {
	 * Assert.notNull(finder);
	 * 
	 * //Logged Account must be a HandyWorker
	 * final Authority a = new Authority();
	 * final UserAccount user = LoginService.getPrincipal();
	 * a.setAuthority(Authority.HANDYWORKER);
	 * Assert.isTrue(user.getAuthorities().contains(a));
	 * 
	 * //finder owner's ID must be the same as Logged HandyWorker's ID
	 * final HandyWorker logHandyWorker;
	 * logHandyWorker = this.handyWorkerService.findByPrincipal();
	 * Assert.notNull(logHandyWorker);
	 * Assert.isTrue(logHandyWorker.getFinders().contains(finder));
	 * 
	 * final Finder f;
	 * finder.setFixUpTasks(results);
	 * f = this.finderRepository.save(finder);
	 * return f;
	 * }
	 */
}
