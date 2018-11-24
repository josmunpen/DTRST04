
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;

public class ActorService {

	@Autowired
	public ActorRepository			actorRepository;

	@Autowired
	public AdministratorService		administratorService;

	@Autowired
	public UserAccountRepository	userAccountRepository;


	//38.2
	public Collection<Actor> suspiciousActors() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.actorRepository.suspiciousActors();
	}

	//38.3
	public Actor banActor(final Actor a) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		a.setBan(true);
		this.actorRepository.save(a);
		final UserAccount user = a.getUserAccount();
		this.userAccountRepository.delete(user);

		return a;
	}

	//38.4
	public Actor UnbanActor(final Actor a) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.isTrue(a.getBan() == true);

		a.setBan(false);
		this.actorRepository.save(a);
		final UserAccount user = a.getUserAccount();
		this.userAccountRepository.save(user);

		return a;
	}
}
