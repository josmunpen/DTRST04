
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Referee;
import domain.Report;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService {

	//Repository
	@Autowired
	public RefereeRepository	refereeRepository;


	//Services

	//Constructor
	public RefereeService() {
		super();
	}

	//Simple CRUD
	//38.1
	public Referee create() {

		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Referee res;
		res = new Referee();
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
		f.setAuthority(Authority.REFEREE);
		newUser.addAuthority(f);

		res.setBoxes(new ArrayList<Box>(predefined));
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setName("");
		res.setEmail("");
		res.setAddress("");
		res.setSurname("");
		res.setPhoneNumber("");
		res.setPhotoURL("");

		//Referee
		res.setReports(new ArrayList<Report>());

		return res;

	}

	//Returns logged customer
	public Referee findByPrincipal() {
		Referee res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged customer from his or her userAccount
	public Referee findByUserAccount(final UserAccount userAccount) {
		Referee res;
		Assert.notNull(userAccount);

		res = this.refereeRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
}
