
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	//8.1
	public Customer create() {
		Customer result;
		result = new Customer();
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
	public Customer save(final Customer customer) {

		//Logged user must be a customer

		/*
		 * final Customer customer2;
		 * customer2 = this.customerService.findByPrincipal();
		 * Assert.notNull(customer2);
		 * Assert.notNull(customer2.getId());
		 * Assert.isTrue(customer.getBan() == false);
		 */

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Restrictions
		Assert.notNull(customer.getName());
		Assert.notNull(customer.getEmail());
		Assert.notNull(customer.getPhoneNumber());
		Assert.notNull(customer.getAddress());
		Assert.isTrue(customer.getBan() == false);
		Assert.notNull(customer.getSurname());
		Assert.notNull(customer.getUserAccount());

		Customer res;
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

	//12.5
	//TODO:logged
	public ArrayList<Object> fixUpTasksStatistics() {
		return this.customerRepository.fixUpTaskStatistics();
	}

	public Collection<Customer> customersWithMoreFixUpTasks() {
		return this.customerRepository.customersWithMoreFixUpTasks();
	}
}
