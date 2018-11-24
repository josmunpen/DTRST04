
package services;

import java.util.ArrayList;
import java.util.List;

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
	public HandyWorkerRepository	handyWorkerRepository;


	//Services

	//Constructor
	public HandyWorkerService() {
		super();
	}

	//Simple CRUD

	//8.1
	public HandyWorker create() {
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
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setBoxes(new ArrayList<Box>(predefined));
		result.setScore(0);
		final UserAccount user = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		user.addAuthority(a);
		result.setUserAccount(user);
		return result;
	}

	//9.2
	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Restrictions
		Assert.notNull(handyWorker.getName());
		Assert.notNull(handyWorker.getEmail());
		Assert.notNull(handyWorker.getPhoneNumber());
		Assert.notNull(handyWorker.getAddress());
		Assert.isTrue(handyWorker.getBan() == false);
		Assert.notNull(handyWorker.getSurname());
		Assert.notNull(handyWorker.getUserAccount());

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

	//Returns logged customer from his or her handyWorker
	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		HandyWorker res;
		Assert.notNull(userAccount);

		res = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
}
