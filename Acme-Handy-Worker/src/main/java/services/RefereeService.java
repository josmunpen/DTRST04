
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
		final Box trash = new Box();
		final Box out = new Box();
		final Box spam = new Box();
		final Box in = new Box();
		final List<Box> predefined = new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);
		res.setBoxes(new ArrayList<Box>(predefined));

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
