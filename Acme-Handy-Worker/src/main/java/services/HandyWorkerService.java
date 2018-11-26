
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.HandyWorker;
import domain.SocialProfile;

@Service
@Transactional
public class HandyWorkerService {

	//Repository
	@Autowired
	public HandyWorkerRepository	handyWorkerRepository;


	//Services

	//Constructor
	public HandyWorkerService() {
		super();
	}

	//Simple CRUD

	//8.1
	public HandyWorker create() {
		//User cant be logged to register
		final Authority a = new Authority();
		final Authority b = new Authority();
		final Authority c = new Authority();
		final Authority d = new Authority();
		final Authority e = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		b.setAuthority(Authority.HANDYWORKER);
		c.setAuthority(Authority.CUSTOMER);
		d.setAuthority(Authority.REFEREE);
		e.setAuthority(Authority.SPONSOR);
		Assert.isTrue(!(user.getAuthorities().contains(a) || user.getAuthorities().contains(b) || user.getAuthorities().contains(c) || user.getAuthorities().contains(d) || user.getAuthorities().contains(e)));

		HandyWorker result;
		result = new HandyWorker();
		final Box trash = new Box();
		final Box out = new Box();
		final Box spam = new Box();
		final Box in = new Box();
		final List<Box> predefined = new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);
		out.setPredefined(true);
		in.setPredefined(true);
		spam.setPredefined(true);
		trash.setPredefined(true);
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setBoxes(new ArrayList<Box>(predefined));
		result.setScore(0);
		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.HANDYWORKER);
		newUser.addAuthority(f);
		result.setUserAccount(user);
		return result;
	}

	//9.2
	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.notNull(handyWorker.getId());
		Assert.isTrue(!handyWorker.getBan());

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Modified customer must be logged customer
		final HandyWorker logHandyWorker;
		logHandyWorker = this.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.notNull(logHandyWorker.getId());

		//Restrictions
		Assert.notNull(handyWorker.getName());
		Assert.notNull(handyWorker.getEmail());
		Assert.notNull(handyWorker.getPhoneNumber());
		Assert.notNull(handyWorker.getAddress());
		Assert.isTrue(handyWorker.getBan() != true);
		Assert.notNull(handyWorker.getSurname());
		Assert.notNull(handyWorker.getUserAccount());
		Assert.notNull(handyWorker.getMake());
		Assert.notNull(handyWorker.getScore());

		HandyWorker res;
		res = this.handyWorkerRepository.save(handyWorker);
		return res;
	}

	//Complex methods

	//Returns logged handyWorker
	public HandyWorker findByPrincipal() {
		HandyWorker res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged customer from his or her userAccount
	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		HandyWorker res;
		Assert.notNull(userAccount);

		res = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	//12.5
	public Collection<HandyWorker> handyWorkersWithMoreApplications() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.handyWorkerRepository.handyWorkersWithMoreApplications();
	}
}
