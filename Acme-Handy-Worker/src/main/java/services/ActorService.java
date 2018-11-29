
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Repository
	@Autowired
	public ActorRepository			actorRepository;

	@Autowired
	public UserAccountRepository	userAccountRepository;

	//Services
	@Autowired
	public AdministratorService		administratorService;


	//Constructor
	public ActorService() {
		super();
	}

	//Simple CRUD methods
	//Returns logged actor
	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged actor from his or her userAccount
	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor res;
		Assert.notNull(userAccount);
		Assert.notNull(userAccount.getId());

		res = this.actorRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Collection<Actor> findAll() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.actorRepository.findAll();
	}

	//38.2
	public Collection<Actor> suspiciousActors() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.actorRepository.suspiciousActors();
	}

	//38.3
	public Actor banActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Actor res;
		//Logged user must be an administrator
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(au));

		Assert.isTrue(a.getBan() == false);

		a.setBan(true);
		res = this.actorRepository.save(a);

		//TODO: en lugar de quitar la cuenta de usuario, hacer comprobación de que el usuario no está baneado en todas las acciones
		final UserAccount userToBan = a.getUserAccount();
		this.userAccountRepository.delete(userToBan);

		return res;
	}

	//38.4
	public Actor unbanActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		final Actor res;

		//Logged user must be an administrator
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(au));

		Assert.isTrue(a.getBan() == true);

		a.setBan(false);
		res = this.actorRepository.save(a);

		//TODO:Revisar (anotación banActor)
		final UserAccount userToUnban = a.getUserAccount();
		this.userAccountRepository.save(userToUnban);

		return res;
	}
}
