
package services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Customer;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	//Repository
	@Autowired
	private CustomerRepository	customerRepository;


	//Services

	//Constructor
	public CustomerService() {
		super();
	}

	//Simple CRUD

	//9.1
	public Customer create() {
		Customer result;
		result = new Customer();
		Box trash=new Box();
		Box out=new Box();
		Box spam=new Box();
		Box in=new Box();
		List<Box> predefined=new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setBoxes(new ArrayList<Box>(predefined));
		result.setScore(0);
		UserAccount user=new UserAccount();
		Authority a=new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		user.addAuthority(a);
		result.setUserAccount(user);
		return result;
	}

	//9.2
	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getBan()==false);
		Authority a=new Authority();
		UserAccount user=LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));
		Customer res;
		//Restricciones
		res = this.customerRepository.save(customer);
		return res;
	}

	//Complex methods

	//Returns logged customer
	public Customer findByPrincipal() {
		Customer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged customer from his or her userAccount
	public Customer findByUserAccount(final UserAccount userAccount) {
		Customer res;
		Assert.notNull(userAccount);

		res = this.customerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
}
