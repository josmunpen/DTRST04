
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
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.FixUpTask;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	//Repository
	@Autowired
	private CustomerRepository	customerRepository;

	//Services
	public AdministratorService	administratorService;


	//Constructor
	public CustomerService() {
		super();
	}

	//Simple CRUD

	//8.1
	public Customer create() {
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

		Customer result;
		result = new Customer();

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
		f.setAuthority(Authority.CUSTOMER);
		newUser.addAuthority(f);

		result.setBoxes(new ArrayList<Box>(predefined));
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhotoURL("");

		//Customer
		result.setScore(0);
		result.setFixUpTasks(new ArrayList<FixUpTask>());
		result.setUserAccount(user);
		return result;
	}
	//9.2
	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);
		Assert.isTrue(!customer.getBan());

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Modified customer must be logged customer
		final Customer logCustomer;
		logCustomer = this.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.notNull(logCustomer.getId());

		//Restrictions
		Assert.isTrue(customer.getBan() != true);

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

	//11.1
	//TODO:Terminar la Querie de findByFixUpTask
	public Customer findByFixUpTask(final FixUpTask fixUpTask) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Customer res;
		res = this.customerRepository.findByFixUpTask(fixUpTask.getId());
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
	public ArrayList<Object> fixUpTasksStatistics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.customerRepository.fixUpTaskStatistics();
	}

	public Collection<Customer> customersWithMoreFixUpTasks() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.customerRepository.customersWithMoreFixUpTasks();
	}

	//38.5
	public Collection<Customer> topThreeCustomersbyComplaints() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.customerRepository.topThreeCustomersByComplaints();
	}
}
