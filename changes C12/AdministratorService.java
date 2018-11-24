
package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	public AdministratorRepository	administratorRepository;


	//12.1

	public Administrator create() {

		//the logged user must be an administrator
		final Administrator admin;
		admin = this.findByPrincipal();
		Assert.notNull(admin);

		final UserAccount user = new UserAccount();
		final Authority au = new Authority();
		au.setAuthority(Authority.ADMIN);
		final ArrayList<Authority> a = new ArrayList<>();
		a.add(au);
		user.setAuthorities(a);
		admin.setUserAccount(user);

		return admin;
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
