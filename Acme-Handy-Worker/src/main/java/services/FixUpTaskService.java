
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Money;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	//Repositories
	@Autowired
	public FixUpTaskRepository	fixUpTaskRepository;

	//Services
	@Autowired
	public CustomerService		customerService;

	@Autowired
	public HandyWorkerService	handyWorkerService;

	@Autowired
	public AdministratorService	administratorService;


	//Constructor
	public FixUpTaskService() {
		super();
	}

	//Simple CRUD

	//10.1

	public FixUpTask create() {
		//Logged user must be a customer
		Assert.isTrue(!LoginService.getPrincipal().isAccountNonLocked());
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final FixUpTask res = new FixUpTask();

		res.setTicker("");
		res.setDescription("");
		res.setAddress("");
		res.setMaximumPrice(new Money());
		res.setStartDate(new Date());
		res.setEndDate(new Date());
		res.setCategory(new Category());
		final ArrayList<Application> applications = new ArrayList<Application>();
		res.setApplications(applications);
		final ArrayList<Phase> phases = new ArrayList<Phase>();
		res.setPhases(phases);
		final ArrayList<Warranty> warranties = new ArrayList<Warranty>();
		res.setWarranty(warranties);

		return res;
	}
	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//fixUpTask's owner ID must be the same as LoggedCustomerId
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.isTrue(logCustomer.getId() != 0);
		Assert.isTrue(logCustomer.getFixUpTasks().contains(fixUpTask));

		//Business logic
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		final FixUpTask res;
		res = this.fixUpTaskRepository.save(fixUpTask);
		return res;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//fixUpTask's owner ID must be the same as LoggedCustomerId
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.isTrue(logCustomer.getId() != 0);
		Assert.isTrue(logCustomer.getFixUpTasks().contains(fixUpTask));

		//Business logic
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	public Collection<FixUpTask> findByCustomer() {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		res = this.fixUpTaskRepository.findByCustomerId(customer.getId());
		return res;
	}

	//11.1
	public Collection<FixUpTask> findAll() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.findAll();
		return res;
	}

	//12.5
	public ArrayList<Object> applicationsStatistics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.fixUpTaskRepository.applicationsStatistics();
	}

	public ArrayList<Object> maximunPriceStatistics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.fixUpTaskRepository.maximunPriceStatistics();
	}

	//38.5
	public ArrayList<Object> complaintsStatistics() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.fixUpTaskRepository.complaintsStatistics();
	}

	public double fixUpTasksWithComplaints() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.fixUpTaskRepository.fixUpTasksWithComplaints();
	}
}
