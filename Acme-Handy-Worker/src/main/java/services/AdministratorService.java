
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Box;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	public AdministratorRepository	administratorRepository;


	//12.1

	public Administrator create() {

		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		Administrator res;
		res = new Administrator();

		final UserAccount newUser = new UserAccount();
		final Authority ad = new Authority();
		ad.setAuthority(Authority.ADMIN);
		newUser.addAuthority(ad);
		res.setUserAccount(newUser);

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

		res.setBoxes(new ArrayList<Box>(predefined));
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setName("");
		res.setEmail("");
		res.setAddress("");
		res.setSurname("");
		res.setPhoneNumber("");
		res.setPhotoURL("");

		//HandyWorker
		res.setUserAccount(user);
		return res;
	}
	//Complex methods

	//Returns logged administrator
	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged administrator from his or her userAccount
	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator res;
		Assert.notNull(userAccount);

		res = this.administratorRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

}
