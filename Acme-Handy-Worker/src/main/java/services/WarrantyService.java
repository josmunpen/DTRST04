
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	@Autowired
	public WarrantyRepository	warrantyRepository;

	@Autowired
	public AdministratorService	administratorService;


	//12.2

	//Constructor
	public WarrantyService() {
		super();
	}

	//Simple CRUD methods
	public Warranty create() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Warranty res = new Warranty();
		res.setApplicableLaws("");
		res.setFinalMode(false);
		res.setTerms("");
		res.setTitle("");
		return res;
	}

	public void delete(final Warranty warranty) {

		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		Assert.isTrue(warranty.isFinalMode() == false);

		this.warrantyRepository.delete(warranty);

	}

	//12.2
	public Warranty save(final Warranty warranty) {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		Assert.isTrue(warranty.isFinalMode() == false);

		final Warranty res = this.warrantyRepository.save(warranty);

		return res;
	}

}
