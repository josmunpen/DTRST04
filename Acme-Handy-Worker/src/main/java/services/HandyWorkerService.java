
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
import domain.Administrator;
import domain.Application;
import domain.Box;
import domain.Finder;
import domain.HandyWorker;
import domain.Phase;
import domain.SocialProfile;

@Service
@Transactional
public class HandyWorkerService {

	//Repository
	@Autowired
	public HandyWorkerRepository	handyWorkerRepository;

	//Services
	@Autowired
	public AdministratorService		administratorService;


	//Constructor
	public HandyWorkerService() {
		super();
	}

	//Simple CRUD

	//8.1
	public HandyWorker create() {
		//User can't be logged to register
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

		//Actor
		final Box trash = new Box();
		final Box out = new Box();
		final Box spam = new Box();
		final Box in = new Box();
		trash.setName("trash");
		in.setName("in");
		out.setName("out");
		spam.setName("spam");
		out.setPredefined(true);
		in.setPredefined(true);
		spam.setPredefined(true);
		trash.setPredefined(true);
		final List<Box> predefined = new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);

		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.HANDYWORKER);
		final List<Application> applications = new ArrayList<Application>();
		final List<Phase> phases = new ArrayList<Phase>();
		final List<Finder> finders = new ArrayList<Finder>();
		newUser.addAuthority(f);

		result.setBoxes(new ArrayList<Box>(predefined));
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhotoURL("");
		result.setApplications(applications);

		//HandyWorker
		result.setScore(0);

		result.setUserAccount(user);
		//TODO: Make name
		result.setMake("");
		result.setPlannedPhases(phases);
		result.setFinders(finders);
		return result;
	}
	//9.2
	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
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

	//38.5
	public Collection<HandyWorker> topThreeHandyWorkersByComplaints() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.handyWorkerRepository.topThreeHandyWorkersByComplaints();
	}
}
