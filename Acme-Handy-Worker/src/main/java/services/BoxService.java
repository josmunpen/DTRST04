
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;

@Service
@Transactional
public class BoxService {

	//Repository
	@Autowired
	public BoxRepository	boxRepository;


	//Services

	//Constructor
	public BoxService() {
		super();
	}

	//Simple CRUD
	public Box create() {
		return new Box();
	}

	//9.4
	public Box save(final Box box) {
		Assert.notNull(box);
		Assert.notNull(box.getId());

		//Logged user must be a customer/handyworker
		final Authority a = new Authority();
		final Authority b = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a)||user.getAuthorities().contains(b));

		//Restrictions
		Assert.notNull(box.getName());
		Assert.isNull(box.getPredefined());
		if(box.getPredefined()==true) {
			Assert.isTrue(box.getName()=="in"||box.getName()=="out"||box.getName()=="trash"||box.getName()=="spam");
		}
		final Box res;
		res = this.boxRepository.save(box);
		return res;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.notNull(box.getId());
		Assert.isTrue(this.boxRepository.exists(box.getId()));

		//Logged user must be a customer/handyworker
		final Authority a = new Authority();
		final Authority b = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a)||user.getAuthorities().contains(b));

		//Restrictions
		Assert.notNull(box.getName());
		Assert.isTrue(box.getPredefined() == false);

		this.boxRepository.delete(box);

	}

}
